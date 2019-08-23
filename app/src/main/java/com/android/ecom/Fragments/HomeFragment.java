package com.android.ecom.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

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
//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v,
//                                    int position, long id) {
//                Toast.makeText(
//                        getContext(),
//                        ((TextView) v.findViewById(R.id.tile_name))
//                                .getText(), Toast.LENGTH_SHORT).show();
//
//            }
//        });

        gridView = root.findViewById(R.id.home_grid);
        arrayList = new ArrayList<>();
        homeTileAdapter = new HomeTileAdapter(
                Objects.requireNonNull(getActivity()), R.layout.category_tile, arrayList);
        getFromRD();
        gridView.setAdapter(homeTileAdapter);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
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
}