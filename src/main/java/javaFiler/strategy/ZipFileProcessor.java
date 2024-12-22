package javaFiler.strategy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFileProcessor implements FileProcessor {
    @Override
    public byte[] processFile(String content, String originalFilename) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
            ZipEntry zipEntry = new ZipEntry("processed_" + originalFilename);
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(content.getBytes(StandardCharsets.UTF_8));
            zipOutputStream.closeEntry();
        }
        return byteArrayOutputStream.toByteArray();
    }

    @Override
    public String getContentType() {
        return "application/zip";
    }
}