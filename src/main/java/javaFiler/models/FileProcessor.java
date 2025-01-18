package javaFiler.models;

import java.io.IOException;

public interface FileProcessor {
    public byte[] processFile(String content, String originalFilename) throws IOException;
    public String getFilename();
}
