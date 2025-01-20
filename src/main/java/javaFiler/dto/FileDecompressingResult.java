package javaFiler.dto;

public class FileDecompressingResult {
    private String content;
    private String filename;

    public FileDecompressingResult(String content, String filename) {
        this.content = content;
        this.filename = filename;
    }

    public String getContent() {
        return content;
    }

    public String getFilename() {
        return filename;
    }
}
