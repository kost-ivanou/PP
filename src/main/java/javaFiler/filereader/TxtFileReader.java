package javaFiler.filereader;

import com.github.junrar.exception.RarException;
import javaFiler.models.FileReader;
import javaFiler.service.ArchiveUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TxtFileReader implements FileReader {
    @Override
    public String readFile(MultipartFile file) throws IOException, RarException {
        if (file.getOriginalFilename().endsWith(".zip")) {
            return ArchiveUtils.extractFileFromZip(file);
        }
        else if (file.getOriginalFilename().endsWith(".rar")) {
            return ArchiveUtils.extractFileFromRar(file);
        }
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream())))
        {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append(System.lineSeparator());
            }
        }
        return contentBuilder.toString().trim();
    }
}


