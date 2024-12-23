package javaFiler.strategy;

import java.io.*;
import java.nio.file.Files;

public class RarFileProcessor implements FileProcessor {

    public static String RAR_EXECUTABLE_PATH = "C:\\Program Files\\WinRAR\\Rar.exe"; // Абсолютный путь к Rar.exe

    @Override
    public byte[] processFile(String content, String originalFilename) throws IOException {

        File tempFile = File.createTempFile("temp", ".txt");
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

        return rarBytes;
    }

    @Override
    public String getContentType() {
        return "application/x-rar-compressed";
    }
}
