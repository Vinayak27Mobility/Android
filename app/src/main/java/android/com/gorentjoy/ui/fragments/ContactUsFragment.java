package android.com.gorentjoy.ui.fragments;

import android.app.Dialog;
import android.com.gorentjoy.R;
import android.com.gorentjoy.util.Constants;
import android.com.gorentjoy.ui.HomeActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * Created by vinayak_kulkarni on 9/30/2016.
 */
public class ContactUsFragment extends Fragment implements View.OnClickListener {
    private Context context;
    private FloatingActionButton call, msg, mail;

    private final String TAG = ContactUsFragment.class.getSimpleName();
    private View rootView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();

        ((HomeActivity)context).setActionBarTitle(context.getResources().getString(R.string.menu_contact));

        rootView = inflater.inflate(R.layout.fragment_contact_us, container, false);

        call = (FloatingActionButton) rootView.findViewById(R.id.call);
        msg = (FloatingActionButton) rootView.findViewById(R.id.message);
        mail = (FloatingActionButton) rootView.findViewById(R.id.mail);
        call.setOnClickListener(this);
        msg.setOnClickListener(this);
        mail.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            // Set title
            ((HomeActivity) context).setActionBarTitle(context.getResources().getString(R.string.menu_cate));
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == call.getId()) {
            processHandle(Intent.ACTION_DIAL);
        } else if (v.getId() == msg.getId()) {
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.popup_message);
            dialog.setTitle(getResources().getString(R.string.ad_popup_title));
            dialog.setCancelable(true);
            // there are a lot of settings, for dialog, check them all out!
            // set up radiobutton
            RadioButton rd1 = (RadioButton) dialog.findViewById(R.id.sms);
            RadioButton rd2 = (RadioButton) dialog.findViewById(R.id.whatsup);
            rd1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    processHandle(Intent.ACTION_SEND);
                }
            });
            rd2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    processHandle(Intent.ACTION_MEDIA_SHARED);
                }
            });
            // now that the dialog is set up, it's time to show it
            dialog.show();
        } else if (v.getId() == mail.getId()) {
            processHandle(Intent.ACTION_SENDTO);
        }
    }

    private void processHandle(String action) {
        Intent intent = new Intent();
        try {
            if (action.equals(Intent.ACTION_DIAL)) {
                intent.setAction(action);
                intent.setData(Uri.parse("tel:" + Constants.call_centre));
            } else if (action.equals(Intent.ACTION_SEND)) {
                intent.setAction(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("smsto:"));
                intent.setType("vnd.android-dir/mms-sms");
                intent.putExtra("address", new String(Constants.msg_centre));
                intent.putExtra("sms_body", "");
            } else if (action.equals(Intent.ACTION_MEDIA_SHARED)) {
                PackageManager pm = context.getPackageManager();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String text = "";
                pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);//may throgh exception
                //Check if package exists or not. If not then code
                //in catch block will be called
                intent.setPackage("com.whatsapp");
                intent.putExtra(Intent.EXTRA_TEXT, text);
            } else if (action.equals(Intent.ACTION_SENDTO)) {
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{Constants.mail_centre});
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                intent.putExtra(Intent.EXTRA_TEXT, "");
            }
            startActivity(intent);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(context, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
