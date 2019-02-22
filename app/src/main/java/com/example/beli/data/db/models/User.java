package com.example.beli.data.db.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @Expose
    @SerializedName("id")
    private Integer id;
    @Expose
    @SerializedName("email")
    private String email;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("height")
    private Integer height;
    @Expose
    @SerializedName("width")
    private Integer width;
    private Integer lastTotalCalory;
    private Integer lastTypeNutrition;
    private Integer lastCountStep;
    private Integer lastHighTemperature;

    public User(Integer id, String email, String name, Integer height, Integer width) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.height = height;
        this.width = width;
    }



}
