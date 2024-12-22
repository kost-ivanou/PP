package javaFiler.strategy;

import java.io.IOException;

public interface FileProcessor {
    byte[] processFile(String content, String originalFilename) throws IOException;
    String getContentType();
}