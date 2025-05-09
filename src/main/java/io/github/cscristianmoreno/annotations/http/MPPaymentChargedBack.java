package io.github.cscristianmoreno.annotations.http;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** - Se realizó un contracargo en la tarjeta de crédito del comprador. */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MPPaymentChargedBack {
}
