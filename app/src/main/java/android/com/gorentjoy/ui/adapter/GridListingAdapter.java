package android.com.gorentjoy.ui.adapter;

import android.com.gorentjoy.R;
import android.com.gorentjoy.model.AdResponse;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by vinayakkulkarni on 7/31/16.
 */
public class GridListingAdapter extends RecyclerView.Adapter<GridListingAdapter.MyViewHolder> {

    private List<AdResponse.Ad> horizontalList;
    private Context context;
    private ClickListner clickListner;

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected ImageView imageView;
        protected TextView txtView;
        protected TextView txtPrice;
        protected AdResponse.Ad seleAd;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            imageView = (ImageView) view.findViewById(R.id.ad_photo);
            txtView = (TextView) view.findViewById(R.id.ad_name);
            txtPrice = (TextView) view.findViewById(R.id.ad_price);
        }

        @Override
        public void onClick(View view) {
            if(clickListner != null) {
                clickListner.ItemClicked(seleAd);
            }
        }
    }

    public void setListner(ClickListner listner) {

        clickListner = listner;
    }

    public GridListingAdapter(Context c, List<AdResponse.Ad> horizontalList) {
        context = c;
        this.horizontalList = horizontalList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ad_grid_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        AdResponse.Ad ad = horizontalList.get(position);
        holder.txtView.setText(ad.getTitle());
        holder.txtPrice.setText(ad.getPrice());
        if(!TextUtils.isEmpty(ad.getThumb()))
            Picasso.with(context).load(ad.getThumb()).into(holder.imageView);
        else
            holder.imageView.setImageResource(R.drawable.ad_banner);
        holder.seleAd = ad;
    }

    @Override
    public int getItemCount() {
        return horizontalList.size();
    }
}
