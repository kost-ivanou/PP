package javaFiler.service;

import javaFiler.factory.FileReaderFactories.*;
import org.springframework.web.multipart.MultipartFile;

public class FileFactoryIdentifier {
    public FileReaderFactory IdentifyType(MultipartFile file) {
        String contentType = file.getContentType();
        String filename = file.getOriginalFilename();

        boolean isExtensionYaml = filename != null && (filename.endsWith(".yaml") || filename.endsWith(".yml"));

        FileReaderFactory factory;
        if(isExtensionYaml){
            factory = new YamlFileReaderFactory();
            return factory;
        }
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
            default:
                return null;
        }
        return factory;
    }
}