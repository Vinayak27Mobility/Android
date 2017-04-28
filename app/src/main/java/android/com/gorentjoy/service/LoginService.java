package android.com.gorentjoy.service;

import android.com.gorentjoy.model.RegisterResponse;
import android.com.gorentjoy.net.APIRequest;
import android.com.gorentjoy.util.Constants;
import android.com.gorentjoy.net.ResponseHandler;
import android.com.gorentjoy.storage.SharedPrefWrapper;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.android.volley.Request;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by vinayak kulkarni on 6/28/2016.
 */

public class LoginService {

    private static LoginService loginService;
    private static final String LOGIN_ENDPOINT = "auth/login";
    private static final String REGISTER_ENDPOINT = "auth";
    private final String TAG = LoginService.class.getSimpleName();

    public static LoginService getInstance() {
        if (loginService == null) {
            loginService = new LoginService();
        }
        return loginService;
    }

    public void login(final Context context, final Handler handler, final JSONObject jsonBody, final String tag) {
        String url = Constants.BASE_URL + LOGIN_ENDPOINT;
        Logger.log(Logger.LOG_TYPE.DEBUG, TAG, url);
        ResponseHandler responseHandler = new ResponseHandler() {

            @Override
            public void onSuccess(JSONObject response) {
                Logger.log(Logger.LOG_TYPE.DEBUG, TAG, response.toString());

                Gson gson = new Gson();
                RegisterResponse registerResponse = gson.fromJson(response.toString(), RegisterResponse.class);

                Logger.log(Logger.LOG_TYPE.DEBUG, TAG, "UserToken = " + registerResponse.getUser().getUserToken());

                SharedPrefWrapper sharedPrefWrapper = SharedPrefWrapper.getInstance();
                RegisterResponse.User user = registerResponse.getUser();
                sharedPrefWrapper.storeUserToken( user.getUserToken());
                sharedPrefWrapper.storeUserName( user.getName());
                sharedPrefWrapper.storeUserEmail( user.getEmail());
                sharedPrefWrapper.storeUserId( user.getIdUser());
                if(user.getHasImage().equals("1")) {
                    sharedPrefWrapper.storeUserIcon(user.getImage());
                }



                Message message = Message.obtain();
                message.arg2 = Constants.SUCCESS;
                handler.sendMessage(message);
            }

            @Override
            public void onError(JSONObject response) {
                Message message = Message.obtain();
                int errorCode = 0;

                Logger.log(Logger.LOG_TYPE.DEBUG, TAG, "response is " + response);

                try {
                    if (response != null && response.getString("error") != null) {
                        Object errorObject = response.get("error");
                        if (errorObject instanceof JSONObject) {
                            errorCode = ((JSONObject) errorObject).getInt("code");
                        } else if (errorObject instanceof String) {
                            errorCode = response.getInt("code");
                        }
                    }
                } catch(JSONException e) {
                    e.printStackTrace();
                }

                //... Handle multiple expected error conditions here
                message.arg2 = errorCode;
                handler.sendMessage(message);
            }
        };

        APIRequest apiRequest = new APIRequest(context, Request.Method.POST, url, jsonBody, responseHandler, tag);
        //Map<String, String> headers = new HashMap<>();
        //headers.put("Authorization", "Basic Qm5hOFlLR2JQVHlTRDZ5a1kxZmc2QmduenhEWjE4c246bnhiVlpBcktkT3dOMmZtRw==");
        //apiRequest.setHeaders(headers);
        apiRequest.execute();
    }

    public void register(final Context context, final Handler handler, final JSONObject jsonBody, final String tag) {
        String url = Constants.BASE_URL + REGISTER_ENDPOINT;
        Logger.log(Logger.LOG_TYPE.DEBUG, TAG, url);
        ResponseHandler responseHandler = new ResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                Logger.log(Logger.LOG_TYPE.DEBUG, TAG, response.toString());

                Gson gson = new Gson();
                RegisterResponse registerResponse = gson.fromJson(response.toString(), RegisterResponse.class);

                SharedPrefWrapper sharedPrefWrapper = SharedPrefWrapper.getInstance();
                RegisterResponse.User user = registerResponse.getUser();
                sharedPrefWrapper.storeUserToken( user.getUserToken());
                sharedPrefWrapper.storeUserName( user.getName());
                sharedPrefWrapper.storeUserEmail( user.getEmail());


                Message message = Message.obtain();
                message.arg2 = Constants.SUCCESS;
                handler.sendMessage(message);
            }

            @Override
            public void onError(JSONObject response) {
                Message message = Message.obtain();
                int errorCode = 0;

                try {
                    if (response != null && response.getString("error") != null) {
                        Object errorObject = response.get("error");
                        if(errorObject instanceof JSONObject) {
                            errorCode = ((JSONObject) errorObject).getInt("code");
                        } else if(errorObject instanceof String) {
                            errorCode = response.getInt("code");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //... Handle multiple expected error conditions here
                message.arg2 = errorCode;
                handler.sendMessage(message);
            }
        };

        APIRequest apiRequest = new APIRequest(context, Request.Method.POST, url, jsonBody, responseHandler, tag);
        //Map<String, String> headers = new HashMap<>();
        //headers.put("Authorization", "Basic Qm5hOFlLR2JQVHlTRDZ5a1kxZmc2QmduenhEWjE4c246bnhiVlpBcktkT3dOMmZtRw==");
        //apiRequest.setHeaders(headers);
        apiRequest.execute();
    }
}
