package com.android.ecom.Fragments;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.ecom.Adapters.CartAdapter;
import com.android.ecom.Emailer.GmailSender;
import com.android.ecom.Models.Product;
import com.android.ecom.R;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment {

    public static ArrayList<Product> cart_list;
    public static float total;
    static CartAdapter cartAdapter;
    static TextView total_text;
    ListView listView;
    Button placeOrder;

    public CartFragment() {
        // Required empty public constructor
    }

    public static void updateList() {
        cartAdapter.notifyDataSetChanged();
    }

    public static void updateCartTotal() {
        total_text.setText(String.format("Total: \u20B9 %s", String.valueOf(total)));
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

    private void setUpView(final View root) {
        listView = root.findViewById(R.id.cart_list);
        cartAdapter = new CartAdapter(
                getContext(), R.layout.product_tile, cart_list);
        listView.setAdapter(cartAdapter);
        total_text = root.findViewById(R.id.total_view);
        updateCartTotal();

        placeOrder = root.findViewById(R.id.place_order);
        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserDetails(view);
            }
        });

    }

    private void getUserDetails(View root) {
        LayoutInflater factory = LayoutInflater.from(root.getContext());
        final View pushDialog = factory.inflate(R.layout.user_details, null);
//        final View pushDialog = getLayoutInflater().inflate(R.layout.user_details,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
        builder.setView(pushDialog);

        final AlertDialog dialog = builder.create();
        Objects.requireNonNull(dialog.getWindow())
                .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        final TextInputEditText name = pushDialog.findViewById(R.id.username);
        final TextInputEditText phone = pushDialog.findViewById(R.id.phone);
        final TextInputEditText address = pushDialog.findViewById(R.id.address);

        final Button sendMail = pushDialog.findViewById(R.id.send_mail);
        sendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name_text = String.valueOf(name.getText());
                String phone_text = String.valueOf(phone.getText());
                String address_text = String.valueOf(address.getText());
                Log.d("values_user", "User Details: " + name_text + phone_text + address_text);
                if (name_text.equals("") || phone_text.equals("") || address_text.equals("")) {
                    Toast.makeText(getActivity(), "Please fill all values!", Toast.LENGTH_LONG).show();
                } else {
                    if (isNetworkAvailable()) {
                        sendMail.setEnabled(false);
                        sendMail.setText("Please Wait...");
                        final StringBuilder message = new StringBuilder("Address:" + address_text + "\n\nOrder:\n");
                        final String from = "faris.subhan.app@gmail.com";
                        final String password = "delivery_app";
                        final String to = "faris.subhan.app@gmail.com";
                        final String subject = "New order from " + name_text;
                        for (Product element : cart_list) {
                            message.append(element.getName()).append(" Quantity:").append(element.getQuantity()).append("\n");
                        }
                        message.append("\nTotal: ").append(total);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    GmailSender sender = new GmailSender(from, password);
                                    sender.sendMail(subject, message.toString(),
                                            from, to);
                                    //Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
                                    Log.d("email", "run: email sent");
                                    dialog.dismiss();
                                    cart_list.clear();
                                    total = 0;
                                    updateCartTotal();
                                    showToast("Order Placed");
                                } catch (Exception e) {
                                    showToast(e.getMessage());
                                }
                            }
                        }).start();
                    } else
                        Toast.makeText(getActivity(), "No Internet Connection!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void showToast(final String toast) {
        Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CartFragment.this.getContext(), toast, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
