package javaFiler.strategy;

import javaFiler.strategy.FileProcessor;

import java.io.IOException;

public class FileProcessingContext {
    private String contentType;
    private FileProcessor fileProcessor;

    public void setStrategy(FileProcessor fileProcessor) {
        this.fileProcessor = fileProcessor;
    }

    public byte[] executeStrategy(String content, String originalFilename) throws IOException {
        return fileProcessor.processFile(content, originalFilename);
    }

    public String getContentType() {
        return contentType;
    }
    public void setContentType(String contentType){
        this.contentType = contentType;
    }
}
