package javaFiler.filedecompressor;

public class ExtensionValidator {
    public static boolean isSupportedFormat(String fileName) {
        return fileName.endsWith(".txt") || fileName.endsWith(".json") ||
                fileName.endsWith(".xml") || fileName.endsWith(".yaml") ||
                fileName.endsWith(".yml");
    }
}
