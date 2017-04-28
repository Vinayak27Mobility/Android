package android.com.gorentjoy.ui.fragments;

import android.app.ProgressDialog;
import android.com.gorentjoy.R;
import android.com.gorentjoy.model.AdResponse;
import android.com.gorentjoy.model.DataHolder;
import android.com.gorentjoy.util.Constants;
import android.com.gorentjoy.net.NetworkErrorManager;
import android.com.gorentjoy.service.ListingService;
import android.com.gorentjoy.service.Logger;
import android.com.gorentjoy.storage.SharedPrefWrapper;
import android.com.gorentjoy.ui.HomeActivity;
import android.com.gorentjoy.ui.adapter.ClickListner;
import android.com.gorentjoy.ui.adapter.GridListingAdapter;
import android.com.gorentjoy.ui.widgets.CustomProgressDialog;
import android.com.gorentjoy.util.Util;
import android.com.gorentjoy.util.WeakReferenceHandler;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by vinayak_kulkarni on 9/30/2016.
 */
public class AdsFragment extends Fragment implements ClickListner {
    private Context context;

    private final String TAG = AdsFragment.class.getSimpleName();
    private View rootView;
    private GridListingAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private int menuId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        rootView = inflater.inflate(R.layout.fragment_ads, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, getResources().getInteger(R.integer.coloumns));
        recyclerView.setLayoutManager(layoutManager);


        ListingService listingService = ListingService.getInstance();

        menuId = getArguments().getInt(Constants.FRAGMENT_KEY);
        if(menuId == R.id.nav_home) {
            ((HomeActivity) context).setActionBarTitle(getArguments().getString(Constants.CATE_KEY_NAME));
            adapter = new GridListingAdapter(context, DataHolder.getInstance().getSelectedAds());
            recyclerView.setAdapter(adapter);
            adapter.setListner(this);
        } else {
            progressDialog = new CustomProgressDialog(context);
            progressDialog.show();
            String inputParam = "";
            if (menuId == R.id.nav_cate) {
                String selectedCateId = getArguments().getString(Constants.CATE_KEY);
                ((HomeActivity) context).setActionBarTitle(getArguments().getString(Constants.CATE_KEY_NAME));
                inputParam = "&id_category=" +  selectedCateId;
            } else if (menuId == R.id.nav_store) {
                ((HomeActivity) context).setActionBarTitle(context.getResources().getString(R.string.menu_store));
                inputParam = "&id_user=" + SharedPrefWrapper.getInstance().getUserId();
            }
            listingService.getListings(context, new AdHandler(this), null, inputParam,TAG);
        }
        return rootView;
    }

    @Override
    public void ItemClicked(Object selectedObj) {
        AdResponse.Ad ad = (AdResponse.Ad) selectedObj;
        Logger.log(Logger.LOG_TYPE.DEBUG, TAG, "ad object " + ad.getTitle());
        DataHolder.getInstance().setSelectedAd(ad);

        Fragment fragTwo;
        String tag;
        if (menuId == R.id.nav_store) {
            fragTwo = new UpdateDetailFragment();
            tag = UpdateDetailFragment.class.getSimpleName();
        } else {
            fragTwo = new AdDetailFragment();
            tag = AdDetailFragment.class.getSimpleName();
        }

        ((HomeActivity) context).fragmentManager.beginTransaction().add(R.id.flContent, fragTwo, tag).addToBackStack(tag).commit();

    }


    private static class AdHandler extends WeakReferenceHandler<AdsFragment> {

        Context context;

        public AdHandler(AdsFragment reference) {
            super(reference);
        }

        @Override
        public void handleMessage(AdsFragment reference, Message msg) {
            context = reference.context;
            if (reference.progressDialog != null) {
                reference.progressDialog.dismiss();
            }

            if (msg.arg2 == Constants.SUCCESS) {
                ArrayList<AdResponse.Ad> dataSet = (ArrayList) msg.obj;
                if (!dataSet.isEmpty()) {
                    reference.adapter = new GridListingAdapter(reference.context, dataSet);
                    reference.recyclerView.setAdapter(reference.adapter);
                    reference.adapter.setListner(reference);
                } else {
                    reference.rootView.findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
                }
            } else {
                android.app.AlertDialog.Builder alert = Util.createAlert(reference.context,
                        null,
                        context.getResources().getString(R.string.error_alert_title),
                        null,
                        R.string.button_ok,
                        0,
                        0,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }
                        ,
                        null,
                        null);

                NetworkErrorManager.showErrors(context, msg.arg2, alert);
            }
        }
    }
}
