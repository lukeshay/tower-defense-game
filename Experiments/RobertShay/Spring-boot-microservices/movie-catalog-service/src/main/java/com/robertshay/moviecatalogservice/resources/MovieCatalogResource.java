package com.robertshay.moviecatalogservice.resources;

import com.robertshay.moviecatalogservice.models.CatalogItem;
import com.robertshay.moviecatalogservice.services.MovieInfoService;
import com.robertshay.moviecatalogservice.services.UserRatingInfoService;
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

    @Autowired
    MovieInfoService movieInfoService;

    @Autowired
    UserRatingInfoService userRatingInfoService;

    /*@Autowired
    private WebClient.Builder webClientBuilder;*/

    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable String userId) {
        return userRatingInfoService.getUserRating(userId).getRatings().stream().map(rating -> movieInfoService.getCatalogItem(rating)).collect(Collectors.toList());
    }
}

/* Movie movie = webClientBuilder.build()
        .get()
        .uri("http://localhost:8082/movies/" + rating.getMoveId())
        .retrieve() // Sends the call and retrieves the body
        .bodyToMono(Movie.class) // Turns the body into the given class
        .block(); // Blocks command until this is done. Essentially makes it synchronous */
