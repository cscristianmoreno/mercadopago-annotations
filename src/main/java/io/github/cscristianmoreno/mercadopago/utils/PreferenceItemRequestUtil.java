package io.github.cscristianmoreno.mercadopago.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceItemRequest.PreferenceItemRequestBuilder;

import io.github.cscristianmoreno.annotations.fields.Id;
import io.github.cscristianmoreno.annotations.fields.Title;
import io.github.cscristianmoreno.annotations.fields.UnitPrice;
import io.github.cscristianmoreno.exceptions.MercadoPagoException;
import io.github.cscristianmoreno.mercadopago.item.MPItem;
import io.github.cscristianmoreno.utils.AnnotationUtil;
import io.github.cscristianmoreno.utils.CastUtil;
import io.github.cscristianmoreno.utils.ClassUtil;
import io.github.cscristianmoreno.utils.MessageUtil;

public abstract class PreferenceItemRequestUtil {

    private static final Logger logger = Logger.getLogger(PreferenceRequestUtil.class.getSimpleName());
    private static List<PreferenceItemRequest> listPreferenceItemRequests = new ArrayList<PreferenceItemRequest>();

    public static <T> void addItem(Class<?> clazz) throws Exception  {
        T instance = ClassUtil.getInstance(clazz);

        Class<? extends PreferenceItemRequestBuilder> clazzPreferenceItemRequest = PreferenceItemRequest.builder().getClass();
        Field[] preferenceItemRequestFields = clazzPreferenceItemRequest.getDeclaredFields(); 

        int pos = 0;

        PreferenceItemRequestBuilder preferenceItemRequestBuilder = PreferenceItemRequest.builder();
        
        Field[] fields = clazz.getDeclaredFields();

        String fieldTitle = null;

        for (Field field: fields) {
            if (pos >= preferenceItemRequestFields.length) {
                break;
            }

            Annotation[] annotations = field.getAnnotations();

            if (annotations.length == 0) {
                continue;
            }

            field.setAccessible(true);
            
            Annotation annotation = AnnotationUtil.getItemAnnotations(annotations).get(0);

            if (annotation == null) {
                continue;
            }

            Class<?> annotationType = annotation.annotationType();
            String annotationName = annotation.annotationType().getSimpleName();
            String uncapitalizeFirstLetter = annotationName.substring(0, 1).toLowerCase() + annotationName.substring(1);
            Field preferenceItemRequestField = clazzPreferenceItemRequest.getDeclaredField(uncapitalizeFirstLetter);

            /** If not match annotation name with preference item builder's names, continue */
            if (!uncapitalizeFirstLetter.matches(preferenceItemRequestField.getName())) {
                continue;
            }
            
            Class<?> fieldType = field.getType();

            T fieldValue = (T) field.get(instance);

            if (annotationType.isAssignableFrom(Id.class)) {
                if (!fieldType.isAssignableFrom(String.class)) {
                    throw new MercadoPagoException("The field type with annotation @Id required String type");
                }

                if (fieldValue == null) {
                    int value = MPItem.getItems().size() + 1;
                    setFieldValue(preferenceItemRequestBuilder, preferenceItemRequestField, Integer.toString(value));
                }
                else {
                    String id = (String) fieldValue;
                    Optional<PreferenceItemRequest> item = getItem(id);

                    if (item.isPresent()) {
                        throw new MercadoPagoException("Field with value %s is already exists", id);
                    }
                }
            }

            /** If field value is null, continue */
            if (fieldValue == null) {
                continue;
            }

            T value;

            if (annotationType.isAssignableFrom(UnitPrice.class)) {
                value = (T) new BigDecimal(fieldValue.toString());
            }
            else {
                
                value = CastUtil.cast(fieldType, fieldValue.toString()); 
            }

            setFieldValue(preferenceItemRequestBuilder, preferenceItemRequestField, value);

            if (annotation.annotationType().isAssignableFrom(Title.class)) {   
                fieldTitle = (String) field.get(instance);
            }

            pos++;
        }

        if (fieldTitle != null) {
            logger.info(MessageUtil.message("Item <%s> has been registered how a product!", fieldTitle));
        }

        PreferenceItemRequest preferenceItemRequest = preferenceItemRequestBuilder.build();
        listPreferenceItemRequests.add(preferenceItemRequest);
    }

    public static Optional<PreferenceItemRequest> getItem(String id) {
        return listPreferenceItemRequests.stream()
        .filter((item) -> item.getId().matches(id)).findFirst();
    }

    public static List<PreferenceItemRequest> getItems() {
        return listPreferenceItemRequests;
    }

    private static <T> void setFieldValue(T instance, Field field, T value) throws Exception {
        field.setAccessible(true);
        field.set(instance, value);

    }
}
