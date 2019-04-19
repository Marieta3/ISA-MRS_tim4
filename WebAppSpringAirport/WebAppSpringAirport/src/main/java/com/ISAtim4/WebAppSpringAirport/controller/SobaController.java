package com.ISAtim4.WebAppSpringAirport.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ISAtim4.WebAppSpringAirport.domain.Soba;
import com.ISAtim4.WebAppSpringAirport.service.SobaService;

@RestController
public class SobaController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	SobaService sobaService;

	/* da snimimo sobu */
	@PreAuthorize("hasRole('HOTEL_ADMIN')")
	@RequestMapping(value = "/api/sobe", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public Soba createSoba(@Valid @RequestBody Soba soba) {
		
		return sobaService.save(soba);
	}

	/* da uzmemo sve sobe, svima dozvoljeno */
	@RequestMapping(value = "/api/sobe", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Soba> getAllSobe() {
		return sobaService.findAll();
	}

	/* da uzmemo sobu po id-u, svima dozvoljeno */
	@RequestMapping(value = "/api/sobe/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Soba> getSoba(
			@PathVariable(value = "id") Long sobaId) {
		Soba soba = sobaService.findOne(sobaId);

		if (soba == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(soba);
	}

	/* update sobe po id-u */
	@PreAuthorize("hasRole('HOTEL_ADMIN')")
	@RequestMapping(value = "/api/sobe/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Soba> updateSobe(
			@PathVariable(value = "id") Long sobaId,
			@Valid @RequestBody Soba sobaDetalji) {

		Soba soba = sobaService.findOne(sobaId);
		if (soba == null) {
			return ResponseEntity.notFound().build();
		}

		soba.setOpis(sobaDetalji.getOpis());
		soba.setHotel(sobaDetalji.getHotel());
		soba.setBrojKreveta(sobaDetalji.getBrojKreveta());
		
		Soba updateSoba = sobaService.save(soba);
		return ResponseEntity.ok().body(updateSoba);
	}

	/* brisanje sobe */
	@PreAuthorize("hasRole('HOTEL_ADMIN')")
	@RequestMapping(value = "/api/sobe/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Soba> deleteSoba(
			@PathVariable(value = "id") Long sobaId) {
		Soba soba = sobaService.findOne(sobaId);

		if (soba != null) {
			sobaService.remove(sobaId);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
