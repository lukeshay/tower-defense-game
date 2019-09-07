package com.robertshay.moviecatalogservice.resources;

import com.robertshay.moviecatalogservice.models.CatalogItem;
import com.robertshay.moviecatalogservice.models.Movie;
import com.robertshay.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    /*@Autowired
    private WebClient.Builder webClientBuilder;*/

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId) {
        // get all rated movie IDs (will be from rating api)
        UserRating userRatings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId, UserRating.class);

        // put them all together
        assert userRatings != null;

        return userRatings.getRatings().stream().map(rating -> {
            //// for each move ID, call info service
            Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMoveId(), Movie.class);
            /* Movie movie = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8082/movies/" + rating.getMoveId())
                    .retrieve() // Sends the call and retrieves the body
                    .bodyToMono(Movie.class) // Turns the body into the given class
                    .block(); // Blocks command until this is done. Essentially makes it synchronous */

            return new CatalogItem(movie.getName(), "Test desc", rating.getRating());
        }).collect(Collectors.toList());
    }
}
