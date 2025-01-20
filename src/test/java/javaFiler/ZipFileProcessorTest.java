package javaFiler;

import javaFiler.filecompressor.ZipFileCompressor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ZipFileProcessorTest {
    private ZipFileCompressor zipFileCompressor;

    @BeforeEach
    public void setUp() {
        zipFileCompressor = new ZipFileCompressor();
    }

    @Test
    public void testProcessFile() throws IOException {
        String content = "Test content";
        String originalFilename = "testfile.txt";

        // Process the file
        byte[] zipData = zipFileCompressor.compressData(content, originalFilename).getBytesContent();
        String filename = zipFileCompressor.compressData(content, originalFilename).getFilename();

        // Verify the filename
        String expectedFilename = "processed_testfile.zip";
        assertEquals(expectedFilename, filename);

        // Verify the contents of the zip file
        try (ZipInputStream zipInputStream = new ZipInputStream(new ByteArrayInputStream(zipData))) {
            ZipEntry entry = zipInputStream.getNextEntry();
            assertEquals("processed_testfile.txt", entry.getName());

            byte[] buffer = new byte[1024];
            StringBuilder stringBuilder = new StringBuilder();
            int len;
            while ((len = zipInputStream.read(buffer)) != -1) {
                stringBuilder.append(new String(buffer, 0, len, StandardCharsets.UTF_8));
            }

            assertEquals(content, stringBuilder.toString());
            zipInputStream.closeEntry();
        }
    }

    @Test
    public void testGetFilename() throws IOException {
        String content = "Another test content";
        String originalFilename = "anotherfile.txt";

        String filename = zipFileCompressor.compressData(content, originalFilename).getFilename();

        String expectedFilename = "processed_anotherfile.zip";
        assertEquals(expectedFilename, filename);
    }
}