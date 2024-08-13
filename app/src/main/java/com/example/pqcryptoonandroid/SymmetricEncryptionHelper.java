package com.example.pqcryptoonandroid;

import java.io.InputStream;
import java.io.OutputStream;

public class SymmetricEncryptionHelper {
    private final byte[] key;

    public static SymmetricEncryptionHelper useDefaultIv(String key) {
        return new SymmetricEncryptionHelper(key.getBytes());
    }

    public SymmetricEncryptionHelper(byte[] aesKey) {
        this.key = aesKey;
    }

    public void encryptStream(InputStream in, OutputStream out) throws Exception {
        byte[] buffer = new byte[4096];
        int bytesRead;
        int keyLength = key.length;
        int keyIndex = 0;

        while ((bytesRead = in.read(buffer)) != -1) {
            for (int i = 0; i < bytesRead; i++) {
                buffer[i] ^= key[keyIndex];  // XOR operation
                keyIndex = (keyIndex + 1) % keyLength;  // Move to the next key byte
            }
            out.write(buffer, 0, bytesRead);
        }
    }

    // Decrypt the stream using XOR
    public void decryptStream(InputStream in, OutputStream out) throws Exception {
        byte[] buffer = new byte[4096];
        int bytesRead;
        int keyLength = key.length;
        int keyIndex = 0;

        while ((bytesRead = in.read(buffer)) != -1) {
            for (int i = 0; i < bytesRead; i++) {
                buffer[i] ^= key[keyIndex];  // XOR operation for decryption
                keyIndex = (keyIndex + 1) % keyLength;  // Move to the next key byte
            }
            out.write(buffer, 0, bytesRead);
        }
    }
}
