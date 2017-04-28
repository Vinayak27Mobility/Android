package android.com.gorentjoy.ui.adapter;

/**
 * Created by vinayakkulkarni on 7/14/16.
 */

import android.com.gorentjoy.R;
import android.com.gorentjoy.model.CategoryResponse;
import android.com.gorentjoy.model.DataHolder;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<CategoryResponse.Category> mDataset;
    private Context context;
    private ClickListner clickListner;
    //private List<CategoryResponse.Category> items;

    public void setFilter(List<CategoryResponse.Category> countryModels) {
        mDataset = new ArrayList<>();
        if(countryModels == null) {
            mDataset.addAll(DataHolder.getInstance().getMergedCategories());
        } else {
            mDataset.addAll(countryModels);
        }
        notifyDataSetChanged();
    }

    public void setListner(ClickListner listner) {
        clickListner = listner;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        protected TextView cateName;
        protected ImageView cateIcon;
        protected CategoryResponse.Category selectedCate;

        public ViewHolder(View v) {
            super(v);
            cateName = (TextView) v.findViewById(R.id.catenameid);
            cateIcon = (ImageView) v.findViewById(R.id.cateiconid);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(clickListner!= null) {
                        clickListner.ItemClicked(selectedCate);
                    }
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public CategoryAdapter(Context c, List<CategoryResponse.Category> myDataset) {
        context = c;
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        View v = null;
        switch (viewType) {
            case 0:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category_item, parent, false);
                break;
            case 1:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sub_category_item, parent, false);
                break;
            case 2:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sub_sub_category_item, parent, false);
                break;
            case 3:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sub_sub_sub_category_item, parent, false);
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
        CategoryResponse.Category cate = mDataset.get(position);
        return Integer.parseInt(cate.getParentDeep());
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final CategoryResponse.Category category = mDataset.get(position);
        if (!(category.getHasImage().equals("0"))) {
            Picasso.with(context).load(category.getIcon()).into(holder.cateIcon);
        } else {
            holder.cateIcon.setImageResource(R.drawable.ad_banner);
        }
        holder.cateName.setText(category.getName());
        holder.selectedCate = category;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}

