package android.com.gorentjoy.ui.adapter;

import android.app.Activity;
import android.com.gorentjoy.R;
import android.com.gorentjoy.ui.widgets.SquareImageView;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vinayak_kulkarni on 11/2/2016.
 */
public class PhotoAdaper extends BaseAdapter {
    private Context context;
    private List<Bitmap> data = new ArrayList();
    private LayoutInflater inflater;

    public PhotoAdaper(Context context, List<Bitmap> data) {
        this.context = context;
        this.data = data;
    }


    @Override
    public int getCount() {
        return (null != data ? data.size() : 0);
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
        if (row == null) {
            inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(R.layout.row_photo_grid_item, parent, false);
            holder = new ViewHolder();
            // if it's not recycled, initialize some attributes
            holder.image = (SquareImageView) row.findViewById(R.id.adphotoid);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        holder.image.setImageBitmap(data.get(position));

        //imageView.setImageResource(data[position]);
        return row;
    }

    static class ViewHolder {
        SquareImageView image;
    }
}
