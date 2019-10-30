package com.mohallab.ecom.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mohallab.ecom.R;

class MyViewHolder extends RecyclerView.ViewHolder {

    TextView textView;
    ImageView imageView;

    MyViewHolder(View v) {
        super(v);
        textView = v.findViewById(R.id.tile_name);
        imageView = v.findViewById(R.id.tile_image);
    }
}