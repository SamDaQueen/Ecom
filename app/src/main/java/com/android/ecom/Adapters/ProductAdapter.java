package com.android.ecom.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ecom.Fragments.CartFragment;
import com.android.ecom.Models.Product;
import com.android.ecom.R;

import java.util.ArrayList;

import static com.android.ecom.Activities.ProductsActivity.updateTotal;
import static com.android.ecom.Fragments.CartFragment.cart_list;

public class ProductAdapter extends ArrayAdapter<Product> {

    public ProductAdapter(Context context, int resource, ArrayList<Product> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).
                    getLayoutInflater().inflate(R.layout.product_tile, parent, false);
        }

        TextView name = convertView.findViewById(R.id.product_name);
        TextView size = convertView.findViewById(R.id.product_size);
        TextView MRP = convertView.findViewById(R.id.product_MRP);
        TextView price = convertView.findViewById(R.id.product_price);
        final TextView quantity = convertView.findViewById(R.id.quantity);
        Button increment = convertView.findViewById(R.id.increment);
        final Button decrement = convertView.findViewById(R.id.decrement);
        decrement.setEnabled(false);

        final Product product = getItem(position);

        if (product != null) {
            name.setText(product.getName());
            size.setText(String.format("Quantity: %s", product.getSize()));
            MRP.setText(String.format("MRP: %s", String.valueOf(product.getMRP())));
            price.setText(String.format("Our price:%s", String.valueOf(product.getPrice())));
            quantity.setText(String.valueOf(product.getQuantity()));

            increment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int count = Integer.parseInt(quantity.getText().toString());
                    if (count < 15) {
                        count++;
                        quantity.setText(String.valueOf(count));
                        decrement.setEnabled(true);
                        product.setQuantity(count);
                        if (!cart_list.contains(product)) {
                            cart_list.add(product);
                        }
                        CartFragment.total += Float.parseFloat(String.valueOf(product.getPrice()));
                        updateTotal();
                    } else
                        Toast.makeText(getContext(), "You cannot order more than 15 items!", Toast.LENGTH_SHORT).show();
                }
            });
            decrement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int count = Integer.parseInt(quantity.getText().toString());
                    if (count > 1) {
                        count--;
                        quantity.setText(String.valueOf(count));
                        product.setQuantity(count);
                        CartFragment.total -= Float.parseFloat(String.valueOf(product.getPrice()));
                        updateTotal();
                    } else {
                        quantity.setText("0");
                        decrement.setEnabled(false);
                        CartFragment.total -= Float.parseFloat(String.valueOf(product.getPrice()));
                        updateTotal();
                    }
                }
            });
        }
        return convertView;
    }
}

