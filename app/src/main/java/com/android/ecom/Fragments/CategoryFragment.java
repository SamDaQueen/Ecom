package com.android.ecom.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Objects;

import static com.android.ecom.Fragments.CartFragment.cart_list;
import static com.android.ecom.Fragments.CartFragment.total;

public class CategoryFragment extends Fragment {

    static TextView total_text;
    String category;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ChildEventListener childEventListener;
    ArrayList<Product> arrayList;
    ProductAdapter productAdapter;
    ListView listView;
    Button goToCart;

    public CategoryFragment() {
    }

    public static void updateTotal() {
        total_text.setText(String.format("Total: \u20B9 %s", String.valueOf(total)));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_category, container, false);
        if (getArguments() != null) {
            category = getArguments().getString("category");
        }
        setUpView(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Objects.requireNonNull(getActivity()).setTitle(category);
    }

    private void setUpView(View root) {
        listView = root.findViewById(R.id.product_list);
        arrayList = new ArrayList<>();
        productAdapter = new ProductAdapter(
                getContext(), R.layout.product_tile, arrayList);
        getFromRD();
        listView.setAdapter(productAdapter);
        total_text = root.findViewById(R.id.total_view);
        total_text.setText(String.format("Total: \u20B9 %s", String.valueOf(total)));

        goToCart = root.findViewById(R.id.go_to_cart);
        goToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new CartFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
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
}
