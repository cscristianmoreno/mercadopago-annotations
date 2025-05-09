package io.github.cscristianmoreno.servlet;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.logging.Logger;

import io.github.cscristianmoreno.annotations.MercadoPagoController;
import io.github.cscristianmoreno.annotations.MercadoPagoIgnoreController;

import io.github.cscristianmoreno.mercadopago.utils.PreferenceRequestUtil;
import io.github.cscristianmoreno.utils.MessageUtil;
import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration.Dynamic;
import jakarta.servlet.annotation.HandlesTypes;

@HandlesTypes(MercadoPagoController.class)
public class MPServletInitializer implements ServletContainerInitializer {

    private Logger logger = Logger.getLogger("ServletInitializer");

    
    /** 
     * @param classes
     * @param context
     * @throws ServletException
     */
    @Override
    public void onStartup(Set<Class<?>> classes, ServletContext context) throws ServletException {

        PreferenceRequestUtil preferenceRequestUtil = PreferenceRequestUtil.INSTANCE;
        String notificationUrl = preferenceRequestUtil.getNotificationUrl();
        URL url = null;
        String path = null;
        
        try {
            url = new URL(notificationUrl);
            path = url.getPath();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        for (Class<?> clazz: classes) {
            
            if (clazz.isAnnotationPresent(MercadoPagoIgnoreController.class)) {
                continue;
            }
            
            String className = clazz.getSimpleName();
            
            try {
                MPRegisterServlet registerServlet = new MPRegisterServlet(clazz);
                Dynamic dynamic = context.addServlet(className, registerServlet);
                dynamic.addMapping(path);
                logger.info(MessageUtil.message("Controller %s has been registered", path));
                break;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    
}
