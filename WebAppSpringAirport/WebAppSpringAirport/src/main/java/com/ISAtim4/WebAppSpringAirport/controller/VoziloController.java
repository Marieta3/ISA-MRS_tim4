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

import com.ISAtim4.WebAppSpringAirport.domain.Vozilo;
import com.ISAtim4.WebAppSpringAirport.service.VoziloService;


@RestController
public class VoziloController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private VoziloService voziloService;

	/* da snimimo vozilo */
	@PreAuthorize("hasRole('ROLE_RENT')")
	@RequestMapping(value = "/api/cars", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Vozilo createCar(@Valid @RequestBody Vozilo vozilo) {
		return voziloService.save(vozilo);
	}

	/* da uzmemo sve vozila, svima dozvoljeno */
	@RequestMapping(value = "/api/cars", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Vozilo> getAllCars() {
		return voziloService.findAll();
	}

	/* da uzmemo vozilo po id-u, svima dozvoljeno  */
	@RequestMapping(value = "/api/cars/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Vozilo> getCar(
			@PathVariable(value = "id") Long hotelId) {
		Vozilo vozilo = voziloService.findOne(hotelId);

		if (vozilo == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(vozilo);
	}

	/* update vozila po id-u */
	@PreAuthorize("hasRole('ROLE_RENT')")
	@RequestMapping(value = "/api/cars/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Vozilo> updateCar(
			@PathVariable(value = "id") Long carId,
			@Valid @RequestBody Vozilo voziloDetalji) {

		Vozilo vozilo = voziloService.findOne(carId);
		if (vozilo == null) {
			return ResponseEntity.notFound().build();
		}

		vozilo.setProizvodjac(voziloDetalji.getProizvodjac());
		vozilo.setModel(voziloDetalji.getModel());
		vozilo.setGodina(voziloDetalji.getGodina());
		vozilo.setTablica(voziloDetalji.getTablica());
		vozilo.setCena(voziloDetalji.getCena());
		vozilo.setBrojMesta(voziloDetalji.getBrojMesta());

		Vozilo updateVozilo = voziloService.save(vozilo);
		return ResponseEntity.ok().body(updateVozilo);
	}

	/* brisanje vozila */
	@PreAuthorize("hasRole('ROLE_RENT')")
	@RequestMapping(value = "/api/cars/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Vozilo> deleteCar(
			@PathVariable(value = "id") Long carId) {
		Vozilo vozilo = voziloService.findOne(carId);

		if (vozilo != null) {
			voziloService.remove(carId);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}