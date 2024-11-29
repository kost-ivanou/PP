package com.example.JavaFiler.controller;


import com.example.JavaFiler.service.StringProcessor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String content = new String(file.getBytes(), StandardCharsets.UTF_8);
            String result = expressionService.processExpressions(content);
            return ResponseEntity.ok("Обработанный результат: " + result);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка обработки файла");
        }
    }
}
