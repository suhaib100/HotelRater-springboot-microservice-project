package com.icwd.hotel.HotelService.service;

import com.icwd.hotel.HotelService.entities.Hotel;
import com.icwd.hotel.HotelService.exceptions.ResourceNotFoundException;
import com.icwd.hotel.HotelService.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelService {



    @Autowired
    private HotelRepository hotelRepository;

    public Hotel createHotel(Hotel hotel){

        String hotelId = UUID.randomUUID().toString();
        hotel.setId(hotelId);
        return hotelRepository.save(hotel);
    }



    public List<Hotel> getAllHotels(){
        return hotelRepository.findAll();
    }



    public Hotel getHotelById(String id){
        return hotelRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("hotel with given id not found"));
    }


}
