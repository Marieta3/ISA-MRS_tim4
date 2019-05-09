package com.ISAtim4.WebAppSpringAirport.controller;

import java.util.ArrayList;
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

import com.ISAtim4.WebAppSpringAirport.domain.Hotel;
import com.ISAtim4.WebAppSpringAirport.domain.Soba;
import com.ISAtim4.WebAppSpringAirport.domain.Usluga;
import com.ISAtim4.WebAppSpringAirport.dto.SobaDTO;
import com.ISAtim4.WebAppSpringAirport.service.HotelService;
import com.ISAtim4.WebAppSpringAirport.service.SobaService;
import com.ISAtim4.WebAppSpringAirport.service.UslugaService;

@RestController
public class SobaController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	SobaService sobaService;
	
	@Autowired
	HotelService hotelService;
	
	@Autowired
	UslugaService uslugaService;
	
	/* da snimimo sobu */
	@PreAuthorize("hasRole('ROLE_HOTEL')")
	@RequestMapping(value = "/api/sobe", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public Soba createSoba(@Valid @RequestBody SobaDTO sobaDTO) {
		Soba soba=new Soba();
		soba.setBrojKreveta(sobaDTO.getBrojKreveta());
		soba.setOpis(sobaDTO.getOpis());
		soba.setSlika(sobaDTO.getSlika());
		
		Hotel hotel=hotelService.findOne(sobaDTO.getIdHotela());
		soba.setHotel(hotel);
		
		//ne trb pristupati bazi kroz for petlju!!!
		ArrayList<Usluga> usluge=(ArrayList<Usluga>) uslugaService.findAllSelected(sobaDTO.getUsluge());
		soba.setUsluge(usluge);
		return sobaService.save(soba);
	}

	/* da uzmemo sve sobe, svima dozvoljeno */
	@RequestMapping(value = "/api/sobe", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Soba> getAllSobe() {
		return sobaService.findAll();
	}
	@RequestMapping(value = "/api/sobeHotela/{hotel_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Soba> getSobeByHotel(@PathVariable(value = "hotel_id") Long hotel_id) {
		
		Hotel hotel=hotelService.findOne(hotel_id);
		return sobaService.findAllByHotel(hotel);
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
	@PreAuthorize("hasRole('ROLE_HOTEL')")
	@RequestMapping(value = "/api/sobe/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Soba> updateSobe(
			@PathVariable(value = "id") Long sobaId,
			@Valid @RequestBody SobaDTO sobaDTO) {

		Soba soba = sobaService.findOne(sobaId);
		if (soba == null) {
			return ResponseEntity.notFound().build();
		}

		soba.setOpis(sobaDTO.getOpis());
		soba.setSlika(sobaDTO.getSlika());
		soba.setBrojKreveta(sobaDTO.getBrojKreveta());
		
		ArrayList<Usluga> usluge=(ArrayList<Usluga>) uslugaService.findAllSelected(sobaDTO.getUsluge());
		soba.setUsluge(usluge);		
		Soba updateSoba = sobaService.save(soba);
		return ResponseEntity.ok().body(updateSoba);
	}

	/* brisanje sobe */
	@PreAuthorize("hasRole('ROLE_HOTEL')")
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
