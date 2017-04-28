package android.com.gorentjoy.ui;

import android.app.ProgressDialog;
import android.com.gorentjoy.R;
import android.com.gorentjoy.util.Constants;
import android.com.gorentjoy.net.NetworkErrorManager;
import android.com.gorentjoy.service.LoginService;
import android.com.gorentjoy.ui.widgets.CustomProgressDialog;
import android.com.gorentjoy.util.Util;
import android.com.gorentjoy.util.WeakReferenceHandler;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/*import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;*/

/**
 * Created by vinayak_kulkarni on 6/27/2016.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userET, passwordET;
    private TextInputLayout userLayout, passwordLayout;
    private Button loginBtn;
    private TextView regisTV;
    private ProgressDialog progressDialog;
    private final String TAG = LoginActivity.class.getSimpleName();
    private Context context;
    /*private LoginButton facebookBtn;
    private CallbackManager callbackManager;
    private
    FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            Logger.log(Logger.LOG_TYPE.DEBUG, TAG, "facebook response " + profile);
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException e) {

        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Logger.log(Logger.LOG_TYPE.DEBUG, TAG, "hash key"+ something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Logger.log(Logger.LOG_TYPE.ERROR, TAG, "name not found" + e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Logger.log(Logger.LOG_TYPE.ERROR, TAG, "no such an algorithm" + e.toString());
        } catch (Exception e) {
            Logger.log(Logger.LOG_TYPE.ERROR, TAG, "exception" + e.toString());
        }*/

        context = this;

        //callbackManager = CallbackManager.Factory.create();

        userET = (EditText) findViewById(R.id.edituserid);
        passwordET = (EditText) findViewById(R.id.editpasswordid);
        userLayout = (TextInputLayout) findViewById(R.id.editlayoutuserid);
        passwordLayout = (TextInputLayout) findViewById(R.id.editlayoutpasswordid);
        loginBtn = (Button) findViewById(R.id.btnloginid);
        loginBtn.setOnClickListener(this);
        /*facebookBtn = (LoginButton) findViewById(R.id.login_button);
        facebookBtn.registerCallback(callbackManager, callback);*/
        regisTV = (TextView) findViewById(R.id.btnregid);
        regisTV.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(R.string.login_screen_caption);
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }*/

    @Override
    public void onClick(View v) {
        if (v.getId() == loginBtn.getId()) {
            String userTxt = userET.getText().toString().trim();

            if (userTxt.isEmpty()) {
                userLayout.setError(getString(R.string.mandate));
                requestFocus(userET);
                return;
            } else if (!(Util.validEmail(userTxt))) {
                userLayout.setErrorEnabled(true);
                userLayout.setError(getString(R.string.email_not_valid));
                requestFocus(userET);
                return;
            } else {
                userLayout.setErrorEnabled(false);
                userLayout.setError(null);
            }

            String passwordTxt = passwordET.getText().toString().trim();

            if (passwordTxt.isEmpty()) {
                passwordLayout.setError(getString(R.string.mandate));
                requestFocus(passwordET);
                return;
            } else {
                passwordLayout.setErrorEnabled(false);
                passwordLayout.setError(null);
            }

            //login API
            progressDialog = new CustomProgressDialog(LoginActivity.this);
            progressDialog.show();
            loginBtn.setEnabled(false);

            Util.hideSoftKeyboard(this);

            Map<String, String> postParam = new HashMap<>();
            postParam.put("email", userTxt);
            postParam.put("password", passwordTxt);
            postParam.put("apikey", Constants.API_KEY);

            JSONObject jsonBody = new JSONObject(postParam);
            LoginService loginService = LoginService.getInstance();

            // Create request queue and pass it to service
            loginService.login(getApplicationContext(), new LoginHandler(this), jsonBody, TAG);
        } else if (v.getId() == regisTV.getId()) {
            Intent regIntent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(regIntent);
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private static class LoginHandler extends WeakReferenceHandler<LoginActivity> {

        public LoginHandler(LoginActivity reference) {
            super(reference);
        }

        @Override
        public void handleMessage(LoginActivity loginActivity, Message msg) {

            if (loginActivity.progressDialog != null) {
                loginActivity.progressDialog.dismiss();
            }
            loginActivity.loginBtn.setEnabled(true);

            if (msg.arg2 == Constants.SUCCESS) {
                Intent intent = new Intent(loginActivity, HomeActivity.class);
                loginActivity.startActivity(intent);
                loginActivity.finish();
            } else {
                android.app.AlertDialog.Builder alert = Util.createAlert(loginActivity.context,
                        null,
                        loginActivity.context.getResources().getString(R.string.error_alert_title),
                        null,
                        R.string.button_ok,
                        0,
                        0,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }
                        ,
                        null,
                        null);

                NetworkErrorManager.showErrors(loginActivity.context, msg.arg2, alert);
            }
        }
    }
}
