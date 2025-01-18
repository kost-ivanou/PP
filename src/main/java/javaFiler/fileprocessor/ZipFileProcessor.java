package javaFiler.fileprocessor;

import javaFiler.models.FileProcessor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFileProcessor implements FileProcessor {
    private String archiveFilename;
    @Override
    public byte[] processFile(String content, String originalFilename) throws IOException {
        archiveFilename = "processed_" + originalFilename.replaceFirst("[.][^.]+$", "") + ".zip";
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream, StandardCharsets.UTF_8)) {
            ZipEntry zipEntry = new ZipEntry("processed_" + originalFilename);
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(content.getBytes(StandardCharsets.UTF_8));
            zipOutputStream.closeEntry();
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public String getFilename() {
        return archiveFilename;
    }
}