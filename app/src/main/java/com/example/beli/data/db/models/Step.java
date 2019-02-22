package com.example.beli.data.db.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Step {
    @SerializedName("id")
    public Integer id;

    @SerializedName("user_id")
    public Integer userId;

    @SerializedName("timestamp")
    public Date timestamp;

    @SerializedName("count_step")
    public Integer countStep;

    public Step(Integer id, Integer userId, Date timestamp, Integer countStep) {
        this.id = id;
        this.userId = userId;
        this.timestamp = timestamp;
        this.countStep = countStep;
    }
}
