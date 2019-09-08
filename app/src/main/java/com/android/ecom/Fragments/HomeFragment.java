package com.android.ecom.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.android.ecom.Activities.ProductsActivity;
import com.android.ecom.Adapters.HomeTileAdapter;
import com.android.ecom.Models.HomeTile;
import com.android.ecom.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

import static com.android.ecom.Fragments.CartFragment.cart_list;

public class HomeFragment extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ChildEventListener childEventListener;
    ArrayList<HomeTile> arrayList;
    HomeTileAdapter homeTileAdapter;
    GridView gridView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
//        gridView.setAdapter(new GridViewAdapter(getContext(), MOBILE_OS));

        gridView = root.findViewById(R.id.home_grid);
        arrayList = new ArrayList<>();
        homeTileAdapter = new HomeTileAdapter(
                Objects.requireNonNull(getActivity()), R.layout.category_tile, arrayList);
        getFromRD();
        gridView.setAdapter(homeTileAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getActivity(), ProductsActivity.class);
                intent.putExtra("category", arrayList.get(position).getName());
                startActivity(intent);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        cart_list = new ArrayList<>();
        Objects.requireNonNull(getActivity()).setTitle("Home");

    }

    public void getFromRD() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Categories");
        attachDatabaseReadListener();
    }

    private void attachDatabaseReadListener() {
        if (childEventListener == null) {
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    HomeTile homeTile = dataSnapshot.getValue(HomeTile.class);
                    Log.d("success", "onChildAdded: " + homeTile.getId() + homeTile.getName() + homeTile.getPhoto());
                    homeTileAdapter.add(homeTile);
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

//    @Override
//    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//        Toast.makeText(getActivity(),"i was clicked",Toast.LENGTH_SHORT ).show();
//        Intent intent = new Intent(getActivity(), ProductsActivity.class);
//        intent.putExtra("category", arrayList.get(position).getName());
//        startActivity(intent);
//    }
}
