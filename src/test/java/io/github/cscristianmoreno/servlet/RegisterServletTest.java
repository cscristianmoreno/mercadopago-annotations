package io.github.cscristianmoreno.servlet;

import org.junit.jupiter.api.Test;

import io.github.cscristianmoreno.mercadopago.response.MPResponseBody;
import io.github.cscristianmoreno.sample.controller.SampleController;

public class RegisterServletTest {
    
    /** 
     * @throws Exception
     */
    @Test
    void testCallMethods() throws Exception {
        MPResponseBody mpResponseBody = new MPResponseBody();

        SampleController mercadoPagoController = new SampleController();
        MPRegisterServlet registerServlet = new MPRegisterServlet<>(mercadoPagoController.getClass());

        registerServlet.callMethods(mpResponseBody);
    }
}
