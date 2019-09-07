package com.robertshay.ratingsdataservice.models;

public class Rating {
    private String moveId;
    private int rating;

    public Rating(String moveId, int rating) {
        this.moveId = moveId;
        this.rating = rating;
    }

    public String getMoveId() {
        return moveId;
    }

    public void setMoveId(String moveId) {
        this.moveId = moveId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;


    }
}
