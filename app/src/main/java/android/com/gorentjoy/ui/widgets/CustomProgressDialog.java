package android.com.gorentjoy.ui.widgets;

import android.app.ProgressDialog;
import android.com.gorentjoy.R;
import android.content.Context;

/**
 * Created by vinayakkulkarni on 7/8/16.
 */
public class CustomProgressDialog extends ProgressDialog {
    public CustomProgressDialog(Context context) {
        super(context);
        setCancelable(false);
        setMessage(context.getResources().getString(R.string.loading));
    }
}
