package android.com.gorentjoy.storage;

import android.com.gorentjoy.security.CryptographyHelper;
import android.com.gorentjoy.service.Logger;
import android.content.Context;
import android.content.SharedPreferences;

import javax.crypto.SecretKey;

/**
 * Created by vinayak kulkarni on 6/28/2016.
 */
public class SharedPrefWrapper {

    private static final String PREF_NAME = "GORentJoyPreference";
    private static final String USER_TOKEN = "UserToken";
    private static final String USER_NAME = "UserName";
    private static final String USER_EMAIL = "UserEmail";
    private static final String USER_ID = "UserId";
    private static final String USER_ICON = "UserIcon";


    private static final String TAG = SharedPrefWrapper.class.getSimpleName();

    private static SharedPrefWrapper sInstance;
    private final SharedPreferences mPref;

    private SharedPrefWrapper(Context context) {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new SharedPrefWrapper(context);
        }
    }

    public static synchronized SharedPrefWrapper getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException(SharedPrefWrapper.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }
        return sInstance;
    }

    public void storeUserToken(String accessToken) {
        SharedPreferences.Editor editor = mPref.edit();
        storeEncryptedValue(editor, USER_TOKEN, accessToken);
        editor.commit();
    }

    public void storeUserName(String userName) {
        SharedPreferences.Editor editor = mPref.edit();
        storeEncryptedValue(editor, USER_NAME, userName);
        editor.commit();
    }

    public void storeUserEmail(String userEmail) {
        SharedPreferences.Editor editor = mPref.edit();
        storeEncryptedValue(editor, USER_EMAIL, userEmail);
        editor.commit();
    }

    public void storeUserId(String userid) {
        SharedPreferences.Editor editor = mPref.edit();
        storeEncryptedValue(editor, USER_ID, userid);
        editor.commit();
    }

    public void storeUserIcon(String userIcon) {
        SharedPreferences.Editor editor = mPref.edit();
        storeEncryptedValue(editor, USER_ICON, userIcon);
        editor.commit();
    }

    public String getUserToken() {
        return getDecryptedValue(USER_TOKEN);
    }
    public String getUserName() {
        return getDecryptedValue(USER_NAME);
    }
    public String getUserEmail() {
        return getDecryptedValue(USER_EMAIL);
    }
    public String getUserId() {
        return getDecryptedValue(USER_ID);
    }
    public String getUserIcon() {
        return getDecryptedValue(USER_ICON);
    }



    public void resetLoginData() {
        storeUserToken(null);
        storeUserName(null);
        storeUserEmail(null);
        storeUserId(null);
        storeUserIcon(null);
    }

    private static final String ENCRYPTION_KEY = "enc_key";

    public void storeSecretKey(String secretKey) {
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString(ENCRYPTION_KEY, secretKey);
        editor.apply();
    }

    public String getSecretKey() {
        return mPref.getString(ENCRYPTION_KEY, null);
    }

    /*Encrypt both key and value then store*/
    private void storeEncryptedValue(SharedPreferences.Editor editor, String key, String value) {
        SecretKey secretKey = CryptographyHelper.getSecretKey();

        String encryptedKey = CryptographyHelper.encrypt(secretKey, key);
        encryptedKey = encryptedKey.substring(0, encryptedKey.indexOf("\n"));

        String encryptedValue = CryptographyHelper.encrypt(secretKey, value);

        Logger.log(Logger.LOG_TYPE.DEBUG, TAG, "Storing key = " + key + ", value = " + value + ", Encrypted key = " + encryptedKey + ", value = " + encryptedValue);
        editor.putString(encryptedKey, encryptedValue);
    }

    private String getDecryptedValue(String key) {
        // Encrypt the key
        SecretKey secretKey = CryptographyHelper.getSecretKey();

        String encryptedKey = CryptographyHelper.encrypt(secretKey, key);
        encryptedKey = encryptedKey.substring(0, encryptedKey.indexOf("\n"));

        // get value for encrypted key
        String encryptedValue = mPref.getString(encryptedKey, null);
        String decryptedValue = null;

        if (encryptedValue != null) {
            decryptedValue = CryptographyHelper.decrypt(secretKey, encryptedValue);
        }

        Logger.log(Logger.LOG_TYPE.DEBUG, TAG, "Fetching key = " + key + ", Encrypted key = " + encryptedKey + ", value = " + encryptedValue);

        // return decrypted value
        return decryptedValue;
    }

}
