package io.github.cscristianmoreno.exceptions;

import io.github.cscristianmoreno.utils.MessageUtil;

public class MercadoPagoException extends RuntimeException {
    public MercadoPagoException(String message, Object... params) {
        super(MessageUtil.message(message, params));
    }
}
