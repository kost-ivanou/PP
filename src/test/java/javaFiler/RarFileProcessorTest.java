package javaFiler;

import javaFiler.strategy.RarFileProcessor;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RarFileProcessorTest {

    @Test
    public void testProcessFile() throws IOException {
        String content = "This is a test content";
        String originalFilename = "test.txt";

        RarFileProcessor rarFileProcessor = new RarFileProcessor();
        byte[] rarBytes = rarFileProcessor.processFile(content, originalFilename);

        assertArrayEquals(new byte[]{}, rarBytes, "RAR file should not be empty");

    }

    @Test
    public void testProcessFileThrowsIOException() {
        String content = "This is a test content";
        String originalFilename = "test.txt";

        RarFileProcessor rarFileProcessor = new RarFileProcessor();

        // Подменяем путь к RAR-исполняемому файлу на несуществующий
        RarFileProcessor.RAR_EXECUTABLE_PATH = "C:\\InvalidPath\\Rar.exe";

        // Проверяем, что при несуществующем RAR-исполняемом файле выбрасывается IOException
        assertThrows(IOException.class, () -> rarFileProcessor.processFile(content, originalFilename));
    }
}
