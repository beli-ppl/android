package com.example.beli.data.db.models;


import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    public Integer id;

    @SerializedName("email")
    public String email;

    @SerializedName("name")
    public String name;

    @SerializedName("height")
    public Integer height;

    @SerializedName("width")
    public Integer width;

    public User(Integer id, String email, String name, Integer height, Integer width) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.height = height;
        this.width = width;
    }

    public User(String email, String name, Integer height, Integer width) {
        this.email = email;
        this.name = name;
        this.height = height;
        this.width = width;
    }
}
