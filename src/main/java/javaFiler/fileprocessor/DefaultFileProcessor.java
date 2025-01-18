package javaFiler.fileprocessor;


import javaFiler.models.FileProcessor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class DefaultFileProcessor implements FileProcessor {
    private String contentType;
    private String filename;
    @Override
    public byte[] processFile(String content, String originalFilename) throws IOException {
        filename = originalFilename;
        return content.getBytes(StandardCharsets.UTF_8);
    }
    @Override
    public String getFilename(){
        return filename;
    }
}
