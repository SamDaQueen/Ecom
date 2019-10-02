package com.android.ecom.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.ecom.Models.Product;
import com.android.ecom.R;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class HomePageProductAdapter extends ArrayAdapter<Product> {

    StorageReference storageReference;

    public HomePageProductAdapter(@NonNull Context context, int resource, ArrayList<Product> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).
                    getLayoutInflater().inflate(R.layout.home_page_product_tile, parent, false);
        }

        TextView name = convertView.findViewById(R.id.tile_name);
        final ImageView imageView = convertView.findViewById(R.id.tile_image);

        Product homeTile = getItem(position);


        if (homeTile != null) {
            Log.d("inner", "getView: " + homeTile.getName());
            name.setText(homeTile.getName());
        }

//        if (homeTile != null) {
//            name.setText(homeTile.getName());
//            // Create a storage reference from our app
//            storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://ecom-8af80.appspot.com");
//            // Create a reference with an initial file path and name
//            //StorageReference pathReference = storageReference.child("/Categories/" + homeTile.getPhoto());
//            storageReference.child("/Categories/" + homeTile.getPhoto()).getDownloadUrl()
//                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            // Got the download URL for 'users/me/profile.png'
//                            Log.d("yay", "onSuccess");
//                            Glide.with(getContext())
//                                    .load(uri)
//                                    .into(imageView);
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception exception) {
//                    // Handle any errors
//                    Log.d("nay", "fail");
//                }
//            });
//        }
        return convertView;
    }

//    @Override
//    public long getItemId(int position) {
//        return 0;
//    }
}
