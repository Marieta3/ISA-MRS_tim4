package com.ISAtim4.WebAppSpringAirport.controller;

import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RestController;

import com.ISAtim4.WebAppSpringAirport.domain.Destinacija;
import com.ISAtim4.WebAppSpringAirport.service.DestinacijaService;

@RestController
public class DestinacijaController {

	@Autowired
	private DestinacijaService destinacijaService;
	
	/* da snimimo destinaciju */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_AVIO')")
	@PostMapping(value = "/api/destinacije", produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public Destinacija createDestinacija(@Valid @RequestBody Destinacija destinacija) {
		return destinacijaService.save(destinacija);
	}

	/* da uzmemo sve destinacije, svima dozvoljeno */
	@GetMapping(value = "/api/destinacije",  produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Destinacija> getAllDestinacije() {
		return destinacijaService.findAll();
	}

	/* da uzmemo destinaciju po id-u, svima dozvoljeno*/
	@GetMapping(value = "/api/destinacije/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Destinacija> getDestinacija(
			@PathVariable(value = "id") Long idDestinacije) {
		Destinacija destinacija = destinacijaService.findOne(idDestinacije);

		if (destinacija == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(destinacija);
	}
	
	/* da uzmemo destinaciju po nazivu, svima dozvoljeno */
	/*
	@RequestMapping(value = "/api/destinacije/search/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Destinacija>> getDestinacijaByName(
			@PathVariable(value = "name") String avioKompanijaName) {
		List<Destinacija> destinacija = destinacijaService.containsName(avioKompanijaName);

		if (destinacija == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(destinacija);
	}*/

	/* update destinacije po id-u */
	@PreAuthorize("hasAnyRole('ROLE_AVIO', 'ROLE_ADMIN')")
	@PutMapping(value = "/api/destinacije/{id}",  produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Destinacija> updateDestinacije(
			@PathVariable(value = "id") Long destinacijaId,
			@Valid @RequestBody Destinacija destinacijaDetalji) {

		Destinacija destinacija = destinacijaService.findOne(destinacijaId);
		if (destinacija == null) {
			return ResponseEntity.notFound().build();
		}

		destinacija.setAdresa(destinacijaDetalji.getAdresa());
		destinacija.setSlika(destinacijaDetalji.getSlika());
		Destinacija updateDestinacije = destinacijaService.save(destinacija);
		return ResponseEntity.ok().body(updateDestinacije);
	}

	/* brisanje destinacije */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_AVIO')")
	@DeleteMapping(value = "/api/destinacije/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Destinacija> deleteDestinacije(
			@PathVariable(value = "id") Long destinacijaId) {
		Destinacija destinacija = destinacijaService.findOne(destinacijaId);

		if (destinacija != null) {
			destinacijaService.remove(destinacijaId);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}
