package com.android.ecom.Models;

public class Product {

    private int id;
    private String name;
    private String size;
    private float MRP;
    private float price;

    public Product() {
    }

    public Product(int id, String name, String size, float MRP, float price) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.MRP = MRP;
        this.price = price;
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
}
