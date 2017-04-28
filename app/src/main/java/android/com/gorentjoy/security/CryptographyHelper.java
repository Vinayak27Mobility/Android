package android.com.gorentjoy.security;

import android.com.gorentjoy.service.Logger;
import android.com.gorentjoy.storage.SharedPrefWrapper;
import android.util.Base64;
import android.util.Log;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by vinayak kulkarni on 6/28/2016.
 */
public class CryptographyHelper {

    private static final String AES = "AES";
    private final static String TAG = CryptographyHelper.class.getSimpleName();
    private static final String ALGORITHM = "SHA1PRNG";
    private static final String RANDOM_SEED = "RandomSeed";
    private static SecretKeySpec secretKey = null;


    public static SecretKey getSecretKey() {

        if (secretKey == null) {

            // get a previously persisted secret key if stored
            String secretKeyStr = SharedPrefWrapper.getInstance().getSecretKey();

            // if it was not stored
            if (secretKeyStr == null) {

                SecureRandom secureRandom = null;
                KeyGenerator keyGenerator = null;

                try {
                    secureRandom = SecureRandom.getInstance(ALGORITHM);
                    keyGenerator = KeyGenerator.getInstance(AES);
                } catch (NoSuchAlgorithmException e) {
                    Log.e(TAG, "AES secret key spec error");
                }

                if (secureRandom != null) {
                    secureRandom.setSeed(RANDOM_SEED.getBytes());
                }
                if (keyGenerator != null) {
                    keyGenerator.init(128, secureRandom);
                    secretKey = new SecretKeySpec((keyGenerator.generateKey()).getEncoded(), AES);
                }

                // persist the secret key
                // byte[] to String
                String secretKeyEncodedStr = Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT);
                SharedPrefWrapper.getInstance().storeSecretKey(secretKeyEncodedStr);
            } else {
                // String to byte[]
                secretKey = new SecretKeySpec(Base64.decode(secretKeyStr, Base64.DEFAULT), AES);
            }

            Logger.log(Logger.LOG_TYPE.DEBUG, TAG, "SecretKey =" + Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT));

        }
        return secretKey;
    }

    public static String encrypt(SecretKey sks, String str) {
        String encodedString = null;

        if (str != null) {
            try {
                Cipher c = Cipher.getInstance(AES);
                c.init(Cipher.ENCRYPT_MODE, sks);
                encodedString = Base64.encodeToString(c.doFinal(str.getBytes()), Base64.DEFAULT);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }catch (IllegalBlockSizeException e) {
                e.printStackTrace();
            } catch (BadPaddingException e) {
                e.printStackTrace();
            }
        }
        return encodedString;
    }

    public static String decrypt(SecretKey sks, String ecodedStr) {

        byte[] decodedBytes = Base64.decode(ecodedStr, Base64.DEFAULT);

        Cipher c;
        try {
            c = Cipher.getInstance(AES);
            c.init(Cipher.DECRYPT_MODE, sks);
            decodedBytes = c.doFinal(decodedBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }

        if (decodedBytes != null) {
            return new String(decodedBytes);
        } else {
            return null;
        }
    }
}
