package com.icwd.rating.RatingsService.service;

import com.icwd.rating.RatingsService.entities.Rating;
import com.icwd.rating.RatingsService.exceptions.ResourceNotFoundException;
import com.icwd.rating.RatingsService.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RatingService {



    @Autowired
    private RatingRepository ratingRepository;

    public Rating create(Rating rating){

        return ratingRepository.save(rating);
    }



    public List<Rating> getAllRatings(){
        return ratingRepository.findAll();
    }



    public Rating getRatingById(String id){
        return ratingRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("rating with given id not found"));
    }

//    public List<Rating> getRatingByUserId(String userId) {
//        return ratingRepository.findByUserId(userId);
//    }
//
//    public List<Rating> getRatingByHotelId(String hotelId) {
//        return ratingRepository.findByHotelId(hotelId);
//    }



    public List<Rating> getRatingByUserId(String userId) {
        List<Rating> ratings = ratingRepository.findByUserId(userId);
        if (ratings.isEmpty()) {
            throw new ResourceNotFoundException("Ratings for user with id " + userId + " not found");
        }
        return ratings;
    }

    public List<Rating> getRatingByHotelId(String hotelId) {
        List<Rating> ratings = ratingRepository.findByHotelId(hotelId);
        if (ratings.isEmpty()) {
            throw new ResourceNotFoundException("Ratings for hotel with id " + hotelId + " not found");
        }
        return ratings;
    }
}
