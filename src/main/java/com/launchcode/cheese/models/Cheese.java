package com.launchcode.cheese.models;



import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Cheese {
    private static int nextID=0;
    private int cheeseID;

    @NotNull
    @Size(min=3, max=15)
    private String name;

    @NotNull
    @Size(min=1, message = "Description must not be empty")
    private String description;


    private CheeseType type;

    private CheeseRating rating;

    public Cheese(){
        cheeseID = ++nextID;
    }

    public Cheese(String name, String description, CheeseType type, CheeseRating rating) {
        this();
        this.name = name;
        this.description = description;
        this.type = type;
        this.rating=rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCheeseID() {
        return cheeseID;
    }

    public CheeseType getType() {
        return type;
    }

    public void setType(CheeseType type) {
        this.type = type;
    }

    public CheeseRating getRating() {
        return rating;
    }

    public void setRating(CheeseRating rating) {
        this.rating = rating;
    }
}
