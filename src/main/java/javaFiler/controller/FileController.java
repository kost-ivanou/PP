package javaFiler.controller;

import javaFiler.factory.FileReaderFactories.FileReaderFactory;
import javaFiler.models.FileReader;
import javaFiler.service.FileFactoryIdentifier;
import javaFiler.service.StringProcessor;
import javaFiler.strategy.EncryptFileProcessor;
import javaFiler.strategy.FileProcessingContext;
import javaFiler.strategy.RarFileProcessor;
import javaFiler.strategy.ZipFileProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/api")
public class FileController {

    @PostMapping("/upload")
    public ResponseEntity<byte[]> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("action") String action) {
        try {
            String processedContent;
            String contentType = file.getContentType();

            StringProcessor expressionService = new StringProcessor();
            FileFactoryIdentifier fileFactoryIdentifier = new FileFactoryIdentifier();
            FileReaderFactory factory = fileFactoryIdentifier.IdentifyType(file);
            if (factory == null) {
                return ResponseEntity.badRequest().body("Unsupported format".getBytes(StandardCharsets.UTF_8));
            }

            FileReader reader = factory.createFileReader();
            String content = reader.readFile(file);
            processedContent = expressionService.evaluateExpressions(content);
            FileProcessingContext context = new FileProcessingContext();

            switch (action) {
                case "zip":
                    context.setStrategy(new ZipFileProcessor());
                    break;
                case "rar":
                    context.setStrategy(new RarFileProcessor());
                    break;
                case "encrypt":
                    context.setStrategy(new EncryptFileProcessor());
                    break;
                default:
                    return ResponseEntity.badRequest().body("Invalid action".getBytes(StandardCharsets.UTF_8));
            }

            byte[] outputBytes = context.executeStrategy(processedContent, file.getOriginalFilename());
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"processed_" + file.getOriginalFilename() + "\"")
                    .contentType(MediaType.valueOf(context.getContentType()))
                    .body(outputBytes);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка обработки файла".getBytes(StandardCharsets.UTF_8));
        }
    }
}