package javaFiler.FileReaderTests;

import javaFiler.service.FileReaders.YamlFileReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class YamlFileReaderTests {

    private YamlFileReader yamlFileReader;

    @BeforeEach
    public void setUp() {
        yamlFileReader = new YamlFileReader();
    }

    @Test
    public void testReadFile_Success() throws IOException {

        String content = "key: value\nanother_key: another_value";
        MultipartFile file = new MockMultipartFile("file", "test.yaml", "application/x-yaml", content.getBytes(StandardCharsets.UTF_8));

        String result = yamlFileReader.readFile(file);

        assertEquals(content, result);
    }

    @Test
    public void testReadFile_ThrowsIOException() {
        MultipartFile file = Mockito.mock(MultipartFile.class);
        try {
            Mockito.when(file.getBytes()).thenThrow(new IOException("File read error"));
        } catch (IOException e) {
            //ignore
        }

        assertThrows(IOException.class, () -> yamlFileReader.readFile(file));
    }
}