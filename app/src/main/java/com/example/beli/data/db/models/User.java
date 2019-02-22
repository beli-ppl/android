package com.example.beli.data.db.models;


public class User {
    private Integer id;
    private String email;
    private String name;
    private Integer height;
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
