package com.example.JavaFiler.models;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public class YamlFileReader implements FileReader {
    private final ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
    @Override
    public String readFile(MultipartFile file) throws IOException {
        return objectMapper.readValue(file.getInputStream(), String.class);
    }

}
