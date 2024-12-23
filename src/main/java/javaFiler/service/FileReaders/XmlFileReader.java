package javaFiler.service.FileReaders;

import com.github.junrar.exception.RarException;
import javaFiler.models.FileReader;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class XmlFileReader implements FileReader {
    @Override
    public String readFile(MultipartFile file) throws IOException, RarException {
        String content = new String(file.getBytes(), StandardCharsets.UTF_8);
        if (file.getOriginalFilename().endsWith(".zip")) {
            return ArchiveUtils.extractFileFromZip(file); }
        else if (file.getOriginalFilename().endsWith(".rar")) {
            return ArchiveUtils.extractFileFromRar(file); }
        return content;
    }
}
