/**
 * Created by alec on 01/26/17.
 */
import javax.crypto.Cipher;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.InvalidKeyException;

public class Encrypter {
    private static String algorithm = "DESede";
    private static Key key = null;
    private static Cipher cipher = null;

    public static void setUp() throws Exception {
        key = KeyGenerator.getInstance(algorithm).generateKey();
        cipher = Cipher.getInstance(algorithm);
    }
    public byte[] encrypt(String input)
            throws InvalidKeyException,
            BadPaddingException,
            IllegalBlockSizeException {
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] inputBytes = input.getBytes();
        return cipher.doFinal(inputBytes);
    }

    public String decrypt(byte[] encryptionBytes)
            throws InvalidKeyException,
            BadPaddingException,
            IllegalBlockSizeException {
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] recoveredBytes =
                cipher.doFinal(encryptionBytes);
        String recovered =
                new String(recoveredBytes);
        return recovered;
    }
}
