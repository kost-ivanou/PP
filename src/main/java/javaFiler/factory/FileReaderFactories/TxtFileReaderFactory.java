package javaFiler.factory.FileReaderFactories;


import javaFiler.models.FileReader;
import javaFiler.service.FileReaders.TxtFileReader;

public class TxtFileReaderFactory implements FileReaderFactory {
    @Override
    public FileReader createFileReader() {
        return new TxtFileReader();
    }
}
