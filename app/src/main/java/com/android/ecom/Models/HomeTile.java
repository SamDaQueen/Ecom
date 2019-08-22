package com.android.ecom.Models;

public class HomeTile {

    private int id;
    private String name;
    private String photo;

    public HomeTile()   {}

    public HomeTile(int id, String name, String photo) {
        this.id = id;
        this.name = name;
        this.photo = photo;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
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
}
