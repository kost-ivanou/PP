package javaFiler.interfaces;

import javaFiler.dto.FileCompressingResult;

import java.io.IOException;

public interface FileCompressor {
    public FileCompressingResult compressData(String content, String filename) throws IOException;
}
