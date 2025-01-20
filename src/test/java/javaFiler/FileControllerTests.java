package javaFiler;

import javaFiler.controller.FileController;
import javaFiler.dto.FileCompressingResult;
import javaFiler.dto.FileDecompressingResult;
import javaFiler.filecompressor.FileCompressorFactory;
import javaFiler.filedecompressor.FileDecompressorFactory;
import javaFiler.fileprocessor.FileProcessorFactory;
import javaFiler.filereader.FileReaderFactory;
import javaFiler.interfaces.*;
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

    @Mock
    private FileCompressorFactory compressorFactory;

    @Mock
    private FileDecompressorFactory decompressorFactory;

    @Mock
    private FileDecompressor decompressor;

    @InjectMocks
    private FileController fileController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    // TODO
    /*@Test
    public void uploadFile_Success() throws Exception {
        // Mocking the input file
        MockMultipartFile file = new MockMultipartFile("file", "test.zip", "application/zip", "Hello 2+5 World".getBytes());

        // Mocking behavior for dependencies
        when(decompressorFactory.createFileDecompressor("test.zip")).thenReturn(decompressor);

        // Ensure the decompressor returns a valid result
        when(decompressor.decompressData(file)).thenReturn(new FileDecompressingResult("Hello 2+5 World", "test.txt"));

        when(fileReaderFactory.createFileReader("test.txt")).thenReturn(fileReader);
        when(fileReader.readContent("Hello 2+5 World")).thenReturn("Hello 2+5 World");

        // Mocking expression evaluation
        when(expressionService.processExpressions("Hello 2+5 World")).thenReturn("Hello 7 World");

        // Mocking the file processor
        FileProcessor mockProcessor = mock(FileProcessor.class);
        when(mockProcessor.processFile("Hello 7 World", "test.txt")).thenReturn("Encrypted content");
        when(fileProcessorFactory.createFileProcessor(false)).thenReturn(mockProcessor);
        when(mockProcessor.getFilename()).thenReturn("processed_test.txt");

        // Mocking the compressor
        FileCompressor mockCompressor = mock(FileCompressor.class);
        when(mockCompressor.compressData(any(String.class), eq("processed_test.txt")))
                .thenReturn(new FileCompressingResult("compressed_test.zip", "Compressed content".getBytes()));
        when(compressorFactory.createCompressorFactory("plain")).thenReturn(mockCompressor);

        // Performing the request
        mockMvc.perform(multipart("/api/upload")
                        .file(file)
                        .param("action", "plain")
                        .param("encrypt", "false"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Disposition", "attachment; filename=\"compressed_test.zip\""))
                .andExpect(content().contentType("application/zip"))
                .andExpect(content().bytes("Compressed content".getBytes())); // Check for the processed content
    }*/
    @Test
    public void uploadFile_UnsupportedFormat() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.xyz", "application/octet-stream", "Hello World".getBytes());

        // Mock behavior for unsupported format
        when(decompressorFactory.createFileDecompressor("test.xyz")).thenReturn(null);

        mockMvc.perform(multipart("/api/upload")
                        .file(file)
                        .param("action", "defaultFile")
                        .param("encrypt", "false")) // Ensure encrypt parameter is included
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Unsupported format"));
    }
}