package javaFiler.fileprocessor;

import javaFiler.interfaces.FileProcessor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Arrays;
import java.util.Base64;

public class EncryptFileProcessor implements FileProcessor {
    private static final String ALGORITHM = "AES";
    private SecretKey secretKey;

    public EncryptFileProcessor() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
        keyGen.init(128); // You can also use 192 or 256 bits
        secretKey = keyGen.generateKey();
    }

    @Override
    public String processFile(String content, String originalFilename) throws IOException {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Arrays.toString(cipher.doFinal(content.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new IOException("Encryption error", e);
        }
    }

    public String getSecretKeyAsString() {
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    @Override
    public String getFilename() {
        return "encrypted_" + System.currentTimeMillis() + ".enc";
    }
}