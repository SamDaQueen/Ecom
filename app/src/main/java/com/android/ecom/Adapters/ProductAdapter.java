package com.android.ecom.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.ecom.Models.Product;
import com.android.ecom.R;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {

    public ProductAdapter(Context context, int resource, ArrayList<Product> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).
                    getLayoutInflater().inflate(R.layout.product_tile, parent, false);
        }

        TextView name = convertView.findViewById(R.id.product_name);
        TextView size = convertView.findViewById(R.id.product_size);
        TextView MRP = convertView.findViewById(R.id.product_MRP);
        TextView price = convertView.findViewById(R.id.product_price);

        Product product = getItem(position);

        if (product != null) {
            name.setText(product.getName());
            size.setText(String.format("Quantity: %s", product.getSize()));
            MRP.setText(String.format("MRP: %s", String.valueOf(product.getMRP())));
            price.setText(String.format("Our price:%s", String.valueOf(product.getPrice())));
        }
        return convertView;
    }
}

