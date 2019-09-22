package com.android.ecom.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

    private int id;
    private String name;
    private String size;
    private float MRP;
    private float price;
    private int quantity;
    private String photo;

    public Product(int id, String name, String size, float MRP, float price, String photo) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.MRP = MRP;
        this.price = price;
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public Product() {
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public float getMRP() {
        return MRP;
    }

    public void setMRP(float MRP) {
        this.MRP = MRP;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
