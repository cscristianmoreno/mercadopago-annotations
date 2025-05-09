package io.github.cscristianmoreno.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.resources.payment.Payment;

import io.github.cscristianmoreno.annotations.http.MPPaymentResponse;
import io.github.cscristianmoreno.config.ObjectMapperConfig;
import io.github.cscristianmoreno.dto.mercadopago.PaymentID;
import io.github.cscristianmoreno.exceptions.MercadoPagoException;
import io.github.cscristianmoreno.mercadopago.response.MPResponseBody;
import io.github.cscristianmoreno.mercadopago.result.MPResult;
import io.github.cscristianmoreno.utils.AnnotationUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class MPRegisterServlet<T> extends HttpServlet {

    private ObjectMapper objectMapper = ObjectMapperConfig.INSTANCE.getObjectMapper();
    private final Class<?> clazz;
    private final T instance;
    private final Method[] methods;

    public MPRegisterServlet(final Class<?> clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        this.clazz = clazz;
        instance = (T) clazz.getDeclaredConstructor().newInstance();
        methods = clazz.getDeclaredMethods();

        validateMethod();
    }
    
    /** 
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            getResponse(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getResponse(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        InputStream inputStream = req.getInputStream();

        String body = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

        if (body == null) {
            return;
        }

        JsonNode jsonNode = objectMapper.readTree(body);

        if (jsonNode.isNull() || jsonNode.size() == 0) {
            resp.getWriter().println("MPResponseBody is null");
            return;
        }

        MPResult mpResult = new MPResult();
        PaymentClient paymentClient = new PaymentClient();
            
        PaymentID data = new PaymentID();
        data.setId(jsonNode.get("data").get("id").asLong()); 
        
        mpResult.setId(jsonNode.get("id").asText());
        mpResult.setLiveMode(jsonNode.get("live_mode").asBoolean());
        mpResult.setType(jsonNode.get("type").asText());
        mpResult.setDateCreated(jsonNode.get("date_created").asText());
        mpResult.setUserId(jsonNode.get("user_id").asText());
        mpResult.setApiVersion(jsonNode.get("api_version").asText());
        mpResult.setAction(jsonNode.get("action").asText());
        mpResult.setData(data);
        
        paymentClient.get(data.getId());

        Payment payment = paymentClient.get(mpResult.getData().getId());

        MPResponseBody mpResponseBody = new MPResponseBody();
        mpResponseBody.setResult(mpResult);
        mpResponseBody.setStatus(payment.getStatus());
        mpResponseBody.setPayment(payment);

        callMethods(mpResponseBody);
    }

    public void callMethods(final MPResponseBody mpResponseBody) throws Exception {
        String status = mpResponseBody.getPayment().getStatus();

        if (status == null) {
            return;
        }

        for (Method method: methods) {
            Annotation[] annotations = method.getDeclaredAnnotations();
            Annotation annotation = annotations[0];

            Class<?> annotationType = annotation.annotationType();

            if (annotationType.isAssignableFrom(MPPaymentResponse.class)) {
                method.invoke(instance, mpResponseBody);
            }

            Class<? extends Annotation> annotationResult = AnnotationUtil.getAnnotationMatchStatus(status);

            if (annotationResult == null) {
                break;
            }

            if (!annotationType.isAssignableFrom(annotationResult)) {
                continue;
            }

            method.invoke(instance, mpResponseBody);
        }
    }

    private void validateMethod() {
        for (Method method: methods) {
            Parameter[] parameters = method.getParameters();
            Annotation[] annotations = method.getDeclaredAnnotations();
            List<Annotation> listAnnotations = getAnnotations(annotations);
            int size = listAnnotations.size();

            if (size != 1) {
                throw new MercadoPagoException("The method %s in %s required a only annotation", method.getName(), clazz.getSimpleName());
            }

            if (parameters.length != 1) {
                throw new MercadoPagoException("The method %s in %s required a only parameter", method.getName(), clazz.getSimpleName());
            }

            Parameter parameter = parameters[0];
            Class<?> parameterType = parameter.getType();

            if (!parameterType.isAssignableFrom(MPResponseBody.class)) {
                throw new MercadoPagoException("The parameter %s is not assignable from MPResponseBody in %s", parameterType.getSimpleName(), clazz.getSimpleName());
            }
        }
    }

    private List<Annotation> getAnnotations(Annotation[] annotations) {
        return AnnotationUtil.getControllerAnnotations(annotations);
    }
}
