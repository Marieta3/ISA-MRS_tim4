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
	private AvioKompanijaServiceImpl aviokompanijaService;
	
	@RequestMapping(value = "/api/avioKompanije", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<AvioKompanija>> getAvioKompanije() {
		logger.info("> getAviokompanije");

		Collection<AvioKompanija> avioKompanije = aviokompanijaService.findAll();

		logger.info("< getAviokompanije");
		return new ResponseEntity<Collection<AvioKompanija>>(avioKompanije, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/avioKompanije/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AvioKompanija> getHotel(@PathVariable("id") Long id) {
		logger.info("> getAviokompanija id:{}", id);
		AvioKompanija avioKompanija = aviokompanijaService.findOne(id);
		if (avioKompanija == null) {
			return new ResponseEntity<AvioKompanija>(HttpStatus.NOT_FOUND);
		}
		logger.info("< getAviokompanija id:{}", id);
		return new ResponseEntity<AvioKompanija>(avioKompanija, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/avioKompanije", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AvioKompanija> createHotel(@RequestBody AvioKompanija hotel) throws Exception {
		logger.info("> createAviokompanija");
		AvioKompanija snimljenaAviokompanija = aviokompanijaService.create(hotel);
		logger.info("< createAviokompanija");
		return new ResponseEntity<AvioKompanija>(snimljenaAviokompanija, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/api/avioKompanije/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AvioKompanija> updateHotel(
			@RequestBody AvioKompanija hotel) throws Exception {
		logger.info("> updateKompanija id:{}", hotel.getId());
		AvioKompanija updatedAviokompanija = aviokompanijaService.update(hotel);
		if (updatedAviokompanija == null) {
			System.out.println("Usao sam ovde");
			return new ResponseEntity<AvioKompanija>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.info("< updateKompanija id:{}", hotel.getId());
		return new ResponseEntity<AvioKompanija>(updatedAviokompanija, HttpStatus.OK);
	}
}
