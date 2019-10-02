package com.android.ecom.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ecom.Models.Category_Model;
import com.android.ecom.Models.Product;
import com.android.ecom.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class HomeTileAdapter extends ArrayAdapter<Category_Model> {

    private ChildEventListener innerChildEventListener;

    public HomeTileAdapter(Context context, int resource, ArrayList<Category_Model> objects) {
        super(context, resource);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).
                    getLayoutInflater().inflate(R.layout.home_tile, parent, false);
        }

        final TextView category = convertView.findViewById(R.id.home_category);
        Button shop = convertView.findViewById(R.id.home_shop);

        final Category_Model category_model = getItem(position);
        Log.d("Home Page", "getView: " + category_model.getName());
        if (category_model != null) {
            category.setText(category_model.getName());
        }
        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), category.getText(), Toast.LENGTH_LONG).show();
            }
        });
        getInnerItems(convertView, category_model.getName());
        return convertView;
    }

    private void getInnerItems(final View root, String name) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        ListView innerList = root.findViewById(R.id.category_items);
        ArrayList<Product> innerArrayList = new ArrayList<>();
        final HomePageProductAdapter homePageProductAdapter = new HomePageProductAdapter(
                getContext(), R.layout.home_page_product_tile, innerArrayList);
        DatabaseReference innerDatabaseReference = firebaseDatabase.getReference().child(name);
        Log.d("inner", "getInnerItems: " + name);
        if (innerChildEventListener == null) {
            innerChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Product product = dataSnapshot.getValue(Product.class);
                    Log.d("inner_success", "onChildAdded: "
                            + product.getId() + product.getName() + product.getPhoto() + product.getMRP());
                    homePageProductAdapter.add(product);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            innerDatabaseReference.orderByKey().limitToFirst(7).addChildEventListener(innerChildEventListener);
        }
        innerList.setAdapter(homePageProductAdapter);
    }
}
