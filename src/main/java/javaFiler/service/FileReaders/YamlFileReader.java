package javaFiler.service.FileReaders;


import javaFiler.models.FileReader;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class YamlFileReader implements FileReader {
    @Override
    public String readFile(MultipartFile file) throws IOException {
        return new String(file.getBytes(), StandardCharsets.UTF_8);
    }

}
