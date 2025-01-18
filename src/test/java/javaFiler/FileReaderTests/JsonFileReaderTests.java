package javaFiler.FileReaderTests;

import com.github.junrar.exception.RarException;
import javaFiler.filereader.JsonFileReader;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonFileReaderTests {

    private JsonFileReader jsonFileReader;

    @BeforeEach
    public void setUp() {
        jsonFileReader = new JsonFileReader();
    }

    @Test
    public void testReadFile_Success() throws IOException, RarException {
        String content = "{\"key\":\"value\"}";
        MultipartFile file = new MockMultipartFile("file", "test.json", "application/json", content.getBytes(StandardCharsets.UTF_8));

        String result = jsonFileReader.readFile(file);

        assertEquals(content, result);
    }

    @Test
    public void testReadFile_ThrowsIOException() {
        MultipartFile file = Mockito.mock(MultipartFile.class);
        try {
            Mockito.when(file.getBytes()).thenThrow(new IOException("File read error"));
        } catch (IOException e) {
            // ignore
        }

        assertThrows(IOException.class, () -> jsonFileReader.readFile(file));
    }
}
