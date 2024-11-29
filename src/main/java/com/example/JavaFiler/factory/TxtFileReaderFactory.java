package com.example.JavaFiler.factory;


import com.example.JavaFiler.models.FileReader;
import com.example.JavaFiler.models.TxtFileReader;

public class TxtFileReaderFactory implements FileReaderFactory {
    @Override
    public FileReader createFileReader() {
        return new TxtFileReader();
    }
}
