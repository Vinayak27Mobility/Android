package android.com.gorentjoy.service;

import android.com.gorentjoy.model.AdResponse;
import android.com.gorentjoy.net.APIRequest;
import android.com.gorentjoy.util.Constants;
import android.com.gorentjoy.net.ResponseHandler;
import android.com.gorentjoy.storage.SharedPrefWrapper;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by vinayak kulkarni on 6/28/2016.
 */

public class ListingService {

    private static ListingService listingService;
    private static final String LISTING_ENDPOINT = "listings";
    private static final String LISTING_FAVORITES = "favorites";
    private final String TAG = ListingService.class.getSimpleName();

    public static ListingService getInstance() {
        if (listingService == null) {
            listingService = new ListingService();
        }
        return listingService;
    }

    public void getListings(final Context context, final Handler handler, final JSONObject jsonBody, final String param, final String tag) {
        String url = Constants.BASE_URL + LISTING_ENDPOINT + "?user_tokan=" + SharedPrefWrapper.getInstance().getUserToken() + "&apikey=" + Constants.API_KEY_EXTRA + param + "&items_per_page=10";
        Logger.log(Logger.LOG_TYPE.DEBUG, TAG, url);
        final ResponseHandler responseHandler = new ResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                Logger.log(Logger.LOG_TYPE.DEBUG, TAG, response.toString());
                List<AdResponse.Ad> yourList = null;

                try {
                    JSONObject jsonObject = new JSONObject(response.toString());

                    Type listType = new TypeToken<List<AdResponse.Ad>>() {
                    }.getType();
                    yourList = new Gson().fromJson(jsonObject.getJSONArray("ads").toString(), listType);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (yourList != null) {
                    Message message = Message.obtain();
                    message.arg2 = Constants.SUCCESS;
                    message.obj = yourList;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onError(JSONObject response) {
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

        APIRequest apiRequest = new APIRequest(context, Request.Method.GET, url, null, responseHandler, tag);
        //apiRequest.addAccessToken(true);
        apiRequest.execute();
    }

    public void getFavorite(final Context context, final Handler handler, final JSONObject jsonBody, final String tag) {
        String url = Constants.BASE_URL + LISTING_FAVORITES + "?user_tokan=" + SharedPrefWrapper.getInstance().getUserToken() + "&apikey=" + Constants.API_KEY_EXTRA;
        Logger.log(Logger.LOG_TYPE.DEBUG, TAG, url);
        final ResponseHandler responseHandler = new ResponseHandler() {
            @Override
            public void onSuccess(JSONObject response) {
                // Logger.log(Logger.LOG_TYPE.DEBUG, TAG, response.toString());

                List<AdResponse.Ad> yourList = null;

                try {
                    JSONObject jsonObject = new JSONObject(response.toString());

                    Type listType = new TypeToken<List<AdResponse.Ad>>() {
                    }.getType();
                    yourList = new Gson().fromJson(jsonObject.getJSONArray("ads").toString(), listType);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (yourList != null) {
                    Message message = Message.obtain();
                    message.arg2 = Constants.SUCCESS;
                    message.obj = yourList;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onError(JSONObject response) {
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

        APIRequest apiRequest = new APIRequest(context, Request.Method.GET, url, null, responseHandler, tag);
        //apiRequest.addAccessToken(true);
        apiRequest.execute();
    }
}
