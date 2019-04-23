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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ISAtim4.WebAppSpringAirport.domain.Korisnik;
import com.ISAtim4.WebAppSpringAirport.domain.RentACar;
import com.ISAtim4.WebAppSpringAirport.service.RentACarService;

@RestController
public class RentACarController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RentACarService rentACarService;
	
	/* da snimimo RentAcar */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/api/rentACars", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public RentACar createRentAcar(@Valid @RequestBody RentACar rentACar) {
		return rentACarService.save(rentACar);
	}

	/* da uzmemo sve RentAcar, svima dozvoljeno */
	
	@RequestMapping(value = "/api/rentACars", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<RentACar> getAllRentAcar() {
		return rentACarService.findAll();
	}

	/* da uzmemo RentAcar po id-u, svima dozvoljeno */
	@RequestMapping(value = "/api/rentACars/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentACar> getRentAcar(
			@PathVariable(value = "id") Long rentAcarId) {
		RentACar rentACar = rentACarService.findOne(rentAcarId);

		if (rentACar == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(rentACar);
	}
	
	/* da uzmemo RentAcar po nazivu, svima dozvoljeno */
	
	@RequestMapping(value = "/api/rentACars/search/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RentACar>> getRentAcarByName(
			@PathVariable(value = "name") String rentACarName) {
		List<RentACar> rentACars = rentACarService.containsName(rentACarName);

		if (rentACars == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(rentACars);
	}

	/* update RentAcar po id-u */
	@PreAuthorize("hasRole('ROLE_RENT')")
	@RequestMapping(value = "/api/rentACars/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentACar> updateRentAcar(
			@PathVariable(value = "id") Long rentAcarId,
			@Valid @RequestBody RentACar rentAcarDetalji) {

		RentACar rentACar = rentACarService.findOne(rentAcarId);
		if (rentACar == null) {
			return ResponseEntity.notFound().build();
		}
		rentACar.setNaziv(rentAcarDetalji.getNaziv());
		rentACar.setAdresa(rentAcarDetalji.getAdresa());
		rentACar.setOpis(rentAcarDetalji.getOpis());

		RentACar updateRentACar = rentACarService.save(rentACar);
		return ResponseEntity.ok().body(updateRentACar);
	}

	/* brisanje RentAcar */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/api/rentACars/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentACar> deleteRentAcar(
			@PathVariable(value = "id") Long rentAcarId) {
		RentACar rentACar = rentACarService.findOne(rentAcarId);
		if (rentACar != null) {
			rentACarService.remove(rentAcarId);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
