package javaFiler.interfaces;

import com.github.junrar.exception.RarException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileReader {
    String readContent(String content) throws IOException, RarException;
}
