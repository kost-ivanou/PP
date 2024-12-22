package javaFiler.strategy;

import java.io.IOException;

public class RarFileProcessor implements FileProcessor {
    @Override
    public byte[] processFile(String content, String originalFilename) throws IOException {
        // Implement RAR archiving logic here using a suitable library
        throw new UnsupportedOperationException("RAR processing not implemented");
    }

    @Override
    public String getContentType() {
        return "application/x-rar-compressed";
    }
}