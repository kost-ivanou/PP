package javaFiler.fileprocessor;

import javaFiler.interfaces.FileProcessor;


public class FileProcessorFactory {
    FileProcessor processor;
    public FileProcessor createFileProcessor(boolean encrypt) throws Exception {
        if (encrypt) {
            processor = new EncryptFileProcessor();
        } else  {
            processor = new DefaultFileProcessor();
        }
        return processor;
    }
}
