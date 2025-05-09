package io.github.cscristianmoreno.mercadopago;

import java.util.logging.Logger;

import io.github.cscristianmoreno.annotations.MercadoPagoServer;
import io.github.cscristianmoreno.annotations.MercadoPago;
import io.github.cscristianmoreno.exceptions.MercadoPagoException;
import io.github.cscristianmoreno.services.ClassScanService;
import io.github.cscristianmoreno.utils.TomcatUtil;

public abstract class MP {

    private static Logger logger = Logger.getLogger("MPInitializer"); 

    public static void initializer(final Class<?> mainClass) throws Exception {

        MercadoPago mercadoPago = mainClass.getDeclaredAnnotation(MercadoPago.class);
        MercadoPagoServer server = mainClass.getDeclaredAnnotation(MercadoPagoServer.class);

        if (mercadoPago == null) {
            throw new MercadoPagoException("@MercadoPago annotation is required");
        }

        String packageScan = mercadoPago.packageScan();

        ClassScanService classScanService = new ClassScanService();
        classScanService.scan(packageScan);

        logger.info("All clases has been scanned");
        
        // MPPayer payer = new MPPayer();
        // payer.setEmail("cristian");
        // MPPreference mpPreference = new MPPreference();
        // Preference preference = mpPreference.createPreference(MPItem.getItems(), new MPPayer());

        // System.out.println(preference.getInitPoint());

        if (server == null) {
            return;
        }

        int port = server.port();
        TomcatUtil.start(port);
    }
}
