package com.mohallab.ecom.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mohallab.ecom.Models.Product;
import com.mohallab.ecom.R;

import java.util.ArrayList;
import java.util.List;

public class HomeTileAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    private List<Product> list;
    private StorageReference storageReference;

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
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        myViewHolder.textView.setText(list.get(i).getName());
        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://ecom-8af80.appspot.com");
        if (list.get(i).getPhoto() == null)
            Glide.with(context)
                    .load(ContextCompat.getDrawable(context, R.drawable.imageerror))
                    .into(myViewHolder.imageView);
        else {
            storageReference.child(list.get(i).getPhoto()).getDownloadUrl()
                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Got the download URL for 'users/me/profile.png'
                            Log.d("yay", "onSuccess");
                            Glide.with(context)
                                    .load(uri)
                                    .into(myViewHolder.imageView);
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}