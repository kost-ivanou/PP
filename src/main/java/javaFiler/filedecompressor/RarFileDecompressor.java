package javaFiler.filedecompressor;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;
import javaFiler.dto.FileDecompressingResult;
import javaFiler.interfaces.FileDecompressor;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class RarFileDecompressor implements FileDecompressor {
    @Override
    public FileDecompressingResult decompressData(MultipartFile file) throws IOException, RarException {

        File tempFile = File.createTempFile("temp", ".rar");
        Files.write(tempFile.toPath(), file.getBytes());

        try (Archive archive = new Archive(tempFile)) {
            FileHeader fileHeader;
            while ((fileHeader = archive.nextFileHeader()) != null) {
                if (!fileHeader.isDirectory() && ExtensionValidator.isSupportedFormat(fileHeader.getFileNameString())) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    archive.extractFile(fileHeader, baos);
                    return new FileDecompressingResult(baos.toString(StandardCharsets.UTF_8).trim(), fileHeader.getFileNameString());
                }
            }
        }
        return null;
    }
}
