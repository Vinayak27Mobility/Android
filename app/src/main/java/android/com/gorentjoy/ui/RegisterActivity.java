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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vinayak_kulkarni on 6/27/2016.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userET, useremailET, passwordET, confirmpasswordET;
    private TextInputLayout userLayout, emailLayout, passwordLayout, confirmpasswordLayout;
    private Button submitBtn;
    private ProgressDialog progressDialog;
    private final String TAG = RegisterActivity.class.getSimpleName();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        context = this;

        userET = (EditText) findViewById(R.id.editusernameid);
        useremailET = (EditText) findViewById(R.id.edituseremailid);
        passwordET = (EditText) findViewById(R.id.editpasswordid);
        confirmpasswordET = (EditText) findViewById(R.id.editconfirmpasswordid);
        userLayout = (TextInputLayout) findViewById(R.id.editlayoutuserid);
        emailLayout = (TextInputLayout) findViewById(R.id.editlayoutemailid);
        passwordLayout = (TextInputLayout) findViewById(R.id.editlayoutpasswordid);
        confirmpasswordLayout = (TextInputLayout) findViewById(R.id.editlayoutconfirmpasswordid);
        submitBtn = (Button) findViewById(R.id.btnsubmitid);
        submitBtn.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(R.string.reg_screen_caption);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == submitBtn.getId()) {
            String userTxt = userET.getText().toString().trim();

            if(userTxt.isEmpty()) {
                userLayout.setError(getString(R.string.mandate));
                requestFocus(userET);
                return;
            } else {
                userLayout.setErrorEnabled(false);
                userLayout.setError(null);
            }
            String useremailTxt = useremailET.getText().toString().trim();
            if(useremailTxt.isEmpty()) {
                emailLayout.setError(getString(R.string.mandate));
                requestFocus(useremailET);
                return;
            } else if(!(Util.validEmail(useremailTxt))) {
                emailLayout.setErrorEnabled(true);
                emailLayout.setError(getString(R.string.email_not_valid));
                requestFocus(useremailET);
                return;
            } else {
                emailLayout.setErrorEnabled(false);
                emailLayout.setError(null);
            }
            String passwordTxt = passwordET.getText().toString().trim();

            if(passwordTxt.isEmpty()) {
                passwordLayout.setError(getString(R.string.mandate));
                requestFocus(passwordET);
                return;
            } else {
                passwordLayout.setErrorEnabled(false);
                passwordLayout.setError(null);
            }

            String confirmpasswordTxt = confirmpasswordET.getText().toString().trim();

            if(confirmpasswordTxt.isEmpty()) {
                confirmpasswordLayout.setError(getString(R.string.mandate));
                requestFocus(passwordET);
                return;
            } else {
                confirmpasswordLayout.setErrorEnabled(false);
                confirmpasswordLayout.setError(null);
            }

            if(!(passwordTxt.equals(confirmpasswordTxt))) {
                passwordLayout.setError(getString(R.string.password_not_match));
                confirmpasswordLayout.setError(getString(R.string.password_not_match));
                requestFocus(passwordET);
                return;
            } else {
                passwordLayout.setErrorEnabled(false);
                passwordLayout.setError(null);
                confirmpasswordLayout.setErrorEnabled(false);
                confirmpasswordLayout.setError(null);
            }

            //Regis API
            progressDialog = new CustomProgressDialog(RegisterActivity.this);

            progressDialog.show();

            submitBtn.setEnabled(false);

            Util.hideSoftKeyboard(this);
            Map<String, String> postParam = new HashMap<>();
            postParam.put("name", userTxt);
            postParam.put("email", useremailTxt);
            postParam.put("password", passwordTxt);
            postParam.put("apikey", Constants.API_KEY);

            JSONObject jsonBody = new JSONObject(postParam);
            LoginService loginService = LoginService.getInstance();

            // Create request queue and pass it to service
            loginService.register(getApplicationContext(), new RegisterHandler(this), jsonBody, TAG);


        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


    private static class RegisterHandler extends WeakReferenceHandler<RegisterActivity> {

        public RegisterHandler(RegisterActivity reference) {
            super(reference);
        }

        @Override
        public void handleMessage(final RegisterActivity registerActivity, Message msg) {

            if (registerActivity.progressDialog != null) {
                registerActivity.progressDialog.dismiss();
            }
            registerActivity.submitBtn.setEnabled(true);

            if (msg.arg2 == Constants.SUCCESS) {
                new AlertDialog.Builder(registerActivity)
                        .setTitle(R.string.alert_success)
                        .setMessage(R.string.reg_success)
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(registerActivity, HomeActivity.class);
                                registerActivity.startActivity(intent);
                                registerActivity.finish();
                            }
                        })
                        .show();
            } else {
                android.app.AlertDialog.Builder alert = Util.createAlert(registerActivity.context,
                        null,
                        registerActivity.context.getResources().getString(R.string.error_alert_title),
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

                NetworkErrorManager.showErrors(registerActivity.context, msg.arg2, alert);
            }
        }
    }
}
