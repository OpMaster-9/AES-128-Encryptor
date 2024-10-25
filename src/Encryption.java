import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public class Encryption {
    public static byte[] encrypt(byte[] data, String password) throws Exception {
        byte[] salt = generateSalt();
        SecretKey secretKey = StringToSecretKey(password, salt);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(data);
        byte[] combined = new byte[salt.length + encryptedData.length];
        System.arraycopy(salt, 0, combined, 0, salt.length);
        System.arraycopy(encryptedData, 0, combined, salt.length, encryptedData.length);
        return combined;
    }

    public static byte[] decrypt(byte[] encryptedData, String password) throws Exception {
        byte[] salt = new byte[16];
        System.arraycopy(encryptedData, 0, salt, 0, salt.length);
        byte[] data = new byte[encryptedData.length - salt.length];
        System.arraycopy(encryptedData, salt.length, data, 0, data.length);
        SecretKey secretKey = StringToSecretKey(password, salt);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }
    private static SecretKey StringToSecretKey(String password, byte[] salt) throws Exception {
        int iterationCount = 65536;
        int keyLength = 256;
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterationCount, keyLength);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        byte[] secretKeyBytes = factory.generateSecret(spec).getEncoded();
        return new SecretKeySpec(secretKeyBytes, "AES");
    }
    private static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16]; // 128-bit salt
        random.nextBytes(salt);
        return salt;
    }
}
