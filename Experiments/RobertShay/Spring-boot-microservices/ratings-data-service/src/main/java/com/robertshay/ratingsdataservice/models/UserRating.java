package com.robertshay.ratingsdataservice.models;

import java.util.Arrays;
import java.util.List;

public class UserRating {
    private String userId;
    private List<Rating> ratings;


    public UserRating() {
    }

    public UserRating(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void initRatings(String userId) {
        setUserId(userId);
        setRatings(Arrays.asList(
                new Rating("100", 4),
                new Rating("101", 3)));
    }
}
