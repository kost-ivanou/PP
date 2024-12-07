package com.example.JavaFiler.models;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TxtFileReader implements FileReader {
    @Override
    public String readFile(MultipartFile file) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append(System.lineSeparator()); // Добавляем новую строку
            }
        }

        return contentBuilder.toString().trim(); // Убираем лишние пробелы в конце
    }
}


