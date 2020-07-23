import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class WeakCipherDataSet {
    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, NoSuchProviderException {
        // In the following two cases, no mode is explicitly specified, should be detected
        Cipher.getInstance("AES");
        Cipher.getInstance("AES", "BC"); // requesting from a specific provider, but with default mode

        //varying the coding styles on each true-positive a little bit to make sure that the rule doesnt depend on the variable names
        private static final Cipher CIPHER = Cipher.getInstance("DES"); //Constant Style
        Cipher cipher = Cipher.getInstance("DES/GCM"); //Assignment Variable Style

        // No Padding Specified, hence should be detected
        Cipher.getInstance("AES/ECB"); // Uses default padding, which is dangerous due to the risk of defaults changing
        Cipher.getInstance("AES/GCM"); // Availability/durability risk: Default padding used

        // Following is an invalid Cipher and should be detected
        Cipher.getInstance("A");

        //Some good examples, these should be ignored
        Cipher.getInstance("AES/GCM/NoPadding"); // OK: GCM mode with explicitly specified padding
        Cipher.getInstance("AES/CBC/PKCS5Padding"); // Acceptable: CBC mode with explicitly specified padding. GCM is preferred for new applications.
        Cipher.getInstance("RSA/ECB/OAEPWithSHA-512AndMGF1Padding"); // OK: ECB mode is acceptable for RSA encryption
        Cipher.getInstance("Aes/Gcm/Nopadding"); //Should ignore case since java does

        //The next line is NoPaddingSpecified but is commented, hence should not be detected
        //Cipher.getInstance("AES/CBC")
    }
}