package com.launchcode.cheese.models;

public enum CheeseRating {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    private final int rating;

    CheeseRating(int rating){
        this.rating=rating;
    }

    public int getRating() {
        return rating;
    }
}
