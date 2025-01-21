package javaFiler.decryptor;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;


public class Decryptor {
        private static final String ALGORITHM = "AES";
        private static SecretKeySpec secretKey;

        // Static method to initialize the secret key
        public static void setSecretKey(String base64EncodedKey) {
            byte[] decodedKey = Base64.getDecoder().decode(base64EncodedKey);
            secretKey = new SecretKeySpec(decodedKey, ALGORITHM);
        }

        public static String decrypt(String content) {
            try {
                // Check if the content is encrypted
                if (isEncrypted(content)) {
                    Cipher cipher = Cipher.getInstance(ALGORITHM);
                    cipher.init(Cipher.DECRYPT_MODE, secretKey);
                    byte[] decryptedBytes = cipher.doFinal(parseEncryptedContent(content));
                    return new String(decryptedBytes, StandardCharsets.UTF_8);
                } else {
                    return content; // Return original content if not encrypted
                }
            } catch (Exception e) {
                // Handle decryption errors (e.g., return original content or log the error)
                return content;
            }
        }

        private static boolean isEncrypted(String content) {
            // Implement logic to determine if the content is encrypted
            return content != null && content.startsWith("encrypted_"); // Example check
        }

        private static byte[] parseEncryptedContent(String content) {
            // Convert the string back to bytes for decryption
            // You may need to implement specific logic based on how you encrypt the content
            return content.getBytes(StandardCharsets.UTF_8);
        }
    }
