package javaFiler.filereader;


import javaFiler.models.FileReader;
import org.springframework.web.multipart.MultipartFile;

public class FileReaderFactory {
    FileReader reader;
    public FileReader createFileReader(MultipartFile file){
        String contentType = file.getContentType();
        String filename = file.getOriginalFilename();

        boolean isExtensionYaml = filename != null && (filename.endsWith(".yaml") || filename.endsWith(".yml"));

        if(isExtensionYaml){
            reader = new YamlFileReader();
            return reader;
        }
        switch (contentType) {
            case "application/json":
            case "text/json":
                reader = new JsonFileReader();
                break;
            case "application/xml":
            case "text/xml":
                reader= new XmlFileReader();
                break;
            case "text/plain":
                reader = new TxtFileReader();
                break;
            default:
                return null;
        }
        return reader;
    }
}

