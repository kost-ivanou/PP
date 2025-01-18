package javaFiler.service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.rarfile.FileHeader;
import org.springframework.web.multipart.MultipartFile;

public class ArchiveUtils {
    public static String extractFileFromZip(MultipartFile file) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(file.getInputStream())) {
            ZipEntry entry;
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (!entry.isDirectory() && entry.getName().endsWith(".txt")) { // Извлекаем только текстовые файлы
                    StringBuilder contentBuilder = new StringBuilder();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(zipInputStream, StandardCharsets.UTF_8));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        contentBuilder.append(line).append(System.lineSeparator());
                    }
                    return contentBuilder.toString().trim();
                }
            }
        }
        return null;
    }

    public static String extractFileFromRar(MultipartFile file) throws IOException, RarException {
        File tempFile = File.createTempFile("temp", ".rar");
        Files.write(tempFile.toPath(), file.getBytes());

        try (Archive archive = new Archive(tempFile)) {
            FileHeader fileHeader;
            while ((fileHeader = archive.nextFileHeader()) != null) {
                if (!fileHeader.isDirectory() && fileHeader.getFileNameString().endsWith(".txt")) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    archive.extractFile(fileHeader, baos);
                    return new String(baos.toByteArray(), StandardCharsets.UTF_8).trim();
                }
            }
        }
        return null;
    }
}
