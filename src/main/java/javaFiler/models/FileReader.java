package javaFiler.models;

import com.github.junrar.exception.RarException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface FileReader {
    String readFile(MultipartFile file) throws IOException, RarException;
}
