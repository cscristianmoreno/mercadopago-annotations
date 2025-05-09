package io.github.cscristianmoreno.utils;

import java.io.File;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

public abstract class TomcatUtil {
    
    public static void start(int port) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);

        String property = System.getProperty("user.dir");
        File file = new File(property);

        tomcat.addWebapp("", file.getAbsolutePath());

        tomcat.getConnector();

        tomcat.getConnector();
        tomcat.start();
        tomcat.getServer().await();
    }
}
