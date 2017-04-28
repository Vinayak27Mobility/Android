package android.com.gorentjoy.ui.adapter;

/**
 * Created by pratap.kesaboyina on 24-12-2014.
 */

import android.com.gorentjoy.R;
import android.com.gorentjoy.model.AdResponse;
import android.com.gorentjoy.model.DataHolder;
import android.com.gorentjoy.service.Logger;
import android.com.gorentjoy.ui.HomeActivity;
import android.com.gorentjoy.ui.fragments.AdDetailFragment;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HomeAdSectionAdapter extends RecyclerView.Adapter<HomeAdSectionAdapter.SingleItemRowHolder> {

    private ArrayList<AdResponse.Ad> itemsList;
    private Context mContext;
    private final String TAG = HomeAdSectionAdapter.class.getSimpleName();

    public HomeAdSectionAdapter(Context context, ArrayList<AdResponse.Ad> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public SingleItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.ad_home_item, viewGroup, false);
        SingleItemRowHolder mh = new SingleItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(SingleItemRowHolder holder, int i) {

        AdResponse.Ad singleItem = itemsList.get(i);

        holder.adPrice.setText(singleItem.getPrice());
        holder.adTitle.setText(singleItem.getTitle());
        holder.selectedAd = singleItem;

        if (!TextUtils.isEmpty(singleItem.getThumb()))
            Picasso.with(mContext).load(singleItem.getThumb()).into(holder.itemImage);
        else
            holder.itemImage.setImageResource(R.drawable.ad_banner);
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class SingleItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView adPrice;
        protected TextView adTitle;
        protected ImageView itemImage;
        protected AdResponse.Ad selectedAd;


        public SingleItemRowHolder(View view) {
            super(view);

            this.adPrice = (TextView) view.findViewById(R.id.ad_price);
            this.adTitle = (TextView) view.findViewById(R.id.ad_name);
            this.itemImage = (ImageView) view.findViewById(R.id.ad_photo);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Logger.log(Logger.LOG_TYPE.DEBUG, TAG, "ad object " + selectedAd.getTitle());
                    DataHolder.getInstance().setSelectedAd(selectedAd);
                    Fragment fragTwo = new AdDetailFragment();
                    String tag = AdDetailFragment.class.getSimpleName();

                    ((HomeActivity) mContext).fragmentManager.beginTransaction().add(R.id.flContent, fragTwo, tag).addToBackStack(tag).commit();
                }
            });
        }
    }
}