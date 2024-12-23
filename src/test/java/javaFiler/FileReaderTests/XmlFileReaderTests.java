package javaFiler.FileReaderTests;

import com.github.junrar.exception.RarException;
import javaFiler.service.FileReaders.XmlFileReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class XmlFileReaderTests {

    private XmlFileReader xmlFileReader;

    @BeforeEach
    public void setUp() {
        xmlFileReader = new XmlFileReader();
    }

    @Test
    public void testReadFile_Success() throws IOException, RarException {
        String content = "<note><to>Tove</to><from>Jani</from><heading>Reminder</heading><body>Don't forget me this weekend!</body></note>";
        MultipartFile file = new MockMultipartFile("file", "test.xml", "application/xml", content.getBytes(StandardCharsets.UTF_8));

        String result = xmlFileReader.readFile(file);

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


        assertThrows(IOException.class, () -> xmlFileReader.readFile(file));
    }
}