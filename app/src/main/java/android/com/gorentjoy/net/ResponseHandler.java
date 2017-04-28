package android.com.gorentjoy.net;

import org.json.JSONObject;

/**
 * Created by vinayak kulkarni on 6/28/2016.
 */
public interface ResponseHandler {
    void onSuccess(JSONObject response);
    void onError(JSONObject response);
}
