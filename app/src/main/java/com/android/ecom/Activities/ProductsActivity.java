package com.android.ecom.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.android.ecom.Adapters.ProductAdapter;
import com.android.ecom.Models.Product;
import com.android.ecom.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProductsActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ChildEventListener childEventListener;
    ArrayList<Product> arrayList;
    ProductAdapter productAdapter;
    ListView listView;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        Intent intent = getIntent();
        if (intent != null)
            category = intent.getStringExtra("category");
        setUpView();
    }

    private void setUpView() {
        listView = findViewById(R.id.product_list);
        arrayList = new ArrayList<>();
        productAdapter = new ProductAdapter(
                ProductsActivity.this, R.layout.product_tile, arrayList);
        getFromRD();
        listView.setAdapter(productAdapter);
    }

    private void getFromRD() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child(category);
        attachDatabaseReadListener();
    }

    private void attachDatabaseReadListener() {
        if (childEventListener == null) {
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Product product = dataSnapshot.getValue(Product.class);
                    Log.d("success", "onChildAdded: "
                            + product.getId() + product.getName() + product.getSize()
                            + product.getMRP() + product.getPrice());
                    productAdapter.add(product);
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
            databaseReference.addChildEventListener(childEventListener);
        }
    }
}
