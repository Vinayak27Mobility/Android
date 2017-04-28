package android.com.gorentjoy.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.com.gorentjoy.R;
import android.com.gorentjoy.model.CategoryResponse;
import android.com.gorentjoy.model.DataHolder;
import android.com.gorentjoy.model.LocationResponse;
import android.com.gorentjoy.net.NetworkErrorManager;
import android.com.gorentjoy.service.CategoryService;
import android.com.gorentjoy.service.Logger;
import android.com.gorentjoy.ui.adapter.CategoryAdapter;
import android.com.gorentjoy.ui.adapter.ClickListner;
import android.com.gorentjoy.ui.adapter.LocationAdapter;
import android.com.gorentjoy.ui.widgets.CustomProgressDialog;
import android.com.gorentjoy.util.Constants;
import android.com.gorentjoy.util.Util;
import android.com.gorentjoy.util.WeakReferenceHandler;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by vinayakkulkarni on 7/7/16.
 */
public class FetchContentActivity extends Activity implements ClickListner {

    private String queryParam;
    private int queryParamIndex;
    private final String TAG = FetchContentActivity.class.getSimpleName();
    private Context context;
    private ProgressDialog progressDialog;
    private RecyclerView mRecyclerView;
    private TextView emptyView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fetch_content);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.fetch_content_caption));

        mRecyclerView = (RecyclerView) findViewById(R.id.list_view);
        emptyView = (TextView) findViewById(R.id.empty_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        context = this;

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    if (mAdapter != null) {
                        if (queryParam.equals(Constants.FETCH_CATEGORY)) {
                            ((CategoryAdapter) mAdapter).setFilter(null);
                        } else if(queryParam.equals(Constants.FETCH_LOCATION)) {
                            ((LocationAdapter) mAdapter).setFilter(null);
                        }
                        mRecyclerView.setVisibility(View.VISIBLE);
                        emptyView.setVisibility(View.GONE);
                    }
                } else {
                    if (queryParam.equals(Constants.FETCH_CATEGORY)) {
                        final List<CategoryResponse.Category> filteredModelList = filterCate(newText);
                        if (filteredModelList == null || filteredModelList.isEmpty()) {
                            mRecyclerView.setVisibility(View.GONE);
                            emptyView.setVisibility(View.VISIBLE);
                        } else {
                            mRecyclerView.setVisibility(View.VISIBLE);
                            emptyView.setVisibility(View.GONE);
                            ((CategoryAdapter) mAdapter).setFilter(filteredModelList);
                        }
                    } else if(queryParam.equals(Constants.FETCH_LOCATION)) {
                        final List<LocationResponse.Location> filteredModelList = filterLoca(newText);
                        if (filteredModelList == null || filteredModelList.isEmpty()) {
                            mRecyclerView.setVisibility(View.GONE);
                            emptyView.setVisibility(View.VISIBLE);
                        } else {
                            mRecyclerView.setVisibility(View.VISIBLE);
                            emptyView.setVisibility(View.GONE);
                            ((LocationAdapter) mAdapter).setFilter(filteredModelList);
                        }
                    }
                }
                return true;
            }
        });

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                queryParam = extras.getString(Constants.FETCH_CONTENT_REQ);
                Logger.log(Logger.LOG_TYPE.DEBUG, TAG, "queryParam " + queryParam);
                queryParamIndex = Integer.parseInt(queryParam);

                Util.hideSoftKeyboard(this);
                if (queryParam.equals(Constants.FETCH_CATEGORY)) {
                    if (DataHolder.getInstance().getMergedCategories() != null && !(DataHolder.getInstance().getMergedCategories().isEmpty())) {
                        mRecyclerView.setVisibility(View.VISIBLE);
                        emptyView.setVisibility(View.GONE);
                        // specify an adapter (see also next example)
                        mAdapter = new CategoryAdapter(context, DataHolder.getInstance().getMergedCategories());
                        ((CategoryAdapter) mAdapter).setListner(this);
                        mRecyclerView.setAdapter(mAdapter);
                        searchView.setVisibility(View.VISIBLE);
                    } else {
                        mRecyclerView.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);
                        searchView.setVisibility(View.GONE);
                    }
                } else if (queryParam.equals(Constants.FETCH_LOCATION)) {
                    if(DataHolder.getInstance().getMergedLocations() != null && !(DataHolder.getInstance().getMergedLocations().isEmpty())) {
                        mAdapter = new LocationAdapter(context, DataHolder.getInstance().getMergedLocations());
                        ((LocationAdapter) mAdapter).setListner(this);
                        mRecyclerView.setAdapter(mAdapter);
                        searchView.setVisibility(View.VISIBLE);
                    } else {
                        progressDialog = new CustomProgressDialog(context);
                        progressDialog.show();
                        CategoryService categoryService = CategoryService.getInstance();
                        categoryService.getLocations(context, new LocationFetchHandler(this), TAG);
                    }
                }
            }
        }
    }

    private List<CategoryResponse.Category> filterCate(String query) {
        query = query.toLowerCase();
        final List<CategoryResponse.Category> filteredModelList = new ArrayList<>();
        for (CategoryResponse.Category model : DataHolder.getInstance().getMergedCategories()) {
            final String text = model.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    private List<LocationResponse.Location> filterLoca(String query) {
        query = query.toLowerCase();
        final List<LocationResponse.Location> filteredModelList = new ArrayList<>();
        for (LocationResponse.Location model : DataHolder.getInstance().getMergedLocations()) {
            final String text = model.getName().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }

    @Override
    public void ItemClicked(Object selectedObj) {
        if (queryParam.equals(Constants.FETCH_CATEGORY)) {
            Intent intent = new Intent();
            String[] data = new String[2];
            CategoryResponse.Category category = (CategoryResponse.Category) selectedObj;
            data[0] = category.getName();
            data[1] = category.getIdCategory();
            intent.putExtra(Constants.FETCH_CONTENT_REQ, data);
            DataHolder.getInstance().setParentCategoryId(category.getIdCategory());
            setResult(queryParamIndex, intent);
        } else if (queryParam.equals(Constants.FETCH_LOCATION)) {
            Intent intent = new Intent();
            String[] data = new String[2];
            LocationResponse.Location location = (LocationResponse.Location) selectedObj;
            data[0] = location.getName();
            data[1] = location.getIdLocation();
            intent.putExtra(Constants.FETCH_CONTENT_REQ, data);
            DataHolder.getInstance().setSelectedCityId(location.getIdLocation());
            setResult(queryParamIndex, intent);
        }
        finish();
    }

    private static final class LocationFetchHandler extends WeakReferenceHandler<FetchContentActivity> {

        Context context;

        public LocationFetchHandler(FetchContentActivity reference) {
            super(reference);
        }

        @Override
        protected void handleMessage(FetchContentActivity reference, Message msg) {
            context = reference.context;
            if (msg.arg2 == Constants.SUCCESS) {
                List<LocationResponse.Location> dataSet = (List) msg.obj;

                if (!dataSet.isEmpty()) {
                    DataHolder.getInstance().setAllLocations(dataSet);
                    List<LocationResponse.Location> citySet = new ArrayList<>();
                    List<LocationResponse.Location> areaSet = new ArrayList<>();
                    List<LocationResponse.Location> mergedLocation = new ArrayList<>();
                    for (LocationResponse.Location location : dataSet) {
                        if (location.getParentDeep().equals("1")) {
                            areaSet.add(location);
                        } else if (location.getParentDeep().equals("0")) {
                            citySet.add(location);
                        }
                    }
                    if (citySet.isEmpty()) {
                        reference.mRecyclerView.setVisibility(View.GONE);
                        reference.emptyView.setVisibility(View.VISIBLE);
                        reference.searchView.setVisibility(View.GONE);
                    } else {
                        Collections.sort(citySet, new LocationComparator());
                        for(LocationResponse.Location city : citySet) {
                            mergedLocation.add(city);
                            for(LocationResponse.Location area : areaSet) {
                                if(area.getIdLocationParent().equals(city.getIdLocation())) {
                                    mergedLocation.add(area);
                                }
                            }
                        }
                        if(!(mergedLocation.isEmpty())) {
                            DataHolder.getInstance().setMergedLocations(mergedLocation);
                        }


                        reference.mRecyclerView.setVisibility(View.VISIBLE);
                        reference.emptyView.setVisibility(View.GONE);
                        // specify an adapter (see also next example)
                        reference.mAdapter = new LocationAdapter(context, mergedLocation);
                        ((LocationAdapter) reference.mAdapter).setListner(reference);
                        reference.mRecyclerView.setAdapter(reference.mAdapter);
                        reference.searchView.setVisibility(View.VISIBLE);
                    }
                }
            } else {
                NetworkErrorManager.showErrors(context, msg.arg2, null);
            }
            reference.progressDialog.dismiss();
        }

        class LocationComparator implements Comparator<LocationResponse.Location> {

            @Override
            public int compare(LocationResponse.Location e1, LocationResponse.Location e2) {
                if (e1.getOrder().equals(e2.getOrder()))
                    return (e1.getName().compareTo(e2.getName()));
                else
                    return (Integer.valueOf(e1.getOrder()).compareTo(Integer.valueOf(e2.getOrder())));
            }
        }
    }
}
