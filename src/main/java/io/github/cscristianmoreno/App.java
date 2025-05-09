package io.github.cscristianmoreno;

import io.github.cscristianmoreno.annotations.MercadoPagoServer;
import io.github.cscristianmoreno.annotations.MercadoPago;
import io.github.cscristianmoreno.mercadopago.MP;

/**
 * Hello world!
 *
 */
@MercadoPago(packageScan = "io")
@MercadoPagoServer
public class App 
{
    public static void main( String[] args ) throws Exception 
    {
        MP.initializer(App.class);
    }
}
