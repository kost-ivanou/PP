package javaFiler.filereader;



import com.github.junrar.exception.RarException;

import javaFiler.interfaces.FileReader;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;


public class FileReaderFactory {
    public FileReader createFileReader(String filename) throws IOException, RarException {
        return determineFileReaderByExtension(getFileExtension(filename));
    }

    private FileReader determineFileReaderByExtension(String extension) {
        if (extension != null) {
            return switch (extension.toLowerCase()) {
                case "yaml", "yml" -> new YamlFileReader();
                case "json" -> new JsonFileReader();
                case "xml" -> new XmlFileReader();
                case "txt" -> new TxtFileReader();
                default -> null;
            };
        }
        return null;
    }

    private static String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex + 1);
        }
        return null;
    }
}

