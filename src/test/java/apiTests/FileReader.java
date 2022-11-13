package apiTests;

import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;

public class FileReader {


    public String readFile(String fileName) throws Exception {
        File file = ResourceUtils.getFile(fileName);
        String content = new String(Files.readAllBytes(file.toPath()));
        return content;
    }
}
