package javaFiler.interfaces;

import java.io.IOException;

public interface FileProcessor {
    public String processFile(String content, String originalFilename) throws IOException;
    public String getFilename();
}
