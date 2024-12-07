package com.example.JavaFiler.models;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class XmlFileReader implements FileReader {
    private final XmlMapper xmlMapper = new XmlMapper();
    @Override
    public String readFile(MultipartFile file) throws IOException {
        return xmlMapper.readValue(file.getInputStream(), String.class);
    }

}
