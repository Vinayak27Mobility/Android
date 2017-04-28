package android.com.gorentjoy.service;

import android.com.gorentjoy.util.Constants;
import android.util.Log;

/**
 * Created by vinayak_kulkarni on 6/29/2016.
 */
public class Logger {

    private Logger() {
    }

    public enum LOG_TYPE {VERBOSE, DEBUG, INFO, WARNING, ERROR}

    public static void log(LOG_TYPE log_type, String tag, Throwable message) {
        log(log_type, tag, message.toString());
    }

    public static void log(LOG_TYPE log_type, String tag, String message) {
        if (Constants.LOGGING_ENABLED) {
            switch (log_type) {
                case VERBOSE:
                    Log.v(tag, message);
                    break;
                case DEBUG:
                    Log.d(tag, message);
                    break;
                case INFO:
                    Log.i(tag, message);
                    break;
                case WARNING:
                    Log.w(tag, message);
                    break;
                case ERROR:
                    Log.e(tag, message);
                    break;
            }
        }
    }
}
