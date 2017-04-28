package android.com.gorentjoy.ui;

import android.app.Application;
import android.com.gorentjoy.storage.SharedPrefWrapper;

//import com.facebook.FacebookSdk;

/**
 * Created by vinayakkulkarni on 7/2/16.
 */
public class GoRentJoyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //FacebookSdk.sdkInitialize(getApplicationContext());
        SharedPrefWrapper.initializeInstance(this);
    }
}
