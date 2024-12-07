package com.example.JavaFiler.service;

import com.example.JavaFiler.factory.*;
import org.springframework.web.multipart.MultipartFile;

public class FileFactoryIdentifier {
    public FileReaderFactory IdentifyType(MultipartFile file) {
        String contentType = file.getContentType();
        FileReaderFactory factory;
        switch (contentType) {
            case "application/json":
            case "text/json":
                factory = new JsonFileReaderFactory();
                break;
            case "application/xml":
            case "text/xml":
                factory = new XmlFileReaderFactory();
                break;
            case "text/plain":
                factory = new TxtFileReaderFactory();
                break;
            case "application/x-yaml":
            case "text/yaml":
            case "text/x-yaml":
                factory = new YamlFileReaderFactory();
                break;
            default:
                return null;
        }
        return factory;
    }
}