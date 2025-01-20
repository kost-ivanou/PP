package javaFiler;

import javaFiler.controller.FileController;
import javaFiler.fileprocessor.FileProcessorFactory;
import javaFiler.filereader.FileReaderFactory;
import javaFiler.interfaces.ExpressionEvaluator;
import javaFiler.interfaces.FileProcessor;
import javaFiler.interfaces.FileReader;
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
    private FileProcessorFactory fileProcessorFactory;

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
        // Mocking the input file
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello 2+5 World".getBytes());

        // Mocking behavior for dependencies
        when(fileReaderFactory.createFileReader(file)).thenReturn(fileReader);
        when(fileReader.readFile(file)).thenReturn("Hello 2+5 World");
        when(expressionService.processExpressions("Hello 2+5 World")).thenReturn("Hello 7 World");

        // Mocking the file processor
        FileProcessor mockProcessor = mock(FileProcessor.class);
        when(mockProcessor.processFile("Hello 5+2 World", "test.txt")).thenReturn("Hello 7 World");
        when(fileProcessorFactory.createFileProcessor(false)).thenReturn(mockProcessor);
        when(mockProcessor.getFilename()).thenReturn("processed_test.txt");

        // Performing the request
        mockMvc.perform(multipart("/api/upload")
                        .file(file)
                        .param("action", "defaultFile")) // Ensure action parameter is included
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=\"processed_test.txt\""))
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Hello 7 World")); // Check for the processed content
    }

    @Test
    public void uploadFile_UnsupportedFormat() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.xyz", "application/octet-stream", "Hello World".getBytes());

        // Mock behavior for unsupported format
        when(fileReaderFactory.createFileReader(file)).thenReturn(null);

        mockMvc.perform(multipart("/api/upload")
                        .file(file)
                        .param("action", "defaultFile")) // Add action parameter here
                .andExpect(status().is5xxServerError())
                .andExpect(content().string("Unsupported format"));
    }


}