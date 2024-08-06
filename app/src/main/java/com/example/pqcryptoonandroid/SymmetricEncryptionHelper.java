package com.example.pqcryptoonandroid;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class SymmetricEncryptionHelper {

    public static SymmetricEncryptionHelper useDefaultIv(String key) {
        // TODO: Dont use static IV better transmit the IV in plaintext within the message.
        byte[] iv = new byte[]{'#', '0', 'a', '0', 'N', '0', '0', 'z', '1', '1', '_', '0', '0', 'x', 'U', '0'};
        return new SymmetricEncryptionHelper(key.getBytes(), iv);
    }

    private static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";

    private final SecretKeySpec secretKeySpec;
    private final IvParameterSpec ivSpec;

    public SymmetricEncryptionHelper(byte[] aesKey, byte[] iv) {
        this.secretKeySpec = new SecretKeySpec(aesKey, "AES");
        this.ivSpec = new IvParameterSpec(iv);
    }

    public String decrypt(String text) {
        try {
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);
            byte[] decryptedBytes = cipher.doFinal(Base64.decode(text, Base64.DEFAULT));
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong.", e);
        }
    }

    public String encrypt(String text) {
        try {
            Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);
            byte[] encrypted = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            return new String(Base64.encode(encrypted, Base64.DEFAULT), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong.", e);
        }
    }


    public void encryptStream(InputStream in, OutputStream out) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);

        try (CipherOutputStream cos = new CipherOutputStream(out, cipher)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                cos.write(buffer, 0, bytesRead);
            }
        }
    }

    public void decryptStream(InputStream in, OutputStream out) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);

        try (CipherInputStream cis = new CipherInputStream(in, cipher)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = cis.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}
