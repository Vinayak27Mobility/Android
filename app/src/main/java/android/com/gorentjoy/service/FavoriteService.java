package android.com.gorentjoy.service;

import android.com.gorentjoy.model.CategoryResponse;
import android.com.gorentjoy.net.APIRequest;
import android.com.gorentjoy.util.Constants;
import android.com.gorentjoy.net.ResponseHandler;
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

public class FavoriteService {

    private static FavoriteService favoriteService;
    private static final String FAV_ENDPOINT = "favorites";
    private final String TAG = FavoriteService.class.getSimpleName();

    public static FavoriteService getInstance() {
        if (favoriteService == null) {
            favoriteService = new FavoriteService();
        }
        return favoriteService;
    }

    public void setFavoriteStatus(final boolean setFlag, final Context context, final Handler handler, final JSONObject jsonBody, final String tag) {
        String url = Constants.BASE_URL + FAV_ENDPOINT;
        Logger.log(Logger.LOG_TYPE.DEBUG, TAG, url);
        final ResponseHandler responseHandler = new ResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                Logger.log(Logger.LOG_TYPE.DEBUG, TAG, response.toString());

                Gson gson = new Gson();

                Message message = Message.obtain();
                message.arg2 = Constants.SUCCESS;
                if (setFlag)
                    message.arg1 = 0;
                else
                    message.arg1 = 1;
                CategoryResponse categoryResponse = gson.fromJson(response.toString(), CategoryResponse.class);
                message.obj = categoryResponse.getCategories();
                handler.sendMessage(message);
            }

            @Override
            public void onError(JSONObject response) {
                Logger.log(Logger.LOG_TYPE.DEBUG, TAG, response.toString());
                Message message = Message.obtain();
                int errorCode = 0;

                try {
                    if (response != null && response.getString("error") != null) {
                        Object errorObject = response.get("error");
                        if (errorObject instanceof JSONObject) {
                            errorCode = ((JSONObject) errorObject).getInt("code");
                        } else if (errorObject instanceof String) {
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

        int method;
        if (setFlag) {
            method = Request.Method.POST;
        } else {
            method = Request.Method.DELETE;
        }
        APIRequest apiRequest = new APIRequest(context, method, url, jsonBody, responseHandler, tag);
        //apiRequest.addAccessToken(true);
        apiRequest.execute();
    }


}
