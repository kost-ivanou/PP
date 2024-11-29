package com.example.JavaFiler.factory;


import com.example.JavaFiler.models.FileReader;
import com.example.JavaFiler.models.YamlFileReader;

public class YamlFileReaderFactory implements FileReaderFactory {
    @Override
    public FileReader createFileReader() {
        return new YamlFileReader();
    }
}
