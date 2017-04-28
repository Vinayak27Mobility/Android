package android.com.gorentjoy.util;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by vinayak_kulkarni on 6/29/2016.
 */
public abstract class WeakReferenceHandler<T> extends Handler {
    private WeakReference<T> mReference;

    public WeakReferenceHandler(T reference) {
        mReference = new WeakReference<>(reference);
    }

    @Override
    public void handleMessage(Message msg) {
        T reference = mReference.get();
        if (reference == null)
            return;
        handleMessage(reference, msg);
    }

    protected abstract void handleMessage(T reference, Message msg);
}
