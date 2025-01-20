package javaFiler.filereader;

import com.github.junrar.exception.RarException;
import javaFiler.interfaces.FileReader;
import javaFiler.filedecompressor.PlainFileDecompressor;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TxtFileReader implements FileReader {
    @Override
    public String readContent(String content) throws IOException, RarException {
        return content;
    }
}


