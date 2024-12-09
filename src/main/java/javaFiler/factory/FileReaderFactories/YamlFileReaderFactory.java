package javaFiler.factory.FileReaderFactories;


import javaFiler.models.FileReader;
import javaFiler.service.FileReaders.YamlFileReader;

public class YamlFileReaderFactory implements FileReaderFactory {
    @Override
    public FileReader createFileReader() {
        return new YamlFileReader();
    }
}
