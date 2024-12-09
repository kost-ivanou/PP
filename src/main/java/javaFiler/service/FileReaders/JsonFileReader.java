package javaFiler.service.FileReaders;


import com.fasterxml.jackson.databind.ObjectMapper;
import javaFiler.models.FileReader;
import org.springframework.web.multipart.MultipartFile;
import java.nio.charset.StandardCharsets;
import java.io.IOException;


public class JsonFileReader implements FileReader {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public String readFile(MultipartFile file) throws IOException {
            return new String(file.getBytes(), StandardCharsets.UTF_8);
    }
}
