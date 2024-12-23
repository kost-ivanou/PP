package javaFiler.strategy;


import java.nio.charset.StandardCharsets;

public class DefaultFileProcessor implements FileProcessor {
    private String contentType;
    @Override
    public byte[] processFile(String content, String originalFilename) {
        return content.getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public String getContentType() {
        return contentType;
    }
}
