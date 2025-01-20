package javaFiler.filedecompressor;

import javaFiler.interfaces.FileDecompressor;

public class FileDecompressorFactory {
    public FileDecompressor createFileDecompressor(String filename){
        if (filename.endsWith(".zip")) {
            return new ZipFileDecompressor();
        } else if (filename.endsWith(".rar")) {
            return new RarFileDecompressor();
        } else {
            return new PlainFileDecompressor();
        }
    }
}
