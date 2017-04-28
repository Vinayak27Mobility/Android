package android.com.gorentjoy.net;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by vinayak kulkarni on 6/28/2016.
 * <p/>
 * This class manages instance of request queue to keep only one
 * volley request queue throughout the application
 * <p/>
 * Use application context instead of activity context
 */
public class RequestQueueManager {
    private static RequestQueueManager mInstance;
    private RequestQueue sslRequestQueue;
    private RequestQueue requestQueue;

    private final Context context;

    /**
     * Pass Application Context
     */
    private RequestQueueManager(Context context) {
        this.context = context;
    }

    public static synchronized RequestQueueManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RequestQueueManager(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }
}
