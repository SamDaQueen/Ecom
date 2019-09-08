package com.android.ecom.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.ecom.Adapters.ProductAdapter;
import com.android.ecom.Models.Product;
import com.android.ecom.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.android.ecom.Fragments.CartFragment.cart_list;
import static com.android.ecom.Fragments.CartFragment.total;

public class ProductsActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ChildEventListener childEventListener;
    ArrayList<Product> arrayList;
    ProductAdapter productAdapter;
    ListView listView;
    String category;
    static TextView total_text;
    Button goToCart;

    public static void updateTotal() {
        total_text.setText(String.format("Total: \u20B9 %s", String.valueOf(total)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);
        Intent intent = getIntent();
        if (intent != null)
            category = intent.getStringExtra("category");
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        //TODO: back button
//        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(category);
        setUpView();
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
                    if (!cart_list.contains(product))
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

    private void setUpView() {
        listView = findViewById(R.id.product_list);
        arrayList = new ArrayList<>();
        productAdapter = new ProductAdapter(
                ProductsActivity.this, R.layout.product_tile, arrayList);
        getFromRD();
        listView.setAdapter(productAdapter);
        total_text = findViewById(R.id.total_view);
        total_text.setText(String.format("Total: \u20B9 %s", String.valueOf(total)));

        //TODO : make go to cart button functionality
//        goToCart = findViewById(R.id.go_to_cart);
//        goToCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ProductsActivity.this, MainActivity.class);
//                intent.putExtra("fragment", "cart");
//                startActivity(intent);
//            }
//        });
    }
}
