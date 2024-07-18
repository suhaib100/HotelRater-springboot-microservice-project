package com.icwd.user.service.UserService.controller;

import com.icwd.user.service.UserService.entities.User;
import com.icwd.user.service.UserService.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
     private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User user1 = userService.saveuser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }



    @GetMapping("/{userId}")
  //  @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
  //  @Retry(name = "ratingHotelService", fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "UserRateLimiter" , fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getSingleUsers(@PathVariable  String userId){

        User  user1 = userService.getUser(userId);
        return ResponseEntity.ok(user1);
    }



    public ResponseEntity<User> ratingHotelFallback(String userId, Exception ex){
        ex.printStackTrace();

    //     logger.info("Fallback is xecuted becoz service is down : ",ex.getMessage());

        User user = User.builder().email("dummy@gmail.com")
                .name("dummy")
                .about("this iser is created dummy becoz some service is down")
                .userId("141234")
                .build();
        return new ResponseEntity<>(user,HttpStatus.OK);

    }


    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
     List<User>    user1 = userService.getAllUsers();
        return ResponseEntity.ok(user1);
    }

}
