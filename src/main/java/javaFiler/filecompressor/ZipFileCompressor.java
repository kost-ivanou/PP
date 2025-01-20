package javaFiler.filecompressor;

import javaFiler.dto.FileCompressingResult;
import javaFiler.interfaces.FileCompressor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFileCompressor implements FileCompressor {
    @Override
    public FileCompressingResult compressData(String content, String originalFilename) throws IOException {
        String archiveFilename = "processed_" + originalFilename.replaceFirst("[.][^.]+$", "") + ".zip";
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream, StandardCharsets.UTF_8)) {
            ZipEntry zipEntry = new ZipEntry("processed_" + originalFilename);
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(content.getBytes(StandardCharsets.UTF_8));
            zipOutputStream.closeEntry();
        }
        return new FileCompressingResult(archiveFilename, byteArrayOutputStream.toByteArray());
    }
}