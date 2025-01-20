package javaFiler.fileprocessor;


import javaFiler.interfaces.FileProcessor;

import java.io.IOException;

public class DefaultFileProcessor implements FileProcessor {
    private String contentType;
    private String filename;
    @Override
    public String processFile(String content, String originalFilename) throws IOException {
        filename = originalFilename;
        return content;
    }
    @Override
    public String getFilename(){
        return "processed_" + filename;
    }
}
