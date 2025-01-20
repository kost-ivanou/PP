package javaFiler.filecompressor;

import javaFiler.dto.FileCompressingResult;
import javaFiler.interfaces.FileCompressor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class DefaultFileCompressor implements FileCompressor {
    @Override
    public FileCompressingResult compressData(String content, String filename) throws IOException {
        return new FileCompressingResult(filename, content.getBytes(StandardCharsets.UTF_8));
    }
}
