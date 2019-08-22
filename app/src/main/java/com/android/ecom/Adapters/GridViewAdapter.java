package com.android.ecom.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.ecom.R;


public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private final String[] mobileValues;

    public GridViewAdapter(Context context, String[] mobileValues) {
        this.context = context;
        this.mobileValues = mobileValues;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.category_tile, null);

            // set value into textview
            TextView textView = (TextView) gridView
                    .findViewById(R.id.tile_name);
            textView.setText(mobileValues[position]);

            // set image based on selected text
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.tile_image);

            String mobile = mobileValues[position];

            if (mobile.equals("Windows")) {
                imageView.setImageResource(R.drawable.fruit);
            } else if (mobile.equals("iOS")) {
                imageView.setImageResource(R.drawable.fruit);
            } else if (mobile.equals("Blackberry")) {
                imageView.setImageResource(R.drawable.fruit);
            } else {
                imageView.setImageResource(R.drawable.fruit);
            }

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return mobileValues.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}