package com.icwd.user.service.UserService.external.services;

import com.icwd.user.service.UserService.entities.Hotel;
import com.icwd.user.service.UserService.entities.Ratings;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "RATINGS-SERVICE")
@Service
public interface RatingService {


    @GetMapping("/ratings/users/{userId}")
    List<Ratings> getRatingsByUserId(@PathVariable("userId") String userId);


    @PostMapping("/ratings")
    public Ratings createRatings(@RequestBody Ratings ratings);



    @PutMapping("/ratings/{ratingId}")
    public Ratings updateRatings(@PathVariable("ratingId") String ratingId, Ratings ratings);



    @DeleteMapping("/ratings/{ratingId}")
    public Ratings deleteRatings(@PathVariable("ratingId") String ratingId);

}
