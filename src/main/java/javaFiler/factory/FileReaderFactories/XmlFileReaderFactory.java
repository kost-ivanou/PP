package javaFiler.factory.FileReaderFactories;


import javaFiler.models.FileReader;
import javaFiler.service.FileReaders.XmlFileReader;

public class XmlFileReaderFactory implements FileReaderFactory {
    @Override
    public FileReader createFileReader() {
        return new XmlFileReader();
    }
}
