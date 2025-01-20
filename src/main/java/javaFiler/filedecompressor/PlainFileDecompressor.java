package javaFiler.filedecompressor;

import java.io.*;
import com.github.junrar.exception.RarException;

import javaFiler.dto.FileDecompressingResult;
import javaFiler.interfaces.FileDecompressor;
import org.springframework.web.multipart.MultipartFile;



public class PlainFileDecompressor implements FileDecompressor {
    @Override
    public FileDecompressingResult decompressData(MultipartFile file) throws IOException, RarException {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream())))
        {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append(System.lineSeparator());
            }
        }
        return new FileDecompressingResult(contentBuilder.toString().trim(), file.getOriginalFilename()) ;
    }





}
