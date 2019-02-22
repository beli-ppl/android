package com.example.beli.data.db.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Temperature {
    @SerializedName("id")
    public Integer id;

    @SerializedName("user_id")
    public Integer userId;

    @SerializedName("timestamp")
    public Date timestamp;

    @SerializedName("high_temperature")
    public Integer highTemperature;

    public Temperature(Integer id, Integer userId, Date timestamp, Integer highTemperature) {
        this.id = id;
        this.userId = userId;
        this.timestamp = timestamp;
        this.highTemperature = highTemperature;
    }


}
