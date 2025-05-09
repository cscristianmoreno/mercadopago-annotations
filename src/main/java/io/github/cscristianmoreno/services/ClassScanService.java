package io.github.cscristianmoreno.services;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import com.mercadopago.MercadoPagoConfig;

import io.github.cscristianmoreno.annotations.MercadoPagoController;
import io.github.cscristianmoreno.annotations.MercadoPago;
import io.github.cscristianmoreno.annotations.MercadoPagoItem;
import io.github.cscristianmoreno.mercadopago.item.MPItem;
import io.github.cscristianmoreno.mercadopago.utils.MPControllerUtil;
import io.github.cscristianmoreno.mercadopago.utils.PreferenceRequestUtil;
import io.github.cscristianmoreno.models.services.IClassScanService;
import io.github.cscristianmoreno.utils.EnvironmentUtil;

public class ClassScanService implements IClassScanService {
    /**
     * Scan all clases if contains annotations
     * @throws Exception
     */
    @Override
    public void scan(String packageScan) throws Exception {
        Path paths = Paths.get(getClass().getResource("/").toURI()).getParent();

        List<File> files = Files.walk(paths.resolve("classes").resolve(packageScan))
        .filter(Files::isRegularFile)
        .map(Path::toFile)
        .collect(Collectors.toList());

        for (File file: files) {
            String path  = file.getAbsolutePath();
            String classes = "classes" + File.separator;
            int classPosition = path.indexOf("classes");
            String classPath = path.substring(classPosition).replace(classes, "").replace(File.separator, ".");
            String packageName = classPath.replace(".class", "");

            URLClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[] {
                file.toURI().toURL()
            });

            Class<?> clazz = urlClassLoader.loadClass(packageName);

            if (clazz.getAnnotations().length == 0) {
                continue;
            }

            if (clazz.isAnnotationPresent(MercadoPagoItem.class)) {
                MPItem.addItem(clazz);
                continue;
            }
            
            if (clazz.isAnnotationPresent(MercadoPagoController.class)) {
                MPControllerUtil.addController(clazz);
            }
            
            if (!clazz.isAnnotationPresent(MercadoPago.class)) {
                continue;
            }


            MercadoPago mercadoPago = clazz.getAnnotation(MercadoPago.class);

            String accessToken = EnvironmentUtil.getEnv(mercadoPago.accessToken());
            MercadoPagoConfig.setAccessToken(accessToken);

            PreferenceRequestUtil preferenceRequestUtil = PreferenceRequestUtil.INSTANCE;
            preferenceRequestUtil.configure(mercadoPago);
        }
    }
}
