package android.com.gorentjoy.ui;

import android.app.ProgressDialog;
import android.com.gorentjoy.R;
import android.com.gorentjoy.model.AdResponse;
import android.com.gorentjoy.model.DataHolder;
import android.com.gorentjoy.net.NetworkErrorManager;
import android.com.gorentjoy.service.ListingService;
import android.com.gorentjoy.service.Logger;
import android.com.gorentjoy.ui.adapter.ClickListner;
import android.com.gorentjoy.ui.adapter.GridListingAdapter;
import android.com.gorentjoy.ui.fragments.AdDetailFragment;
import android.com.gorentjoy.ui.widgets.CustomProgressDialog;
import android.com.gorentjoy.util.Constants;
import android.com.gorentjoy.util.Util;
import android.com.gorentjoy.util.WeakReferenceHandler;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by vinayakkulkarni on 8/2/16.
 */
public class SearchActivity extends AppCompatActivity implements ClickListner, View.OnClickListener {
    private ProgressDialog progressDialog;
    private final String TAG = SearchActivity.class.getSimpleName();
    private Context context;
    private SearchView searchView;
    private TextView cateTV, locationTV;
    private SeekBar minSeekbar, maxSeekbar;
    private TextView minText, maxText;
    private GridListingAdapter adapter;
    private RecyclerView recyclerView;
    private String selectedCateId;
    private String selectedLocationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(R.string.search_screen_caption);

        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, getResources().getInteger(R.integer.coloumns));
        recyclerView.setLayoutManager(layoutManager);

        cateTV = (TextView) findViewById(R.id.textcateid);
        cateTV.setOnClickListener(this);
        locationTV = (TextView) findViewById(R.id.textlocationid);
        locationTV.setOnClickListener(this);


        minSeekbar = (SeekBar) findViewById(R.id.minSeek);
        maxSeekbar = (SeekBar) findViewById(R.id.maxSeek);

        minText = (TextView) findViewById(R.id.minText);
        maxText = (TextView) findViewById(R.id.maxText);

        minSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i > 0)
                    minText.setText("" + i);
                else
                    minText.setText(getResources().getString(R.string.search_min));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        maxSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i > 0)
                    maxText.setText("" + i);
                else
                    maxText.setText(getResources().getString(R.string.search_max));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        searchView = ((SearchView) findViewById(R.id.searchView));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Logger.log(Logger.LOG_TYPE.DEBUG, TAG, "and here " + searchView.getQuery());
            if (TextUtils.isEmpty(searchView.getQuery())) {
                Toast.makeText(context, R.string.mandate, Toast.LENGTH_SHORT).show();
            } else {
                String inputParam = "";
                ListingService listingService = ListingService.getInstance();
                if (selectedCateId != null)
                    inputParam = "&id_category=" + selectedCateId;
                if (selectedLocationId != null)
                    inputParam = "&id_location=" + selectedLocationId;
                inputParam += "&q=" + searchView.getQuery().toString();
                String minVal = minText.getText().toString();
                String maxVal = maxText.getText().toString();
                if (!minVal.equals(getResources().getString(R.string.search_min)) && !maxVal.equals(getResources().getString(R.string.search_max))) {
                    if (Integer.parseInt(minText.getText().toString()) > 0 && Integer.parseInt(maxText.getText().toString()) > 0) {
                        inputParam += "&price__between=" + minText.getText().toString() + "," + maxText.getText().toString();
                    }
                }
                progressDialog = new CustomProgressDialog(context);
                progressDialog.show();
                searchView.clearFocus();
                Util.hideSoftKeyboard(this);
                listingService.getListings(context, new AdHandler(this), null, inputParam, TAG);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void ItemClicked(Object selObject) {
        AdResponse.Ad ad = (AdResponse.Ad) selObject;
        Logger.log(Logger.LOG_TYPE.DEBUG, TAG, "ad object " + ad.getTitle());
        DataHolder.getInstance().setSelectedAd(ad);
        Fragment fragTwo = new AdDetailFragment();
        String tag = AdDetailFragment.class.getSimpleName();
        HomeActivity.fragmentManager.beginTransaction().add(R.id.flContent, fragTwo, tag).addToBackStack(tag).commit();
    }

    @Override
    public void onClick(View v) {
        Intent inte = new Intent(context, FetchContentActivity.class);
        if (v.getId() == cateTV.getId()) {
            inte.putExtra(Constants.FETCH_CONTENT_REQ, Constants.FETCH_CATEGORY);
            startActivityForResult(inte, Integer.parseInt(Constants.FETCH_CATEGORY));
        } else if (v.getId() == locationTV.getId()) {
            inte.putExtra(Constants.FETCH_CONTENT_REQ, Constants.FETCH_LOCATION);
            startActivityForResult(inte, Integer.parseInt(Constants.FETCH_LOCATION));
        }
    }

    //@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.log(Logger.LOG_TYPE.DEBUG, TAG, "resultCode code is " + resultCode);
        Logger.log(Logger.LOG_TYPE.DEBUG, TAG, "data is " + data);
        if (data == null)
            return;
        String[] receData = data.getStringArrayExtra(Constants.FETCH_CONTENT_REQ);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, Math.round(getResources().getDimension(R.dimen.activity_horizontal_margin)), 0, 0);

        if (resultCode == Integer.parseInt(Constants.FETCH_CATEGORY)) {
            selectedCateId = receData[1];
            cateTV.setText(receData[0]);
        } else if (resultCode == Integer.parseInt(Constants.FETCH_LOCATION)) {
            selectedLocationId = receData[1];
            locationTV.setText(receData[0]);
        }
    }

    private static class AdHandler extends WeakReferenceHandler<SearchActivity> {

        Context context;

        public AdHandler(SearchActivity reference) {
            super(reference);
        }

        @Override
        public void handleMessage(SearchActivity reference, Message msg) {
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
                    reference.findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
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