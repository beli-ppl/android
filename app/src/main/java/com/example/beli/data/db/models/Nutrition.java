package com.example.beli.data.db.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Nutrition {
    @SerializedName("id")
    public Integer id;

    @SerializedName("user_id")
    public Integer userId;

    @SerializedName("timestamp")
    public Date timestamp;

    @SerializedName("type")
    public Integer type;

    @SerializedName("total_calory")
    public Integer totalCalory;

    public Nutrition(Integer id, Integer userId, Date timestamp, Integer type, Integer totalCalory) {
        this.id = id;
        this.userId = userId;
        this.timestamp = timestamp;
        this.type = type;
    }
}
