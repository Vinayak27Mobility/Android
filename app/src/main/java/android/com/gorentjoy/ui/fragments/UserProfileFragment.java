package android.com.gorentjoy.ui.fragments;

import android.app.Activity;
import android.com.gorentjoy.R;
import android.com.gorentjoy.ui.HomeActivity;
import android.com.gorentjoy.util.Util;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by vinayak_kulkarni on 9/30/2016.
 */
public class UserProfileFragment extends Fragment implements View.OnClickListener {
    private Context context;
    private View rootView;
    private EditText phoneET, addphoneET, addressET, pinET;
    private TextInputLayout phoneLayout, addphoneLayout, addressLayout, pinLayout;
    private Button saveBtn;
    private String selectedLocationId;
    private final String TAG = UserProfileFragment.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        ((HomeActivity) context).setActionBarTitle(context.getResources().getString(R.string.menu_profile));
        rootView = inflater.inflate(R.layout.fragment_userprofile, container, false);

        phoneET = (EditText) rootView.findViewById(R.id.editphoneid);
        addphoneET = (EditText) rootView.findViewById(R.id.editaddphoneid);
        addressET = (EditText) rootView.findViewById(R.id.editaddressid);
        pinET = (EditText) rootView.findViewById(R.id.editpinid);

        phoneLayout = (TextInputLayout) rootView.findViewById(R.id.editlayoutphoneid);
        addphoneLayout = (TextInputLayout) rootView.findViewById(R.id.editlayoutaddphoneid);
        addressLayout = (TextInputLayout) rootView.findViewById(R.id.editlayoutaddressid);
        pinLayout = (TextInputLayout) rootView.findViewById(R.id.editlayoutpinid);

        saveBtn = (Button) rootView.findViewById(R.id.btnsaveid);
        saveBtn.setOnClickListener(this);

        //load data from db

        //

        return rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            // Set title

        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == saveBtn.getId()) {
            String phoneTxt = phoneET.getText().toString().trim();
            if (phoneTxt.isEmpty()) {
                phoneLayout.setError(getString(R.string.mandate));
                requestFocus(phoneET);
                return;
            } else if (!(Util.validPhone(phoneTxt))) {
                phoneLayout.setErrorEnabled(true);
                phoneLayout.setError(getString(R.string.phone_not_valid));
                requestFocus(phoneET);
                return;
            } else {
                phoneLayout.setErrorEnabled(false);
                phoneLayout.setError(null);
            }

            String addphoneTxt = addphoneET.getText().toString().trim();

            if (addphoneTxt.isEmpty()) {
            } else if (!(Util.validPhone(addphoneTxt))) {
                addphoneLayout.setErrorEnabled(true);
                addphoneLayout.setError(getString(R.string.addphone_not_valid));
                requestFocus(addphoneET);
                return;
            } else {
                addphoneLayout.setErrorEnabled(false);
                addphoneLayout.setError(null);
            }

            String addressTxt = addressET.getText().toString().trim();

            if (addressTxt.isEmpty()) {
                addressLayout.setError(getString(R.string.mandate));
                requestFocus(addressET);
                return;
            } else {
                addressLayout.setErrorEnabled(false);
                addressLayout.setError(null);
            }

            String pinTxt = pinET.getText().toString().trim();

            if (pinTxt.isEmpty()) {
                pinLayout.setError(getString(R.string.mandate));
                requestFocus(pinET);
                return;
            } else if (!(Util.validPhone(pinTxt))) {
                pinLayout.setErrorEnabled(true);
                pinLayout.setError(getString(R.string.pin_not_valid));
                requestFocus(pinET);
                return;
            } else {
                pinLayout.setErrorEnabled(false);
                pinLayout.setError(null);
            }

            //update data in the server new upate userprofile api

            //
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            ((Activity) context).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
