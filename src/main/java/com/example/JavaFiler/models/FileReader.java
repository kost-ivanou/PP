package com.example.JavaFiler.models;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface FileReader {
    String readFile(MultipartFile file) throws IOException;
}
