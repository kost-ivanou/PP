package com.example.JavaFiler.factory;


import com.example.JavaFiler.models.FileReader;
import com.example.JavaFiler.models.XmlFileReader;

public class XmlFileReaderFactory implements FileReaderFactory {
    @Override
    public FileReader createFileReader() {
        return new XmlFileReader();
    }
}
