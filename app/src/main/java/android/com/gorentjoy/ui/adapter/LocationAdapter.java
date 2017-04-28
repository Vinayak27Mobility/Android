package android.com.gorentjoy.ui.adapter;

/**
 * Created by vinayakkulkarni on 7/14/16.
 */

import android.com.gorentjoy.R;
import android.com.gorentjoy.model.DataHolder;
import android.com.gorentjoy.model.LocationResponse;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {
    private List<LocationResponse.Location> mDataset;
    private Context context;
    private ClickListner clickListner;

    public void setFilter(List<LocationResponse.Location> locationModels) {
        mDataset = new ArrayList<>();
        if(locationModels == null) {
            mDataset.addAll(DataHolder.getInstance().getMergedLocations());
        } else {
            mDataset.addAll(locationModels);
        }
        notifyDataSetChanged();
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        protected TextView cateName;
        protected ImageView cateIcon;
        protected LocationResponse.Location seleLoca;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            cateName = (TextView) v.findViewById(R.id.catenameid);

            cateIcon = (ImageView) v.findViewById(R.id.cateiconid);
        }

        @Override
        public void onClick(View view) {
            if(clickListner!= null) {
                clickListner.ItemClicked(seleLoca);
            }
        }
    }

    public void setListner(ClickListner listner) {
        clickListner = listner;
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public LocationAdapter(Context c, List<LocationResponse.Location> myDataset) {
        context = c;
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view

        View v = null;
        switch(viewType) {
            case 0:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category_item, parent, false);
                break;
            case 1:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sub_category_item, parent, false);
                break;
        }
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        LocationResponse.Location location = mDataset.get(position);
        return Integer.parseInt(location.getParentDeep());
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final LocationResponse.Location location = mDataset.get(position);
        /*if (!(location.getHasImage().equals("0"))) {
            Picasso.with(context).load(location.getIcon()).into(holder.cateIcon);
        } else {
            holder.cateIcon.setImageResource(R.drawable.ad_banner);
        }*/
        holder.cateName.setText(location.getName());
        holder.seleLoca = location;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}

