package javaFiler;

import javaFiler.controller.FileController;
import javaFiler.filereader.FileReaderFactory;
import javaFiler.models.ExpressionEvaluator;
import javaFiler.models.FileReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class FileControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ExpressionEvaluator expressionService;

    @Mock
    private FileReaderFactory fileReaderFactory;

    @Mock
    private FileReader fileReader;

    @InjectMocks
    private FileController fileController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void uploadFile_Success() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello World".getBytes());
        when(fileReaderFactory.createFileReader(file)).thenReturn(fileReader);
        when(fileReader.readFile(file)).thenReturn("Hello World");
        when(expressionService.processExpressions("Hello 2+5 World")).thenReturn("Hello 7 World"); // Обработка содержимого

        mockMvc.perform(multipart("/api/upload")
                        .file(file))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=\"processed_test.txt\""))
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Hello World"));
    }

    @Test
    public void uploadFile_UnsupportedFormat() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.xyz", "application/octet-stream", "Hello World".getBytes());

        when(fileReaderFactory.createFileReader(file)).thenReturn(null);

        mockMvc.perform(multipart("/api/upload")
                        .file(file))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Unsupported format"));
    }

    /*is not working, still WIP :(
    @Test
    public void uploadFile_InternalServerError() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello World".getBytes());

        FileReaderFactory mockedFactory = mock(FileReaderFactory.class);
        when(fileTypeIdentifier.IdentifyType(file)).thenReturn(mockedFactory);
        when(mockedFactory.createFileReader()).thenReturn(mock(FileReader.class));
        when(fileReader.readFile(file)).thenThrow(new IOException("File read error"));

        mockMvc.perform(multipart("/api/upload")
                        .file(file))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Ошибка обработки файла"));
    }*/
}