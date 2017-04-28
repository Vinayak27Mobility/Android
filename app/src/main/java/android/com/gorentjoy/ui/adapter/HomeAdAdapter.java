package android.com.gorentjoy.ui.adapter;

/**
 * Created by pratap.kesaboyina on 24-12-2014.
 */

import android.com.gorentjoy.R;
import android.com.gorentjoy.model.DataHolder;
import android.com.gorentjoy.model.SectionDataModel;
import android.com.gorentjoy.ui.HomeActivity;
import android.com.gorentjoy.ui.fragments.AdsFragment;
import android.com.gorentjoy.util.Constants;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class HomeAdAdapter extends RecyclerView.Adapter<HomeAdAdapter.ItemRowHolder> {

    private List<SectionDataModel> dataList = new ArrayList<>();
    private Context mContext;

    public HomeAdAdapter(Context context, SectionDataModel dataList) {
        this.dataList.add(dataList);
        this.mContext = context;
    }


    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ad_dashboard, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    public void addView(SectionDataModel data) {
        dataList.add(data);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {

        final String sectionName = dataList.get(i).getHeaderTitle();
        final String sectionId = dataList.get(i).getHeaderId();

        final ArrayList singleSectionItems = dataList.get(i).getAllItemsInSection();

        itemRowHolder.itemTitle.setText(sectionName);

        HomeAdSectionAdapter itemListDataAdapter = new HomeAdSectionAdapter(mContext, singleSectionItems);

        itemRowHolder.recycler_view_list.setHasFixedSize(true);
        itemRowHolder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        itemRowHolder.recycler_view_list.setAdapter(itemListDataAdapter);


        itemRowHolder.recycler_view_list.setNestedScrollingEnabled(false);

        itemRowHolder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragTwo = new AdsFragment();
                String tag = AdsFragment.class.getSimpleName();
                Bundle arguments = new Bundle();
                arguments.putString(Constants.CATE_KEY, sectionId);
                arguments.putString(Constants.CATE_KEY_NAME, sectionName);
                arguments.putInt(Constants.FRAGMENT_KEY, R.id.nav_home);
                DataHolder.getInstance().setSelectedAds(singleSectionItems);
                fragTwo.setArguments(arguments);
                ((HomeActivity) mContext).fragmentManager.beginTransaction().add(R.id.flContent, fragTwo, tag).addToBackStack(tag).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView itemTitle;
        protected RecyclerView recycler_view_list;
        protected Button btnMore;

        public ItemRowHolder(View view) {
            super(view);
            this.itemTitle = (TextView) view.findViewById(R.id.catenameid);
            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.catelistid);
            this.btnMore = (Button) view.findViewById(R.id.seeallid);
        }
    }
}