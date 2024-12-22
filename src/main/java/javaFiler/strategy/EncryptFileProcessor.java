package javaFiler.strategy;

import java.nio.charset.StandardCharsets;

public class EncryptFileProcessor implements FileProcessor {
    @Override
    public byte[] processFile(String content, String originalFilename) {
        // Implement encryption logic here
        // For demonstration, we'll just return the content as is
        return content.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String getContentType() {
        return "application/octet-stream"; // Change as needed
    }
}