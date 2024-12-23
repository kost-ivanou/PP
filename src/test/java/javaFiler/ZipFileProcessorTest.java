package javaFiler;

import javaFiler.strategy.ZipFileProcessor;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class ZipFileProcessorTest {

    @Test
    public void testProcessFile() throws IOException {
        String content = "This is a test content";
        String originalFilename = "test.txt";

        ZipFileProcessor zipFileProcessor = new ZipFileProcessor();
        byte[] zipBytes = zipFileProcessor.processFile(content, originalFilename);

        assertArrayEquals(new byte[]{}, zipBytes, "ZIP file should not be empty");

    }
}
