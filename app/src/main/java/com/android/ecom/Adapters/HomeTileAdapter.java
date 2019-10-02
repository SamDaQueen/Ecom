package com.android.ecom.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.ecom.Models.Product;
import com.android.ecom.R;

import java.util.ArrayList;
import java.util.List;

public class HomeTileAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    private List<Product> list;

    public HomeTileAdapter() {
        list = new ArrayList<>();
    }

    public HomeTileAdapter(Context context, List<Product> list) {
        this.context = context;
        this.list = list;
    }

    public void add(Product product) {
        list.add(product);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.home_page_product_tile, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.textView.setText(list.get(i).getName());
        myViewHolder.imageView.setImageResource(R.drawable.imageerror);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}