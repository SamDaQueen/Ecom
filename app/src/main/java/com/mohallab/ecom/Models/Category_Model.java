package com.mohallab.ecom.Models;

public class Category_Model {

    private String id;
    private String name;

    public Category_Model() {
    }

    public Category_Model(String id, String name) {
        this.id = id;
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
