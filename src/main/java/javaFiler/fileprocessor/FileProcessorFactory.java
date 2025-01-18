package javaFiler.fileprocessor;

import javaFiler.models.FileProcessor;


public class FileProcessorFactory {
    FileProcessor processor;
    public FileProcessor createFileProcessor(String fileProcessorType){
        switch(fileProcessorType) {
            case "zip":
                processor = new ZipFileProcessor();
                break;
            case "rar":
                processor = new RarFileProcessor();
                break;
            case "encrypt":
                processor = new EncryptFileProcessor();
                break;
            case "defaultFile":
                processor = new DefaultFileProcessor();
                break;
            default:
                processor = null;
        }
        return processor;
    }
}
