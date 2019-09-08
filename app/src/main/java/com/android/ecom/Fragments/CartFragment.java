package com.android.ecom.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.ecom.Adapters.CartAdapter;
import com.android.ecom.Models.Product;
import com.android.ecom.R;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    public static ArrayList<Product> cart_list;
    static CartAdapter cartAdapter;
    ListView listView;

    public CartFragment() {
        // Required empty public constructor
    }

    public static void updateList() {
        cartAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_cart, container, false);
        setUpView(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        Objects.requireNonNull(getActivity()).setTitle("Cart");
    }

    private void setUpView(View root) {
        listView = root.findViewById(R.id.cart_list);
        cartAdapter = new CartAdapter(
                getContext(), R.layout.product_tile, cart_list);
        listView.setAdapter(cartAdapter);

    }

}
