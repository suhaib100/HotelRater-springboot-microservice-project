package com.icwd.user.service.UserService;

import com.icwd.user.service.UserService.entities.Ratings;
import com.icwd.user.service.UserService.external.services.RatingService;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceApplicationTests {

	@Test
	void contextLoads() {
	}


	@Autowired
	private RatingService ratingService;

//
//	@Test
//	void createRating(){
//		Ratings ratings = Ratings.builder().rating(10).userId(" ").hotelId("").feedback("this is created using feign client").build();
//		Ratings savedRating = ratingService.createRatings(ratings);
//
//		System.out.println("new rating created ");
//	}


}
