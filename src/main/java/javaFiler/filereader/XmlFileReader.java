package javaFiler.filereader;

import com.github.junrar.exception.RarException;
import javaFiler.interfaces.FileReader;
import javaFiler.filedecompressor.PlainFileDecompressor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class XmlFileReader implements FileReader {
    // TODO: implement custom XML processing logic
    @Override
    public String readContent(String content) throws IOException, RarException {
        return content;
    }
}
