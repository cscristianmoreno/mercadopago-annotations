package io.github.cscristianmoreno.services;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class ClassScanServiceTest {
    
    /** 
     * @throws URISyntaxException
     * @throws IOException
     */
    @Test
    public void testGetClassList() throws URISyntaxException, IOException {
        Path paths = Paths.get(getClass().getResource("/").toURI()).getParent();

        List<File> files = Files.walk(paths.resolve("classes").resolve("io"))
        .filter(Files::isRegularFile)
        .map(Path::toFile)
        .collect(Collectors.toList());

        for (File file: files) {
            String path  = file.getAbsolutePath();
            String classes = "classes" + File.separator;
            int classPosition = path.indexOf("classes");
            String classPath = path.substring(classPosition).replace(classes, "").replace(File.separator, ".");
            String packageClass = classPath.replace(".class", "");
            System.out.println(packageClass);
        }
    }

    @Test
    public void testScan() {

    }
}
