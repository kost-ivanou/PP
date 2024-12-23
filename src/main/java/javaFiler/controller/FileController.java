package javaFiler.controller;

import com.github.junrar.exception.RarException;
import javaFiler.factory.FileReaderFactories.FileReaderFactory;
import javaFiler.models.FileReader;
import javaFiler.service.FileFactoryIdentifier;
import javaFiler.service.StringProcessor;
import javaFiler.strategy.*;
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
            String originalFilename = file.getOriginalFilename();
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
            byte[] outputBytes;
            String archiveFilename;

            switch (action) {
                case "zip":
                    context.setStrategy(new ZipFileProcessor());
                    context.setContentType("application/zip");
                    archiveFilename = "processed_" + originalFilename.replaceFirst("[.][^.]+$", "") + ".zip";
                    outputBytes = context.executeStrategy(processedContent, originalFilename);
                    return ResponseEntity.ok()
                            .header("Content-Disposition", "attachment; filename=\"" + archiveFilename + "\"")
                            .contentType(MediaType.APPLICATION_OCTET_STREAM)
                            .body(outputBytes);
                case "rar":
                    context.setStrategy(new RarFileProcessor());
                    context.setContentType("application/x-rar-compressed");
                    archiveFilename = "processed_" + originalFilename.replaceFirst("[.][^.]+$", "") + ".rar";
                    outputBytes = context.executeStrategy(processedContent, originalFilename);
                    return ResponseEntity.ok()
                            .header("Content-Disposition", "attachment; filename=\"" + archiveFilename + "\"")
                            .contentType(MediaType.APPLICATION_OCTET_STREAM)
                            .body(outputBytes);
                case "encrypt":
                    context.setStrategy(new EncryptFileProcessor());
                    context.setContentType("application/octet-stream");
                    archiveFilename = "processed_" + originalFilename;
                    outputBytes = context.executeStrategy(processedContent, originalFilename);
                    return ResponseEntity.ok()
                            .header("Content-Disposition", "attachment; filename=\"" + archiveFilename + "\"")
                            .contentType(MediaType.APPLICATION_OCTET_STREAM)
                            .body(outputBytes);
                case "defaultFile":
                    context.setStrategy(new DefaultFileProcessor());
                    context.setContentType(contentType);
                    outputBytes = context.executeStrategy(processedContent, originalFilename);
                    return ResponseEntity.ok()
                            .header("Content-Disposition", "attachment; filename=\"" + originalFilename + "\"")
                            .contentType(MediaType.parseMediaType(contentType))
                            .body(outputBytes);
                default:
                    return ResponseEntity.badRequest().body("Invalid action".getBytes(StandardCharsets.UTF_8));
            }

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка обработки файла".getBytes(StandardCharsets.UTF_8));

        } catch (RarException e) {
            throw new RuntimeException(e);
        }
    }
}