package com.example.JavaFiler.controller;


import com.example.JavaFiler.factory.*;
import com.example.JavaFiler.models.FileReader;
import com.example.JavaFiler.service.FileFactoryIdentifier;
import com.example.JavaFiler.service.StringProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


@RestController
@RequestMapping("/api")
public class FileController {

    private StringProcessor expressionService;

    @PostMapping("/upload")
    public ResponseEntity<byte[]> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String contentType = file.getContentType();
            String processedContent;

            StringProcessor expressionService = new StringProcessor();

            FileReaderFactory factory;

            FileFactoryIdentifier fileFactoryIdentifier = new FileFactoryIdentifier();

            factory = fileFactoryIdentifier.IdentifyType(file);
            if(factory == null) return ResponseEntity.badRequest().body("Неподдерживаемый формат файла".getBytes(StandardCharsets.UTF_8));

            FileReader reader = factory.createFileReader();

            String content = reader.readFile(file);

            processedContent = expressionService.processExpressions(content); // Предполагается, что у вас есть метод для обработки

            byte[] outputBytes = processedContent.getBytes(StandardCharsets.UTF_8);

            // Устанавливаем заголовки ответа
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"processed_" + file.getOriginalFilename() + "\"")
                    .contentType(contentType != null ? MediaType.parseMediaType(contentType) : MediaType.APPLICATION_OCTET_STREAM)
                    .body(outputBytes);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка обработки файла".getBytes(StandardCharsets.UTF_8));
        }
    }
}