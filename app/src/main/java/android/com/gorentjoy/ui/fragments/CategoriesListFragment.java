package android.com.gorentjoy.ui.fragments;

import android.com.gorentjoy.R;
import android.com.gorentjoy.model.CategoryResponse;
import android.com.gorentjoy.model.DataHolder;
import android.com.gorentjoy.service.Logger;
import android.com.gorentjoy.ui.HomeActivity;
import android.com.gorentjoy.ui.adapter.CategoryAdapter;
import android.com.gorentjoy.ui.adapter.ClickListner;
import android.com.gorentjoy.util.Constants;
import android.com.gorentjoy.util.Util;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinayak_kulkarni on 9/30/2016.
 */
public class CategoriesListFragment extends Fragment implements ClickListner{
    private Context context;
    private final String TAG = CategoriesListFragment.class.getSimpleName();
    private View rootView;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private TextView emptyView;
    private RecyclerView.LayoutManager mLayoutManager;
    private int menuId;
    private SearchView searchView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();

        ((HomeActivity) context).setActionBarTitle(context.getResources().getString(R.string.menu_cate));

        rootView = inflater.inflate(R.layout.fragment_cate, container, false);

        menuId = getArguments().getInt(Constants.FRAGMENT_KEY);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list_view);
        emptyView = (TextView) rootView.findViewById(R.id.empty_view);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(context);
        mRecyclerView.setLayoutManager(mLayoutManager);

        searchView = (SearchView) rootView.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    if (mAdapter != null) {
                        ((CategoryAdapter) mAdapter).setFilter(null);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        emptyView.setVisibility(View.GONE);
                    }
                } else {
                    final List<CategoryResponse.Category> filteredModelList = filter(newText);
                    if (filteredModelList == null || filteredModelList.isEmpty()) {
                        mRecyclerView.setVisibility(View.GONE);
                        emptyView.setVisibility(View.VISIBLE);
                    } else {
                        mRecyclerView.setVisibility(View.VISIBLE);
                        emptyView.setVisibility(View.GONE);
                        ((CategoryAdapter) mAdapter).setFilter(filteredModelList);
                    }
                    return true;
                }
                return true;
            }
        });

        if (DataHolder.getInstance().getMergedCategories() != null && !(DataHolder.getInstance().getMergedCategories().isEmpty())) {
            mRecyclerView.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            // specify an adapter (see also next example)
            mAdapter = new CategoryAdapter(context, DataHolder.getInstance().getMergedCategories());
            ((CategoryAdapter) mAdapter).setListner(this);
            mRecyclerView.setAdapter(mAdapter);
            searchView.setVisibility(View.VISIBLE);
        } else {
            searchView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }

        return rootView;
    }

    private List<CategoryResponse.Category> filter(String query) {
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

    @Override
    public void ItemClicked(Object selectedObj) {
        Util.hideSoftKeyboard(getActivity());
        searchView.clearFocus();
        Logger.log(Logger.LOG_TYPE.DEBUG, TAG, "selected category " + ((CategoryResponse.Category) selectedObj).getName());
        Fragment fragTwo = new AdsFragment();
        String tag = AdsFragment.class.getSimpleName();
        Bundle arguments = new Bundle();
        arguments.putString(Constants.CATE_KEY, ((CategoryResponse.Category) selectedObj).getIdCategory());
        arguments.putString(Constants.CATE_KEY_NAME, ((CategoryResponse.Category) selectedObj).getName());
        arguments.putInt(Constants.FRAGMENT_KEY, menuId);
        fragTwo.setArguments(arguments);
        ((HomeActivity) context).fragmentManager.beginTransaction().add(R.id.flContent, fragTwo, tag).addToBackStack(tag).commit();
    }
}
