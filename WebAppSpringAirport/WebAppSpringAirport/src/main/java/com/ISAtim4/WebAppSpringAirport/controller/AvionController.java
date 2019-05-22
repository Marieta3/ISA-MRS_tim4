package com.ISAtim4.WebAppSpringAirport.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.ISAtim4.WebAppSpringAirport.domain.Avion;
import com.ISAtim4.WebAppSpringAirport.domain.Destinacija;
import com.ISAtim4.WebAppSpringAirport.service.AvionService;

@RestController
public class AvionController {

	@Autowired
	private AvionService avionService;
	
	/* da snimimo avion */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_AVIO')")
	@RequestMapping(value = "/api/avion", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public Avion createAvion(@Valid @RequestBody Avion avion) {
		
		return avionService.save(avion);
	}

	/* da uzmemo sve avione, svima dozvoljeno */
	@RequestMapping(value = "/api/avion", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Avion> getAllAvioni() {
		return avionService.findAll();
	}

	/* da uzmemo avion po id-u, svima dozvoljeno*/
	@RequestMapping(value = "/api/avion/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Avion> getAvion(
			@PathVariable(value = "id") Long idAviona) {
		Avion avion = avionService.findOne(idAviona);

		if (avion == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(avion);
	}
	
	/* da uzmemo avion po nazivu, svima dozvoljeno */
	/*
	@RequestMapping(value = "/api/avion/search/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Avion>> getAvionByName(
			@PathVariable(value = "name") String avionName) {
		List<Avion> avion = avionService.containsName(avionName);

		if (avion == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(avion);
	}*/

	/* update aviona po id-u */
	@PreAuthorize("hasAnyRole('ROLE_AVIO', 'ROLE_ADMIN')")
	@RequestMapping(value = "/api/avion/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Avion> updateAviona(
			@PathVariable(value = "id") Long avionId,
			@Valid @RequestBody Avion avionDetalji) {

		Avion avion = avionService.findOne(avionId);
		if (avion == null) {
			return ResponseEntity.notFound().build();
		}

		//avion.setAdresa(avionDetalji.getAdresa());
		//avion.setSlika(avionDetalji.getSlika());
		avion.setModel(avionDetalji.getModel());
		avion.setBrojRedova(avionDetalji.getBrojRedova());
		avion.setBrojKolona(avionDetalji.getBrojKolona());
		avion.setBrojRedovaEC(avionDetalji.getBrojRedovaEC());
		avion.setBrojRedovaBC(avionDetalji.getBrojRedovaBC());
		avion.setBrojRedovaFC(avionDetalji.getBrojRedovaFC());
		Avion updateAvion = avionService.save(avion);
		return ResponseEntity.ok().body(updateAvion);
	}

	/* brisanje aviona */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_AVIO')")
	@RequestMapping(value = "/api/avion/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Destinacija> deleteAviona(
			@PathVariable(value = "id") Long avionId) {
		Avion avion = avionService.findOne(avionId);

		if (avion != null) {
			avionService.remove(avionId);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
