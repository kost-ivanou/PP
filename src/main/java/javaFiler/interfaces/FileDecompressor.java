package javaFiler.interfaces;


import com.github.junrar.exception.RarException;
import javaFiler.dto.FileDecompressingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileDecompressor {
    public FileDecompressingResult decompressData(MultipartFile file) throws IOException, RarException;
}
