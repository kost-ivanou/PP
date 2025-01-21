package javaFiler.controller;

import javaFiler.dto.FileCompressingResult;
import javaFiler.dto.FileDecompressingResult;
import javaFiler.expressioneval.ExpressionEvaluatorFactory;
import javaFiler.filecompressor.FileCompressorFactory;
import javaFiler.decryptor.Decryptor;
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
            // TODO: move factories to Singletons

            FileDecompressorFactory decompressorFactory = new FileDecompressorFactory();
            FileDecompressor decompressor = decompressorFactory.createFileDecompressor(originalFilename);
            FileDecompressingResult decompressedContent = decompressor.decompressData(file);

            FileReaderFactory readerFactory = new FileReaderFactory();
            FileReader reader = readerFactory.createFileReader(decompressedContent.getFilename());
            String content = reader.readContent(Decryptor.decrypt(decompressedContent.getContent()));

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

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unsupported format".getBytes(StandardCharsets.UTF_8));

        }
    }
}