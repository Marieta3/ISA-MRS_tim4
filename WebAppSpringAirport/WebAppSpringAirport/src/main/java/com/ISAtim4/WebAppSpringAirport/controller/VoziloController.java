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

import com.ISAtim4.WebAppSpringAirport.domain.Vozilo;
import com.ISAtim4.WebAppSpringAirport.service.VoziloService;

@RestController
public class VoziloController {
private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private VoziloService voziloService;
	
	@RequestMapping(value = "/api/cars", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Vozilo>> getVozila() {
		logger.info("> getVozila");

		Collection<Vozilo> vozila = voziloService.findAll();

		logger.info("< getVozila");
		return new ResponseEntity<Collection<Vozilo>>(vozila, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/cars/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Vozilo> getVozilo(@PathVariable("id") Long id) {
		logger.info("> getVozilo id:{}", id);
		Vozilo Vozilo = voziloService.findOne(id);
		if (Vozilo == null) {
			return new ResponseEntity<Vozilo>(HttpStatus.NOT_FOUND);
		}
		logger.info("< getVozilo id:{}", id);
		return new ResponseEntity<Vozilo>(Vozilo, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/api/cars", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Vozilo> createVozilo(@RequestBody Vozilo vozilo) throws Exception {
		logger.info("> createVozilo");
		Vozilo snimljenVozilo = voziloService.create(vozilo);
		logger.info("< createVozilo");
		return new ResponseEntity<Vozilo>(snimljenVozilo, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/api/cars/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Vozilo> updateVozilo(
			@RequestBody Vozilo vozilo) throws Exception {
		logger.info("> updateVozilo id:{}", vozilo.getId());
		Vozilo updatedVozilo = voziloService.update(vozilo);
		if (updatedVozilo == null) {
			System.out.println("Usao sam ovde");
			return new ResponseEntity<Vozilo>(
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		logger.info("< updateVozilo id:{}", vozilo.getId());
		return new ResponseEntity<Vozilo>(updatedVozilo, HttpStatus.OK);
	}
}