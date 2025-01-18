package javaFiler.fileprocessor;

import javaFiler.models.FileProcessor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class EncryptFileProcessor implements FileProcessor {
    @Override
    public byte[] processFile(String content, String originalFilename) throws IOException {
        // Implement encryption logic here
        // For demonstration, we'll just return the content as is
        return content.getBytes(StandardCharsets.UTF_8);
    }
    @Override
    public String getFilename(){
        return "";
    }
}