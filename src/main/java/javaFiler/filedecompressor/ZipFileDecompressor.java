package javaFiler.filedecompressor;

import javaFiler.dto.FileDecompressingResult;
import javaFiler.interfaces.FileDecompressor;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class ZipFileDecompressor implements FileDecompressor {
    @Override
    public FileDecompressingResult decompressData(MultipartFile file) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(file.getInputStream())) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (!entry.isDirectory() && ExtensionValidator.isSupportedFormat(entry.getName())) {
                    StringBuilder contentBuilder = new StringBuilder();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(zipInputStream, StandardCharsets.UTF_8));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        contentBuilder.append(line).append(System.lineSeparator());
                    }
                    return new FileDecompressingResult(contentBuilder.toString().trim(), entry.getName()) ;
                }
            }
        }
        return null;
    }
}
