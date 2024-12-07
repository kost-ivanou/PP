package com.example.JavaFiler.models;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public class JsonFileReader implements FileReader {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public String readFile(MultipartFile file) throws IOException {
            return objectMapper.readValue(file.getInputStream(), String.class);
    }
}
