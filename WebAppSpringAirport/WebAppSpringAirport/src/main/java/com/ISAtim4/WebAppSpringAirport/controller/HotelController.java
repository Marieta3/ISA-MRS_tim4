package com.ISAtim4.WebAppSpringAirport.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ISAtim4.WebAppSpringAirport.domain.Hotel;
import com.ISAtim4.WebAppSpringAirport.service.HotelService;



@RestController
public class HotelController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private HotelService hotelService;
	
	@RequestMapping(value = "/api/hotels", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Hotel>> getHotels() {
		logger.info("> getHotels");

		Collection<Hotel> hotels = hotelService.findAll();

		logger.info("< getHotels");
		return new ResponseEntity<Collection<Hotel>>(hotels, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/hotels/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hotel> getHotel(@PathVariable("id") Long id) {
		logger.info("> geHotel id:{}", id);
		Hotel hotel = hotelService.findOne(id);
		if (hotel == null) {
			return new ResponseEntity<Hotel>(HttpStatus.NOT_FOUND);
		}
		logger.info("< getGreeting id:{}", id);
		return new ResponseEntity<Hotel>(hotel, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/hotels", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) throws Exception {
		logger.info("> createHotel");
		Hotel savedHotel = hotelService.create(hotel);
		logger.info("< createHotel");
		return new ResponseEntity<Hotel>(savedHotel, HttpStatus.CREATED);
	}

}
