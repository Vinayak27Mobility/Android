package android.com.gorentjoy.service;

import android.com.gorentjoy.model.CategoryResponse;
import android.com.gorentjoy.model.LocationResponse;
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

public class CategoryService {

    private static CategoryService categoryService;
    private static final String CATE_ENDPOINT = "categories";
    private static final String LOCATION_ENDPOINT = "locations";
    private final String TAG = CategoryService.class.getSimpleName();

    public static CategoryService getInstance() {
        if (categoryService == null) {
            categoryService = new CategoryService();
        }
        return categoryService;
    }

    public void getCategories(final Context context, final Handler handler, final String tag) {
            String url = Constants.BASE_URL + CATE_ENDPOINT;
        Logger.log(Logger.LOG_TYPE.DEBUG, TAG, url);
        final ResponseHandler responseHandler = new ResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                Logger.log(Logger.LOG_TYPE.DEBUG, TAG, response.toString());

                Gson gson = new Gson();

                Message message = Message.obtain();
                message.arg2 = Constants.SUCCESS;
                CategoryResponse categoryResponse = gson.fromJson(response.toString(), CategoryResponse.class);
                message.obj = categoryResponse.getCategories();
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

        APIRequest apiRequest = new APIRequest(context, Request.Method.GET, url, null, responseHandler, tag);
        //apiRequest.addAccessToken(true);
        apiRequest.execute();
    }

    public void getLocations(final Context context, final Handler handler, final String tag) {
        String url = Constants.BASE_URL + LOCATION_ENDPOINT;
        Logger.log(Logger.LOG_TYPE.DEBUG, TAG, url);
        final ResponseHandler responseHandler = new ResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                Logger.log(Logger.LOG_TYPE.DEBUG, TAG, response.toString());

                Gson gson = new Gson();

                Message message = Message.obtain();
                message.arg2 = Constants.SUCCESS;
                LocationResponse locationResponse = gson.fromJson(response.toString(), LocationResponse.class);
                message.obj = locationResponse.getLocations();
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

        APIRequest apiRequest = new APIRequest(context, Request.Method.GET, url, null, responseHandler, tag);
        //apiRequest.addAccessToken(true);
        apiRequest.execute();
    }
}
