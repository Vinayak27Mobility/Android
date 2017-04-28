package android.com.gorentjoy.service;

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

public class PublishListingService {

    private static PublishListingService publishListingService;
    private static final String PUBLISH_ENDPOINT = "ads";
    private static final String PUBLISH_PHOTO_ENDPOINT = "ads/image/";
    private final String TAG = PublishListingService.class.getSimpleName();

    public static PublishListingService getInstance() {
        if (publishListingService == null) {
            publishListingService = new PublishListingService();
        }
        return publishListingService;
    }

    public void publishAd(final Context context, final Handler handler, final JSONObject jsonBody, final String tag) {
        String url = Constants.BASE_URL + PUBLISH_ENDPOINT;
        Logger.log(Logger.LOG_TYPE.DEBUG, TAG, url);
        ResponseHandler responseHandler = new ResponseHandler() {

            @Override
            public void onSuccess(JSONObject response) {
                Logger.log(Logger.LOG_TYPE.DEBUG, TAG, response.toString());

                Gson gson = new Gson();
                String adId = null;
                try {
                    JSONObject jsonObject = response.getJSONObject("ad");
                    adId = jsonObject.getString("id_ad");
                } catch(Exception e) {

                }



                Message message = Message.obtain();
                message.arg1 = Integer.valueOf(adId);
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

    public void publishAdPhoto(final Context context, final Handler handler, final JSONObject jsonBody, final String adId, final String tag) {
        String url = Constants.BASE_URL + PUBLISH_PHOTO_ENDPOINT + adId;
        Logger.log(Logger.LOG_TYPE.DEBUG, TAG, url);
        ResponseHandler responseHandler = new ResponseHandler() {

            @Override
            public void onSuccess(JSONObject response) {
                Logger.log(Logger.LOG_TYPE.DEBUG, TAG, response.toString());

                Gson gson = new Gson();




                Message message = Message.obtain();
                message.arg2 = Constants.SUCCESS;
                message.arg1 = Integer.valueOf(adId);
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
}
