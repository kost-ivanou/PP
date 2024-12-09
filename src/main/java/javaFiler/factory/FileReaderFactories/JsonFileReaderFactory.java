package javaFiler.factory.FileReaderFactories;


import javaFiler.models.FileReader;
import javaFiler.service.FileReaders.JsonFileReader;

public class JsonFileReaderFactory implements FileReaderFactory {
    @Override
    public FileReader createFileReader() {
        return new JsonFileReader();
    }
}

