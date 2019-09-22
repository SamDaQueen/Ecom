package com.android.ecom.Adapters;

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

import com.android.ecom.Fragments.CartFragment;
import com.android.ecom.Models.Product;
import com.android.ecom.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;
import static com.android.ecom.Fragments.CartFragment.cart_list;
import static com.android.ecom.Fragments.CategoryFragment.updateTotal;

public class ProductAdapter extends ArrayAdapter<Product> {

    StorageReference storageReference;

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
        final ImageView productImage = convertView.findViewById(R.id.product_photo);
        decrement.setEnabled(false);

        final Product product = getItem(position);
        Log.d(TAG, "getView: " + product.getName() + product.getPhoto());

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

