package javaFiler.FileReaderTests;

import com.github.junrar.exception.RarException;
import javaFiler.filereader.TxtFileReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TxtFileReaderTests {

    private TxtFileReader txtFileReader;

    @BeforeEach
    public void setUp() {
        txtFileReader = new TxtFileReader();
    }

    @Test
    public void testReadFile_Success() throws IOException, RarException {
        String content = "Hello\r\nWorld\r\nThis is a test.";
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", content.getBytes());

        String result = txtFileReader.readFile(file);

        String normalizedContent = content.replace("\r\n", "\n").replace("\r", "\n");
        String normalizedResult = result.replace("\r\n", "\n").replace("\r", "\n");

        assertEquals(normalizedContent, normalizedResult);
    }

    @Test
    public void testReadFile_ThrowsIOException() {
        MultipartFile file = Mockito.mock(MultipartFile.class);
        try {
            Mockito.when(file.getInputStream()).thenThrow(new IOException("File read error"));
        } catch (IOException e) {
            // ignore
        }
        assertThrows(IOException.class, () -> txtFileReader.readFile(file));
    }
}