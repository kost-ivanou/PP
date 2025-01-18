package javaFiler.controller;

import com.github.junrar.exception.RarException;
import javaFiler.expressioneval.ExpressionEvaluatorFactory;
import javaFiler.filereader.FileReaderFactory;
import javaFiler.models.ExpressionEvaluator;
import javaFiler.models.FileProcessor;
import javaFiler.models.FileReader;
import javaFiler.fileprocessor.*;
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

    @PostMapping("/upload")
    public ResponseEntity<byte[]> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("action") String action) {
        try {
            String processedContent;
            String originalFilename = file.getOriginalFilename();
            String contentType = file.getContentType();

            ExpressionEvaluatorFactory expressionEvaluatorFactory= new ExpressionEvaluatorFactory();

            ExpressionEvaluator expressionEvaluator = expressionEvaluatorFactory.createExpressionEvaluator();

            FileReaderFactory readerFactory = new FileReaderFactory();

            FileReader reader = readerFactory.createFileReader(file);

            String content = reader.readFile(file);

            processedContent = expressionEvaluator.processExpressions(content);

            FileProcessorFactory processorFactory = new FileProcessorFactory();

            FileProcessor processor = processorFactory.createFileProcessor(action);
            byte[] outputBytes;

            outputBytes = processor.processFile(processedContent, originalFilename);
            //TODO that it will be okay with all extensions(default, rar...)
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + processor.getFilename() + "\"")
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(outputBytes);

            /*switch (action) {
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
            }*/

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка обработки файла".getBytes(StandardCharsets.UTF_8));

        } catch (RarException e) {
            throw new RuntimeException(e);
        }
    }
}