package android.com.gorentjoy.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.com.gorentjoy.R;
import android.com.gorentjoy.model.CategoryResponse;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by vinayak_kulkarni on 4/14/2016.
 */
public class Util {

    public static boolean validEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public static boolean validPhone(String phone) {
        if(phone.length() == 10)
            return true;
        else
            return false;
    }

    public static boolean validDesc(String desc) {
        if(desc.length() >= 5)
            return true;
        else
            return false;
    }

    public static boolean validPin(String pin) {
        if(pin.length() < 6)
            return true;
        else
            return false;
    }

    public static void hideSoftKeyboard(Activity context) {
        InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(context.getWindow().getDecorView().getApplicationWindowToken(), 0);
            context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
    }

    public static AlertDialog.Builder createAlert(Context context,
                                                  View layout,
                                                  String title,
                                                  String message,
                                                  int positiveButtonText,
                                                  int negativeButtonText,
                                                  int neutralButtonText,
                                                  DialogInterface.OnClickListener positiveButtonClickListener,
                                                  DialogInterface.OnClickListener negativeButtonClickListener,
                                                  DialogInterface.OnClickListener neutralButtonClickListener) {

        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        if (title != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.alert_title, null);
            TextView textView = (TextView) view.findViewById(R.id.alert_title);
            textView.setText(title);
            //FontUtil.setRobotoBoldFont(context, textView);
            //alert.setView(view);
            alert.setCustomTitle(view);
        }

        if (layout != null) {
            alert.setView(layout);
        } else if (message != null) {
            alert.setMessage(message);
        }

        if (positiveButtonText > 0 && positiveButtonClickListener != null) {
            alert.setPositiveButton(positiveButtonText, positiveButtonClickListener);
        }
        if (negativeButtonText > 0 && negativeButtonClickListener != null) {
            alert.setNegativeButton(negativeButtonText, negativeButtonClickListener);
        }
        if (neutralButtonText > 0 && neutralButtonClickListener != null) {
            alert.setNeutralButton(positiveButtonText, neutralButtonClickListener);
        }

        return alert;
    }

    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        // There are no active networks.
        return cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected();
    }

    public static void styleShownAlert(Context context, AlertDialog dialog) {

        // Must call show() prior to fetching text view
        TextView messageView = (TextView) dialog.findViewById(android.R.id.message);

        if (messageView != null) {
            messageView.setGravity(Gravity.CENTER);
        }

        Button button1 = dialog.getButton(DialogInterface.BUTTON_NEUTRAL);
        Button button2 = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button button3 = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);

        int colourForAppFlow = context.getResources().getColor(R.color.services_dark);

        if (button1 != null) {
            button1.setTextColor(colourForAppFlow);
        }
        if (button2 != null) {
            button2.setTextColor(colourForAppFlow);
        }
        if (button3 != null) {
            button3.setTextColor(colourForAppFlow);
        }
    }

    public static void createAndShowAlert(Context context,
                                          View layout,
                                          String title,
                                          String message,
                                          int positiveButtonText,
                                          int negativeButtonText,
                                          int neutralButtonText,
                                          DialogInterface.OnClickListener positiveButtonClickListener,
                                          DialogInterface.OnClickListener negativeButtonClickListener,
                                          DialogInterface.OnClickListener neutralButtonClickListener) {


        AlertDialog.Builder alert = createAlert(context, layout, title, message, positiveButtonText, negativeButtonText, neutralButtonText, positiveButtonClickListener, negativeButtonClickListener, neutralButtonClickListener);

        alert.setCancelable(false);
        AlertDialog dialog = alert.show();

        styleShownAlert(context, dialog);
    }

    public static void clearFragmentStack(android.support.v4.app.FragmentManager mFragmentManager) {
        if(mFragmentManager == null) {
            return;
        } else {
            for(int i = 0; i < mFragmentManager.getBackStackEntryCount(); ++i) {
                mFragmentManager.popBackStack();
            }
        }
        /*List<Fragment> al = mFragmentManager.getFragments();
        if (al == null) {
        } else {
            for (Fragment frag : al) {
                // To save any of the fragments, add this check.
                // A tag can be added as a third parameter to the fragment when you commit it
                mFragmentManager.beginTransaction().remove(frag).commit();
            }
        }*/
    }

    public static void handleFragmentBack(android.support.v4.app.FragmentManager mFragmentManager) {
        mFragmentManager.popBackStack();
    }

    public static List<CategoryResponse.Category> isSafeList( List other ) {
        return other == null ? Collections.EMPTY_LIST : other;
    }
}
