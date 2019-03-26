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

import com.ISAtim4.WebAppSpringAirport.domain.RentACar;
import com.ISAtim4.WebAppSpringAirport.service.RentACarService;

@RestController
public class RentACarController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RentACarService rentACarService;
	
	@RequestMapping(value = "/api/rentACars", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<RentACar>> getrentACars() {
		logger.info("> getrentACars");

		Collection<RentACar> rentACars = rentACarService.findAll();

		logger.info("< getrentACars");
		return new ResponseEntity<Collection<RentACar>>(rentACars, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/rentACars/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentACar> getrentACar(@PathVariable("id") Long id) {
		logger.info("> gerentACar id:{}", id);
		RentACar rentACar = rentACarService.findOne(id);
		if (rentACar == null) {
			return new ResponseEntity<RentACar>(HttpStatus.NOT_FOUND);
		}
		logger.info("< getGreeting id:{}", id);
		return new ResponseEntity<RentACar>(rentACar, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/rentACars", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentACar> createrentACar(@RequestBody RentACar rentACar) throws Exception {
		logger.info("> createrentACar");
		RentACar savedrentACar = rentACarService.create(rentACar);
		logger.info("< createrentACar");
		return new ResponseEntity<RentACar>(savedrentACar, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/api/rentACars/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentACar> updaterentACar(
			@RequestBody RentACar rentACar) throws Exception {
		logger.info("> updaterentACar id:{}", rentACar.getId());
		RentACar updatedrentACar = rentACarService.update(rentACar);
		if (updatedrentACar == null) {
			System.out.println("Usao sam ovde");
			return new ResponseEntity<RentACar>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.info("< updaterentACar id:{}", rentACar.getId());
		return new ResponseEntity<RentACar>(updatedrentACar, HttpStatus.OK);
	}
}
