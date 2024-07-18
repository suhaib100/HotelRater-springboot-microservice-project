package com.icwd.user.service.UserService.service;

import com.icwd.user.service.UserService.entities.Hotel;
import com.icwd.user.service.UserService.entities.Ratings;
import com.icwd.user.service.UserService.entities.User;
import com.icwd.user.service.UserService.exceptions.ResourceNotFoundException;
import com.icwd.user.service.UserService.external.services.HotelService;
import com.icwd.user.service.UserService.external.services.RatingService;
import com.icwd.user.service.UserService.repository.UserRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HotelService hotelService;


    @Autowired
    private RatingService ratingService;

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    public User saveuser(User user){

        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
      return userRepository.save(user);
  }



    public List<User> getAllUsers(){
        return userRepository.findAll();
    }


// using rest template
//
//    public User getUser(String userId){
//        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given ID not found"));
//
//        // Fetch ratings of the user from ratings service
//        try {
//            Ratings[] ratingsOfUser = restTemplate.getForObject("http://RATINGS-SERVICE/ratings/users/" + user.getUserId(), Ratings[].class);
//            logger.info("Ratings of user {}: {}", userId, ratingsOfUser);
//
//            List<Ratings> ratings = Arrays.stream(ratingsOfUser).toList();
//
//
//            List<Ratings> ratingList = ratings.stream().map(rating -> {
//                //api call to hotel service to get the hotel
//                //http://localhost:8082/hotels/a032629a-6b2e-4b40-a346-7e93ee83d3de
//
//                ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
//                Hotel hotel = forEntity.getBody();
//                logger.info("response status code: {}  ",forEntity.getStatusCode());
//                rating.setHotel(hotel);
//
//                // set the hotel to rating
//                //return the rating
//                return rating;
//            }).collect(Collectors.toList());
//
//            user.setRatings(ratingList);
//        } catch (HttpClientErrorException e) {
//            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
//                logger.warn("Ratings for user with id {} not found", userId);
//                user.setRatings(Collections.emptyList()); // Set empty list if ratings not found
//            } else {
//                throw e; // Re-throw other exceptions
//            }
//        }
//
//        return user;
//    }




// using feign client

    public User getUser(String userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given ID not found"));

        // Fetch ratings of the user from ratings service
        try {
            List<Ratings> ratingsOfUser = ratingService.getRatingsByUserId(userId);
            logger.info("Ratings of user {}: {}", userId, ratingsOfUser);

            List<Ratings> ratingList = ratingsOfUser.stream().map(rating -> {
                // API call to hotel service to get the hotel
                Hotel hotel = hotelService.getHotel(rating.getHotelId());
                rating.setHotel(hotel);

                // Set the hotel to rating
                return rating;
            }).collect(Collectors.toList());

            user.setRatings(ratingList);
        } catch (FeignException e) {
            if (e.status() == 404) {
                logger.warn("Ratings for user with id {} not found", userId);
                user.setRatings(Collections.emptyList()); // Set empty list if ratings not found
            } else {
                throw e; // Re-throw other exceptions
            }
        }

        return user;
    }

}
