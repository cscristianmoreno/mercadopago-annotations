package io.github.cscristianmoreno.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


import io.github.cscristianmoreno.enums.PaymentMethod;
import io.github.cscristianmoreno.enums.PaymentType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE_USE)
public @interface MercadoPago {
    /** Token de acceso de Mercado Pago. */
    String accessToken() default "$MP_ACCESS_TOKEN";

    /** URL de redireccionamiento en caso de exito. */
    String redirectSuccessUrl() default "$MP_REDIRECT_SUCCESS_URL";
    
    /** URL de redireccionamiento en caso de rechazo. */
    String redirectFailureUrl() default "$MP_REDIRECT_FAILURE_URL";
    
    /** URL de redireccionamiento en caso de pendiente. */
    String redirectPendingUrl() default "$MP_REDIRECT_PENDING_URL";
    
    /** URL en el cuál se enviarán las notificaciones. */
    String notificationUrl() default "$MP_NOTIFICATION_URL";

    /** Clave secreta (Opcional). */
    String secretKey() default "$MP_SECRET_KEY";

    /** Cantidad de cuotas permitidas. */
    int installments() default 6;

    /** Excluir métodos de pago. Por ejemplo: Rapipago. */
    PaymentMethod[] excludedPaymentMethod() default {};

    /** # Excluir tipos de pago. Por ejemplo: Efectivo. */
    PaymentType[] excludedPaymentType() default {};

    /** Duración de un enlace vigente para ser pagado. Por ejemplo: 3 horas. */
    PreferenceDuration preferenceDuration() default @PreferenceDuration;

    /** Habilita si un enlace será expirado. */
    boolean expires() default true;

    /** Paquete a escanear. Por defecto escanea en "com" */
    String packageScan() default "com";
}
