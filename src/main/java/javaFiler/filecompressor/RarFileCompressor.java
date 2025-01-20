package javaFiler.filecompressor;

import javaFiler.dto.FileCompressingResult;
import javaFiler.interfaces.FileCompressor;

import java.io.*;
import java.nio.file.Files;

public class RarFileCompressor implements FileCompressor {
    public static String RAR_EXECUTABLE_PATH = "C:\\Program Files\\WinRAR\\Rar.exe"; // Абсолютный путь к Rar.exe

    @Override
    public FileCompressingResult compressData(String content, String originalFilename) throws IOException {
        String archiveFilename = "processed_" + originalFilename.replaceFirst("[.][^.]+$", "") + ".rar";

        File tempFile = File.createTempFile("processed_"+originalFilename, "");
        Files.write(tempFile.toPath(), content.getBytes());

        String rarFilename = "processed_" + originalFilename.replaceFirst("[.][^.]+$", "") + ".rar";
        File rarFile = new File(rarFilename);

        String command = String.format("\"%s\" a -ep \"%s\" \"%s\"", RAR_EXECUTABLE_PATH, rarFile.getAbsolutePath(), tempFile.getAbsolutePath());

        Process process = Runtime.getRuntime().exec(command);

        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int exitCode = process.exitValue();
        if (exitCode != 0) {
            throw new IOException("Error creating RAR archive");
        }

        byte[] rarBytes = Files.readAllBytes(rarFile.toPath());

        tempFile.delete();
        rarFile.delete();

        return new FileCompressingResult(archiveFilename, rarBytes);
    }


}
