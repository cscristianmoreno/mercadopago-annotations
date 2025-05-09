package io.github.cscristianmoreno.sample.controller;

import io.github.cscristianmoreno.annotations.MercadoPagoController;
import io.github.cscristianmoreno.annotations.http.MPPaymentRejected;
import io.github.cscristianmoreno.annotations.http.MPPaymentResponse;
import io.github.cscristianmoreno.annotations.http.MPPaymentApproved;
import io.github.cscristianmoreno.mercadopago.response.MPResponseBody;

@MercadoPagoController
public class SampleController {
    
    @MPPaymentResponse
    public void response(MPResponseBody mpResponseBody) {
        System.out.println(mpResponseBody);
    }
    
    @MPPaymentApproved
    public void success(MPResponseBody responseBody) {
        System.out.println(responseBody);
    }

    @MPPaymentRejected
    public void rejected(MPResponseBody responseBody) {
    }
}
