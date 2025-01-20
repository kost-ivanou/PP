package javaFiler.dto;

public class FileCompressingResult {
    private final String filename;
    private final byte[] bytesContent;

    public FileCompressingResult(String filename, byte[] bytesContent) {
        this.filename = filename;
        this.bytesContent = bytesContent;
    }

    public String getFilename() {
        return filename;
    }

    public byte[] getBytesContent() {
        return bytesContent;
    }

    public String getContentType() {
        if(filename.endsWith(".zip")){
            return "application/zip";
        } else if(filename.endsWith(".rar")){
            return "application/x-rar-compressed";
        } else if(filename.endsWith(".txt")){
            return "text/plain";
        } else if(filename.endsWith(".json")){
            return "application/json";
        } else if(filename.endsWith(".yaml") || filename.endsWith(".yml")){
            return "text/yaml";
        } else if(filename.endsWith(".xml")){
            return "application/xml";
        } else if(filename.endsWith(".enc")){
            return "application/octet-stream";
        } else{
            return "text/plain";
        }
    }
}
