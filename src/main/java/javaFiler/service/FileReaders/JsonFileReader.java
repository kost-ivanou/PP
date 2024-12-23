package javaFiler.service.FileReaders;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.junrar.exception.RarException;
import javaFiler.models.FileReader;
import org.springframework.web.multipart.MultipartFile;
import java.nio.charset.StandardCharsets;
import java.io.IOException;


public class JsonFileReader implements FileReader {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override public String readFile(MultipartFile file) throws IOException, RarException {
        String content = new String(file.getBytes(), StandardCharsets.UTF_8);
        if (file.getOriginalFilename().endsWith(".zip")) {
            return ArchiveUtils.extractFileFromZip(file); }
        else if (file.getOriginalFilename().endsWith(".rar")) {
            return ArchiveUtils.extractFileFromRar(file); }
        return content;
    }
}