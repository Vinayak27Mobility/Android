package android.com.gorentjoy.net;

import android.content.Context;


/**
 * Created by vinayak kulkarni on 6/28/2016.
 */
public class RequestController {

    private final String requestTag;
    private final Context context;

    public RequestController(Context context, String requestTag) {
        this.context = context;
        this.requestTag = requestTag;
    }

    public void cancelAllRequest() {
        RequestQueueManager.getInstance(context).getRequestQueue().cancelAll(requestTag);
    }
}