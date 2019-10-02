package com.android.ecom.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.ecom.R;

public class MyViewHolder extends RecyclerView.ViewHolder {

    public TextView textView;
    public ImageView imageView;

    public MyViewHolder(View v) {
        super(v);
        textView = v.findViewById(R.id.tile_name);
        imageView = v.findViewById(R.id.tile_image);
    }
}