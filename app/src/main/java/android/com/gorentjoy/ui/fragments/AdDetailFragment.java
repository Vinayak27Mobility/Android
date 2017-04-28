package android.com.gorentjoy.ui.fragments;

import android.app.Dialog;
import android.com.gorentjoy.R;
import android.com.gorentjoy.model.AdResponse;
import android.com.gorentjoy.model.DataHolder;
import android.com.gorentjoy.ui.HomeActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by vinayak_kulkarni on 9/30/2016.
 */
public class AdDetailFragment extends Fragment implements View.OnClickListener{
    private Context context;

    private final String TAG = AdDetailFragment.class.getSimpleName();
    private View rootView;
    private AdResponse.Ad selectedAd;
    private Button makeCall, sendMsg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        ((HomeActivity) context).setActionBarTitle(getResources().getString(R.string.ad_detail_caption));
        rootView = inflater.inflate(R.layout.fragment_ad_detail, container, false);

        selectedAd = DataHolder.getInstance().getSelectedAd();

        if (!TextUtils.isEmpty(selectedAd.getThumb())) {
            Picasso.with(context).load(selectedAd.getThumb()).into((ImageView) rootView.findViewById(R.id.adimageid));
        }

        ((TextView) rootView.findViewById(R.id.adtitleid)).setText(selectedAd.getTitle());
        ((TextView) rootView.findViewById(R.id.adpriceid)).setText(selectedAd.getPrice());
        ((TextView) rootView.findViewById(R.id.addateid)).setText(selectedAd.getCreated());
        ((TextView) rootView.findViewById(R.id.adlocationid)).setText(selectedAd.getAddress());
        ((TextView) rootView.findViewById(R.id.addescid)).setText(selectedAd.getDescription());
        //((TextView) rootView.findViewById(R.id.adhitid)).setText(getResources().getString(R.string.ad_hit_caption) + "");
        //((TextView) rootView.findViewById(R.id.adidid)).setText(getResources().getString(R.string.ad_id_caption) + selectedAd.getIdAd());

        makeCall = (Button) rootView.findViewById(R.id.btncallid);
        sendMsg = (Button) rootView.findViewById(R.id.btnmsgid);
        makeCall.setOnClickListener(this);
        sendMsg.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == makeCall.getId()) {
            processHandle(Intent.ACTION_DIAL);
        } else if (v.getId() == sendMsg.getId()) {
            //processHandle(Intent.ACTION_SEND);
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
        }
    }

    private void processHandle(String action) {
        if (!(TextUtils.isEmpty(selectedAd.getPhone()))) {
            Intent intent = new Intent();
            try {
                if (action.equals(Intent.ACTION_DIAL)) {
                    intent.setAction(action);
                    intent.setData(Uri.parse("tel:" + selectedAd.getPhone()));
                } else if (action.equals(Intent.ACTION_SEND)) {
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("smsto:"));
                    intent.setType("vnd.android-dir/mms-sms");
                    intent.putExtra("address", new String(selectedAd.getPhone()));
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
                }
                startActivity(intent);
            } catch (PackageManager.NameNotFoundException e) {
                Toast.makeText(context, "WhatsApp not Installed", Toast.LENGTH_SHORT)
                        .show();
            }
        } else {
            Toast.makeText(context, R.string.no_phone_found, Toast.LENGTH_SHORT).show();
        }
    }
}
