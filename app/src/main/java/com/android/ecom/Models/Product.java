package com.android.ecom.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable {

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
    private String name;
    private String size;
    private float MRP;
    private float price;
    private int quantity;
    private String photo;
    private String id;

    public Product(String id, String name, String size, float MRP, float price, String photo) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.MRP = MRP;
        this.price = price;
        this.photo = photo;
    }

    private Product(Parcel in) {
        id = in.readString();
        name = in.readString();
        size = in.readString();
        MRP = in.readFloat();
        price = in.readFloat();
        quantity = in.readInt();
        photo = in.readString();
    }

    public String getPhoto() {
        return photo;
    }

    public Product() {
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(size);
        parcel.writeFloat(MRP);
        parcel.writeFloat(price);
        parcel.writeInt(quantity);
        parcel.writeString(photo);
    }
}
