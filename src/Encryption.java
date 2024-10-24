import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public class Encryption {
    public static String encrypt(String text, String password) throws Exception {
        byte[] salt = generateSalt();
        SecretKey secretKey = StringToSecretKey(password, salt);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedText = cipher.doFinal(text.getBytes());
        byte[] combined = new byte[salt.length + encryptedText.length];
        System.arraycopy(salt, 0, combined, 0, salt.length);
        System.arraycopy(encryptedText, 0, combined, salt.length, encryptedText.length);
        return Base64.getEncoder().encodeToString(combined);
    }

    public static String decrypt(String encryptedText, String password) throws Exception {
        byte[] decodedText = Base64.getDecoder().decode(encryptedText);
        byte[] salt = new byte[16];
        System.arraycopy(decodedText, 0, salt, 0, salt.length);
        byte[] encryptedData = new byte[decodedText.length - salt.length];
        System.arraycopy(decodedText, salt.length, encryptedData, 0, encryptedData.length);
        SecretKey secretKey = StringToSecretKey(password, salt);
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedText = cipher.doFinal(encryptedData);

        return new String(decryptedText);
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
