package com.robertshay.ratingsdataservice.resources;

import com.robertshay.ratingsdataservice.models.Rating;
import com.robertshay.ratingsdataservice.models.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsDataResource {

    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable String movieId) {
        return new Rating(movieId, 4);
    }

    @RequestMapping("/users/{userId}")
    public UserRating getUserRatings(@PathVariable String userId) {
        UserRating userRating = new UserRating();

        userRating.initRatings(userId);
        return userRating;
    }
}
