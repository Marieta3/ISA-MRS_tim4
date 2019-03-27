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

import com.ISAtim4.WebAppSpringAirport.domain.AvioKompanija;
import com.ISAtim4.WebAppSpringAirport.service.AvioKompanijaServiceImpl;

@RestController
public class AvioKompanijaController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AvioKompanijaServiceImpl hotelService;
	
	@RequestMapping(value = "/api/avioKompanije", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<AvioKompanija>> getAvioKompanije() {
		logger.info("> getHotels");

		Collection<AvioKompanija> avioKompanije = hotelService.findAll();

		logger.info("< getHotels");
		return new ResponseEntity<Collection<AvioKompanija>>(avioKompanije, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/avioKompanije/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AvioKompanija> getHotel(@PathVariable("id") Long id) {
		logger.info("> geHotel id:{}", id);
		AvioKompanija hotel = hotelService.findOne(id);
		if (hotel == null) {
			return new ResponseEntity<AvioKompanija>(HttpStatus.NOT_FOUND);
		}
		logger.info("< getGreeting id:{}", id);
		return new ResponseEntity<AvioKompanija>(hotel, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/avioKompanije", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AvioKompanija> createHotel(@RequestBody AvioKompanija hotel) throws Exception {
		logger.info("> createHotel");
		AvioKompanija savedHotel = hotelService.create(hotel);
		logger.info("< createHotel");
		return new ResponseEntity<AvioKompanija>(savedHotel, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/api/avioKompanije/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AvioKompanija> updateHotel(
			@RequestBody AvioKompanija hotel) throws Exception {
		logger.info("> updateHotel id:{}", hotel.getId());
		AvioKompanija updatedHotel = hotelService.update(hotel);
		if (updatedHotel == null) {
			System.out.println("Usao sam ovde");
			return new ResponseEntity<AvioKompanija>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.info("< updateHotel id:{}", hotel.getId());
		return new ResponseEntity<AvioKompanija>(updatedHotel, HttpStatus.OK);
	}
}
