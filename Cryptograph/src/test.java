import javax.swing.*;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class test extends JFrame {

    public static void main(String[] args) {

        //asdasergnhryrtuyuuzxdszxcvbnjmk,o2[[l,o0A765E342tyui81Q.;mjnhbgvffdewqwertyuiopasdfghjklzxcvbnm,./']'//']\][pl,o98765U321`~!@!!^&*()I~!@#$%^&*({}}|}{"?":>:<<LP)_)(*&^%$+/-=PLMKOJNBNJIHBYGVGTFCXDRDXZSW

        String a = "bshaiiaidjlv";

        System.out.println(a.indexOf("a"));


    }

    private static final String ALGORITHM = "AES";
    private static final String KEY = "MySecretKey12345"; // This should be changed to a more secure key

    public static String encrypt(String value) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedBytes = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String encryptedValue) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedValue));
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
