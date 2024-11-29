package com.example.JavaFiler.factory;


import com.example.JavaFiler.models.FileReader;
import com.example.JavaFiler.models.JsonFileReader;

public class JsonFileReaderFactory implements FileReaderFactory {
    @Override
    public FileReader createFileReader() {
        return new JsonFileReader();
    }
}

