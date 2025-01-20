package javaFiler.filecompressor;

import javaFiler.interfaces.FileCompressor;

public class FileCompressorFactory {
    public FileCompressor createCompressorFactory(String compressing){
        return switch (compressing) {
            case "rar" -> new RarFileCompressor();
            case "zip" -> new ZipFileCompressor();
            case "plain" -> new DefaultFileCompressor();
            default -> null;
        };
    }
}
