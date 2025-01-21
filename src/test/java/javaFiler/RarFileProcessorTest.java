package javaFiler;

import javaFiler.filecompressor.RarFileCompressor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RarFileCompressor.class)
public class RarFileProcessorTest {

    private RarFileCompressor rarFileProcessor;
    // TODO
    /*
    @Before
    public void setUp() {
        rarFileProcessor = new RarFileCompressor();
    }
    @Test
    public void testProcessFileFailure() throws Exception {
        // Mock the Process to simulate an error
        Process mockProcess = Mockito.mock(Process.class);
        when(mockProcess.waitFor()).thenThrow(new InterruptedException());
        when(mockProcess.exitValue()).thenReturn(1);

        // Mock Runtime.exec() to return the mock process
        PowerMockito.mockStatic(Runtime.class);
        when(Runtime.getRuntime().exec(anyString())).thenReturn(mockProcess);

        // Prepare test data
        String content = "Test content";
        String originalFilename = "testfile.txt";

        // Expect an IOException
        IOException exception = assertThrows(IOException.class, () -> {
            rarFileProcessor.processFile(content, originalFilename);
        });

        assertEquals("Error creating RAR archive", exception.getMessage());
    }

    @Test
    public void testProcessFileSuccess() throws Exception {
        // Mock the Process
        Process mockProcess = Mockito.mock(Process.class);
        when(mockProcess.waitFor()).thenReturn(0);
        when(mockProcess.exitValue()).thenReturn(0);

        // Mock Runtime.exec() to return the mock process
        PowerMockito.mockStatic(Runtime.class);
        when(Runtime.getRuntime().exec(anyString())).thenReturn(mockProcess);

        // Prepare test data
        String content = "Test content";
        String originalFilename = "testfile.txt";

        // Process the file
        byte[] rarData = rarFileProcessor.processFile(content, originalFilename);

        // Verify the filename
        String expectedFilename = "processed_testfile.rar";
        assertEquals(expectedFilename, rarFileProcessor.getFilename());

        // Verify if exec was called
        PowerMockito.verifyStatic(Runtime.class);
        Runtime.getRuntime().exec(contains(RarFileCompressor.RAR_EXECUTABLE_PATH));
    }



    @Test
    public void testGetFilename() throws Exception {
        String content = "Another test content";
        String originalFilename = "anotherfile.txt";

        rarFileProcessor.processFile(content, originalFilename);

        String expectedFilename = "processed_anotherfile.rar";
        assertEquals(expectedFilename, rarFileProcessor.getFilename());
    }*/
}
