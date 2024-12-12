package javaFiler.controller;


import javaFiler.factory.FileReaderFactories.FileReaderFactory;
import javaFiler.models.FileReader;
import javaFiler.service.FileFactoryIdentifier;
import javaFiler.service.StringProcessor;
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

            FileFactoryIdentifier fileFactoryIdentifier = new FileFactoryIdentifier();

            FileReaderFactory factory = fileFactoryIdentifier.IdentifyType(file);
            if(factory == null) return ResponseEntity.badRequest().body("Unsupported format".getBytes(StandardCharsets.UTF_8));

            FileReader reader = factory.createFileReader();

            String content = reader.readFile(file);

            processedContent = expressionService.evaluateExpressions(content);

            byte[] outputBytes = processedContent.getBytes(StandardCharsets.UTF_8);

            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"processed_" + file.getOriginalFilename() + "\"")
                    .contentType(contentType != null ? MediaType.parseMediaType(contentType) : MediaType.APPLICATION_OCTET_STREAM)
                    .body(outputBytes);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка обработки файла".getBytes(StandardCharsets.UTF_8));
        }
    }
}