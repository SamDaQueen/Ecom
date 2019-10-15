package com.mohallab.ecom.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mohallab.ecom.Adapters.HomeTileAdapter;
import com.mohallab.ecom.Models.Product;
import com.mohallab.ecom.R;

import java.util.ArrayList;
import java.util.Objects;

public class HomeFragment extends Fragment {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    DatabaseReference[] groupDatabaseReferences;
    ChildEventListener[] childEventListeners;
    HomeTileAdapter[] homeTileAdapters;
    RecyclerView[] recyclerViews;
    LinearLayoutManager[] managers;
    //    CarouselView carouselView;
    ArrayList<Product>[] lists;
    Button[] shopButtons;
    int NUMBER_OF_PAGES = 5;
    //String[] images = {"image_1", "image_2", "image_3", "image_4", "image_5"};
//    int[] sampleImages = {R.drawable.image_1, R.drawable.image_2, R.drawable.image_3, R.drawable.image_4, R.drawable.image_5};
////    ImageListener imageListener = new ImageListener() {
////        @Override
////        public void setImageForPosition(int position, ImageView imageView) {
//            imageView.setImageResource(sampleImages[position]);
//        }
//    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
//        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://ecom-8af80.appspot.com");
//        for (String image : images) {
//            getDrawable(image);
//        }
        setUpView(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        CartFragment.cart_list = new ArrayList<>();
        Objects.requireNonNull(getActivity()).setTitle("Home");
    }

    private void setUpView(View root) {
//        carouselView = root.findViewById(R.id.carouselView);
//        carouselView.setPageCount(NUMBER_OF_PAGES);
//        carouselView.setImageListener(imageListener);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        final String[] categories = {"Breakfast & Tea", "Biscuits, Snacks & Chocolates"
                , "Dry Fruits & Nuts", "Edible Oils & Vanaspati", "Food Grains"
                , "Noodles, Sauces & Instant Food", "Personal Care & Household Needs"
                , "Vegetables & Fruits", "Spices"};
        int[] resID = {R.id.breakfast_grid, R.id.biscuit_grid, R.id.nuts_grid, R.id.oils_grid
                , R.id.grains_grid, R.id.noodles_grid, R.id.household_grid
                , R.id.veg_grid, R.id.spices_grid};
        int[] button_ID = {R.id.breakfast_shop_button, R.id.biscuit_shop_button
                , R.id.nuts_shop_button, R.id.oils_shop_button, R.id.grains_shop_button
                , R.id.noodles_shop_button, R.id.household_shop_button
                , R.id.veg_shop_button, R.id.spices_shop_button};

        shopButtons = new Button[resID.length];
        groupDatabaseReferences = new DatabaseReference[resID.length];
        recyclerViews = new RecyclerView[resID.length];
        homeTileAdapters = new HomeTileAdapter[resID.length];
        managers = new LinearLayoutManager[resID.length];
        childEventListeners = new ChildEventListener[resID.length];
        lists = (ArrayList<Product>[]) new ArrayList[(resID.length)];
        for (int i = 0; i < resID.length; i++) {
            final int c = i;
            shopButtons[i] = root.findViewById(button_ID[i]);
            shopButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    bundle.putString("category", categories[c]);
                    Fragment fragment = new CategoryFragment();
                    fragment.setArguments(bundle);
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_frame, fragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            });
            Log.d("Home", "setUpView: " + categories[i]);
            groupDatabaseReferences[i] = databaseReference.child(categories[i]);
            recyclerViews[i] = root.findViewById(resID[i]);
            lists[i] = new ArrayList<>();
            homeTileAdapters[i] = new HomeTileAdapter(getActivity(), lists[i]);
            managers[i] = new LinearLayoutManager(getActivity());
            managers[i].setOrientation(LinearLayoutManager.HORIZONTAL);
            setUpGrid(i);
        }

    }

    private void setUpGrid(int i) {
        recyclerViews[i].setHasFixedSize(true);
        recyclerViews[i].setLayoutManager(managers[i]);
        recyclerViews[i].setAdapter(homeTileAdapters[i]);
        getFromRD(i);
    }

    private void getFromRD(final int i) {
        if (childEventListeners[i] == null) {
            childEventListeners[i] = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Product product = dataSnapshot.getValue(Product.class);
                    lists[i].add(product);
                    homeTileAdapters[i].notifyDataSetChanged();
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
            groupDatabaseReferences[i].addChildEventListener(childEventListeners[i]);
        }

    }

//
//    public void getDrawable(String image) {
////        storageReference.child("Carousel/image_1.jpeg").getDownloadUrl()
////                .addOnSuccessListener(new OnSuccessListener<Uri>() {
////                    @Override
////                  public void onSuccess(Uri uri) {
////                      try {
////                          InputStream inputStream = getContext().getContentResolver().openInputStream(uri);
////                          sampleImages = Arrays.copyOf(sampleImages, sampleImages.length + 1);
////                          sampleImages[sampleImages.length - 1] = Integer.valueOf(uri);
////                      } catch (FileNotFoundException e) {
////                          image_1 = getResources().getDrawable(R.drawable.imageerror);
////                      }
////
////                  }
////              }
////                ).addOnFailureListener(new OnFailureListener() {
////            @Override
////            public void onFailure(@NonNull Exception e) {
////                Log.d(TAG, "onFailure: Carousel Image could not be loaded!");
////            }
////        });
//    }
}
