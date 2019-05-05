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

import com.ISAtim4.WebAppSpringAirport.domain.Filijala;
import com.ISAtim4.WebAppSpringAirport.domain.RentACar;
import com.ISAtim4.WebAppSpringAirport.dto.FilijalaDTO;
import com.ISAtim4.WebAppSpringAirport.service.FilijalaService;
import com.ISAtim4.WebAppSpringAirport.service.RentACarService;

@RestController
public class FilijalaController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	FilijalaService filijalaService;

	@Autowired
	RentACarService rentService;

	/* da snimimo filijalu */
	@PreAuthorize("hasRole('ROLE_RENT')")
	@RequestMapping(value = "/api/filijala", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Filijala createFilijala(@Valid @RequestBody FilijalaDTO filijalaDTO) {
		Filijala filijala = new Filijala();

		filijala.setAdresa(filijalaDTO.getAdresa());
		filijala.setTelefon(filijalaDTO.getTelefon());
		filijala.setOpis(filijalaDTO.getOpis());
		filijala.setSlika(filijalaDTO.getSlika());

		RentACar rent = rentService.findOne(filijalaDTO.getIdRent());
		filijala.setRentACar(rent);
		return filijalaService.save(filijala);
	}

	/* da uzmemo sve filijala, svima dozvoljeno */
	@RequestMapping(value = "/api/filijala", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Filijala> getAllBranches() {
		return filijalaService.findAll();
	}

	/* da uzmemo sve filijala za neki rent-a-car, svima dozvoljeno */
	@RequestMapping(value = "/api/filijala/rent/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Filijala> getAllBranchesByRentACar(
			@PathVariable(value = "id") Long rentId) {
		logger.info("ID je " + rentId);
		RentACar rent = rentService.findOne(rentId);
		return filijalaService.findAllByRentACar(rent);
	}

	/* da uzmemo sobu po id-u, svima dozvoljeno */
	@RequestMapping(value = "/api/filijala/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Filijala> getFilijala(
			@PathVariable(value = "id") Long filijalaId) {
		Filijala filijala = filijalaService.findOne(filijalaId);

		if (filijala == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(filijala);
	}

	/* update filijala po id-u */
	@PreAuthorize("hasRole('ROLE_RENT')")
	@RequestMapping(value = "/api/filijala/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Filijala> updateBranch(
			@PathVariable(value = "id") Long filijalaId,
			@Valid @RequestBody Filijala filijalaDetalji) {

		Filijala filijala = filijalaService.findOne(filijalaId);
		if (filijala == null) {
			return ResponseEntity.notFound().build();
		}

		filijala.setOpis(filijalaDetalji.getOpis());
		filijala.setAdresa(filijalaDetalji.getAdresa());
		filijala.setTelefon(filijalaDetalji.getTelefon());
		filijala.setSlika(filijalaDetalji.getSlika());

		Filijala updateFilijala = filijalaService.save(filijala);
		return ResponseEntity.ok().body(updateFilijala);
	}

	/* brisanje filijala */
	@PreAuthorize("hasRole('ROLE_RENT')")
	@RequestMapping(value = "/api/filijala/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Filijala> deleteFilijala(
			@PathVariable(value = "id") Long filijalaId) {
		Filijala filijala = filijalaService.findOne(filijalaId);

		if (filijala != null) {
			filijalaService.remove(filijalaId);
			logger.info("Branch with id:" + filijalaId + "deleted.");
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.error("Branch with id:" + filijalaId + "not found.");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
