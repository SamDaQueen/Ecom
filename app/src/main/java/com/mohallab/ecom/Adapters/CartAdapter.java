package com.mohallab.ecom.Adapters;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mohallab.ecom.Fragments.CartFragment;
import com.mohallab.ecom.Models.Product;
import com.mohallab.ecom.R;

import java.util.ArrayList;

public class CartAdapter extends ArrayAdapter<Product> {

    private StorageReference storageReference;

    public CartAdapter(Context context, int resource, ArrayList<Product> objects) {
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
        final TextView price = convertView.findViewById(R.id.product_price);
        final TextView quantity = convertView.findViewById(R.id.quantity);
        Button increment = convertView.findViewById(R.id.increment);
        final Button decrement = convertView.findViewById(R.id.decrement);
        final ImageView productImage = convertView.findViewById(R.id.product_photo);

        final Product product = getItem(position);

        if (product != null) {
            name.setText(product.getName());
            size.setText(String.format("Quantity: %s", product.getSize()));
            MRP.setText(String.format("MRP: %s", String.valueOf(product.getMRP())));
            price.setText(String.format("Our price:%s", String.valueOf(product.getPrice())));
            quantity.setText(String.valueOf(product.getQuantity()));
            storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://ecom-8af80.appspot.com");
            if (product.getPhoto() == null)
                Glide.with(getContext())
                        .load(ContextCompat.getDrawable(getContext(), R.drawable.imageerror))
                        .into(productImage);
            else {
                storageReference.child(product.getPhoto()).getDownloadUrl()
                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // Got the download URL for 'users/me/profile.png'
                                Log.d("yay", "onSuccess");
                                Glide.with(getContext())
                                        .load(uri)
                                        .into(productImage);
                                uri = null;
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors
                        Log.d("nay", "fail");
                    }
                });
            }

            increment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int count = Integer.parseInt(quantity.getText().toString());
                    if (count < 15) {
                        count++;
                        quantity.setText(String.valueOf(count));
                        decrement.setEnabled(true);
                        product.setQuantity(count);
                        if (!CartFragment.cart_list.contains(product)) {
                            CartFragment.cart_list.add(product);
                        }
                        CartFragment.total += Float.parseFloat(String.valueOf(product.getPrice()));
                        CartFragment.updateCartTotal();
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
                        CartFragment.updateCartTotal();
                    } else {
                        CartFragment.total -= Float.parseFloat(String.valueOf(product.getPrice()));
                        product.setQuantity(0);
                        CartFragment.cart_list.remove(product);
                        CartFragment.updateList();
                        CartFragment.updateCartTotal();
                    }
                }
            });
        }

        return convertView;
    }
}
