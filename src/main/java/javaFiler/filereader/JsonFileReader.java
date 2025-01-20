package javaFiler.filereader;


import com.github.junrar.exception.RarException;
import javaFiler.interfaces.FileReader;
import javaFiler.filedecompressor.PlainFileDecompressor;
import org.springframework.web.multipart.MultipartFile;
import java.nio.charset.StandardCharsets;
import java.io.IOException;


public class JsonFileReader implements FileReader {
    // TODO: implement custom JSON processing logic
    @Override
    public String readContent(String content) throws IOException, RarException {
        return content;
    }
}