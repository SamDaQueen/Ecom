package com.android.ecom.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.ecom.Adapters.HomeTileAdapter;
import com.android.ecom.Models.Product;
import com.android.ecom.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.android.ecom.Fragments.CartFragment.cart_list;

public class HomeFragment extends Fragment {
    FirebaseDatabase firebaseDatabase;
    //    DatabaseReference databaseReference;
    ChildEventListener childEventListener;
    //    ArrayList<Product> arrayList;
    HomeTileAdapter homeTileAdapter;
    RecyclerView recyclerView;
    CarouselView carouselView;
    List<Product> list = new ArrayList<>();
    int NUMBER_OF_PAGES = 5;
    //String[] images = {"image_1", "image_2", "image_3", "image_4", "image_5"};
    int[] sampleImages = {R.drawable.image_1, R.drawable.image_2, R.drawable.image_3, R.drawable.image_4, R.drawable.image_5};
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            imageView.setImageResource(sampleImages[position]);
        }
    };

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
        cart_list = new ArrayList<>();
        Objects.requireNonNull(getActivity()).setTitle("Home");
    }

    private void setUpView(View root) {
        carouselView = root.findViewById(R.id.carouselView);
        carouselView.setPageCount(NUMBER_OF_PAGES);
        carouselView.setImageListener(imageListener);
        firebaseDatabase = FirebaseDatabase.getInstance();

        int gridIDBreakfast = R.id.breakfast_grid;

        setUpGrid(root, gridIDBreakfast, "Edible Oils, Ghee, Vanaspati");
    }

    private void setUpGrid(View root, int resID, String category) {

        DatabaseReference databaseReference = firebaseDatabase.getReference().child(category);
        recyclerView = root.findViewById(resID);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        homeTileAdapter = new HomeTileAdapter(getActivity(), list);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(homeTileAdapter);
        getFromRD(databaseReference);


    }

    private void getFromRD(DatabaseReference databaseReference) {
        if (childEventListener == null) {
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Product product = dataSnapshot.getValue(Product.class);
                    Log.d("success", "onChildAdded: "
                            + product.getId() + product.getName() + product.getPhoto());
                    list.add(product);
                    homeTileAdapter.notifyDataSetChanged();
                    Log.d("size", "onChildAdded: " + homeTileAdapter.getItemCount());
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
            Log.d("new size", "getFromRD: " + homeTileAdapter.getItemCount());
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
