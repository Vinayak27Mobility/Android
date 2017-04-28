package android.com.gorentjoy.net;

import android.com.gorentjoy.service.Logger;
import android.com.gorentjoy.util.Constants;
import android.com.gorentjoy.util.Util;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * Created by vinayak kulkarni on 6/28/2016.
 */
public class APIRequest {

    private final String TAG = APIRequest.class.getSimpleName();
    private static final String API_KEY = "apiKey";

    private boolean sslPinningEnabled = true;
    private final int method;
    private final String url;
    private final JSONObject requestBody;
    private Map<String, String> headersToSet;
    private Response.Listener<JSONObject> successHandler;
    private Response.ErrorListener errorHandler;
    private JsonRequest jsonRequest;
    private final RequestQueue requestQueue;
    private final ResponseHandler responseHandler;
    private final String requestTag;
    private Context context;

    private RetryPolicy retryPolicy;

    private static volatile boolean isFetchingNewAccessToken;

    private static final Queue<JsonRequest> pendingRequestQueue = new LinkedList<JsonRequest>();

    public APIRequest(Context context, int method, String url, JSONObject requestBody, final ResponseHandler responseHandler, final String requestTag, boolean... sslEnabled) {

        this.url = url;
        this.method = method;
        this.requestBody = requestBody;
        this.context = context;

        if (sslEnabled.length > 0) {
            sslPinningEnabled = sslEnabled[0];
        }

        this.requestQueue = RequestQueueManager.getInstance(context).getRequestQueue();
        this.responseHandler = responseHandler;
        this.requestTag = requestTag;
    }

    private JSONObject getErrorObject(VolleyError volleyError) {

        JSONObject jsonObject = null;
        if (volleyError.networkResponse != null &&
                volleyError.networkResponse.data != null) {
            Logger.log(Logger.LOG_TYPE.ERROR, TAG, new String(volleyError.networkResponse.data));

            try {
                jsonObject = new JSONObject(new String(volleyError.networkResponse.data));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    /*public void setHeaders(Map<String, String> headers) {
        this.headersToSet = headers;
    }*/

    public void execute() {
        successHandler = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                responseHandler.onSuccess(response);
            }
        };

        errorHandler = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Logger.log(Logger.LOG_TYPE.DEBUG, TAG, "Error: " + volleyError.getMessage() + " Request URL:" + jsonRequest.getUrl());
                if (!handleVolleyError(volleyError, responseHandler)) {
                    JSONObject jsonObject = getErrorObject(volleyError);

                    String errorMsg = null;

                    if (jsonObject != null) {
                        try {
                            Logger.log(Logger.LOG_TYPE.ERROR, TAG, jsonObject.toString());
                            errorMsg = jsonObject.getString("error");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    int errorCode = 0;
                    if (errorMsg != null) {
                        try {
                            errorCode = jsonObject.getInt("code");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    responseHandler.onError(jsonObject);
                }
            }
        };

        retryPolicy = new DefaultRetryPolicy(
                Constants.CONNECTION_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonRequest = new JsonRequest(method, url, requestBody, successHandler, errorHandler);
        jsonRequest.setTag(requestTag);
        //jsonRequest.setHeaders(headersToSet);
        jsonRequest.setRetryPolicy(retryPolicy);
        requestQueue.add(jsonRequest);
    }

    private boolean handleVolleyError(VolleyError error, ResponseHandler responseHandler) {
        if (error instanceof TimeoutError) {
            responseHandler.onError(getVolleyErrorObject(NetworkErrorManager.ErrorCode.TIME_OUT_ERROR));
            return true;
        } else if (error instanceof NoConnectionError) {
            if (Util.checkInternetConnection(context)) {
                responseHandler.onError(getVolleyErrorObject(NetworkErrorManager.ErrorCode.CERTIFICATE_INVALID));
            } else {
                responseHandler.onError(getVolleyErrorObject(NetworkErrorManager.ErrorCode.NO_CONNECTION_ERROR));
            }
            return true;
        } else if (error instanceof NetworkError) {
            responseHandler.onError(getVolleyErrorObject(NetworkErrorManager.ErrorCode.NETWORK_ERROR));
            return true;
        } else if (error instanceof ParseError) {
            responseHandler.onError(getVolleyErrorObject(NetworkErrorManager.ErrorCode.PARSE_ERROR));
            return true;
        }
        return false;
    }

    private JSONObject getVolleyErrorObject(NetworkErrorManager.ErrorCode volleyErrorCode) {
        JSONObject jsonObject = null;
        try {
            JSONObject errorObject = new JSONObject();
            errorObject.put("code", volleyErrorCode.getErrorCode());
            jsonObject = new JSONObject();
            jsonObject.put("error", errorObject);
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    class JsonRequest extends JsonObjectRequest {
        private Map<String, String> headers;

        public JsonRequest(int method, String url, JSONObject requestBody, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
            super(method, url, requestBody, listener, errorListener);
        }

        public void setHeaders(Map<String, String> headers) {
            this.headers = headers;
        }

        @Override
        public Map<String, String> getHeaders() throws AuthFailureError {
            if (headers != null) {
                //headers.put(API_KEY, Constants.API_KEY_HEADER_VALUE);
            } else {
                headers = new HashMap<>();
                //headers.put(API_KEY, Constants.API_KEY_HEADER_VALUE);
            }
            Logger.log(Logger.LOG_TYPE.DEBUG, TAG, " APIRequest Headers:: " + headers.toString());
            return headers;
        }
    }
}
