package javaFiler.controller;

import javaFiler.dto.FileCompressingResult;
import javaFiler.dto.FileDecompressingResult;
import javaFiler.expressioneval.ExpressionEvaluatorFactory;
import javaFiler.filecompressor.FileCompressorFactory;
import javaFiler.filedecompressor.FileDecompressorFactory;
import javaFiler.filereader.FileReaderFactory;
import javaFiler.interfaces.*;
import javaFiler.fileprocessor.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api")
public class FileController {

    @PostMapping("/upload")
    public ResponseEntity<byte[]> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("action") String action,
            @RequestParam("encrypt") boolean encrypt){
        try {
            String originalFilename = file.getOriginalFilename();
            String contentType = file.getContentType();
            // TODO: move factories to Singletons


            FileDecompressorFactory decompressorFactory = new FileDecompressorFactory();
            FileDecompressor decompressor = decompressorFactory.createFileDecompressor(originalFilename);
            FileDecompressingResult decompressedContent = decompressor.decompressData(file);

            // TODO: decrypt with static method in readContent

            FileReaderFactory readerFactory = new FileReaderFactory();
            FileReader reader = readerFactory.createFileReader(decompressedContent.getFilename());
            String content = reader.readContent(decompressedContent.getContent());

            ExpressionEvaluatorFactory expressionEvaluatorFactory= new ExpressionEvaluatorFactory();
            ExpressionEvaluator expressionEvaluator = expressionEvaluatorFactory.createExpressionEvaluator();
            String processedContent = expressionEvaluator.processExpressions(content);

            FileProcessorFactory processorFactory = new FileProcessorFactory();
            FileProcessor processor = processorFactory.createFileProcessor(encrypt);
            String encryptedContent = processor.processFile(processedContent, decompressedContent.getFilename());

            FileCompressorFactory compressorFactory = new FileCompressorFactory();
            FileCompressor compressor =  compressorFactory.createCompressorFactory(action);
            FileCompressingResult compressedContent = compressor.compressData(encryptedContent, processor.getFilename());

            //TODO that it will be okay with all extensions(default, rar...)
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=\"" + compressedContent.getFilename() + "\"")
                    .contentType(MediaType.parseMediaType(compressedContent.getContentType()))
                    .body(compressedContent.getBytesContent());

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

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unsupported format".getBytes(StandardCharsets.UTF_8));

        }
    }
}