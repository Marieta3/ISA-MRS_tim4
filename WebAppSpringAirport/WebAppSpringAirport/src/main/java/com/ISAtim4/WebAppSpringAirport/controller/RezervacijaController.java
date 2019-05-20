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

import com.ISAtim4.WebAppSpringAirport.domain.Rezervacija;
import com.ISAtim4.WebAppSpringAirport.dto.HotelDTO;
import com.ISAtim4.WebAppSpringAirport.service.RezervacijaService;

@RestController
public class RezervacijaController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	RezervacijaService rezervacijaService;

	/* da dodamo rezervaciju */
	@RequestMapping(value = "/api/reserve", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	public Rezervacija createReservation(@Valid @RequestBody Rezervacija rezervacija) {
		return rezervacijaService.save(rezervacija);
	}

	/* da uzmemo sve rezervacije, svima je dozvoljeno */
	@RequestMapping(value = "/api/reserve", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Rezervacija> getAllReservations() {
		return rezervacijaService.findAll();
	}

	/* da uzmemo rezervaciju po id-u, svima dozvoljeno */
	@RequestMapping(value = "/api/reserve/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Rezervacija> getReservation(
			@PathVariable(value = "id") Long reservationId) {
		Rezervacija rezervacija = rezervacijaService.findOne(reservationId);

		if (rezervacija == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(rezervacija);
	}
	
	/* da uzmemo RentAcar po nazivu, svima dozvoljeno */
	/*
	@RequestMapping(value = "/api/reserve/search/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Rezervacija>> getHotelsByName(
			@PathVariable(value = "name") String hotelName) {
		List<Rezervacija> hotels = rezervacijaService.containsName(hotelName);

		if (hotels == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(hotels);
	}*/

	/* update rezervacije po id-u */
	@RequestMapping(value = "/api/reserve/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Rezervacija> updateRezervacije(
			@PathVariable(value = "id") Long reservationId,
			@Valid @RequestBody Rezervacija rezervacijaDetalji) {

		Rezervacija rezervacija = rezervacijaService.findOne(reservationId);
		if (rezervacija == null) {
			return ResponseEntity.notFound().build();
		}
		
		/* OVDE TREBA DA SE DODAJU ATRIBUTI REZERVACIJE
		*/
		rezervacija.setAktivnaRezervacija(rezervacijaDetalji.getAktivnaRezervacija());
		/*rezervacija.setDatumRezervacije(rezervacijaDetalji.getDatumRezervacije());
		rezervacija.setOdabranaSedista(rezervacijaDetalji.getOdabranaSedista());
		rezervacija.setOdabranaVozila(rezervacijaDetalji.getOdabranaVozila());
		rezervacija.setOdabraneSobe(rezervacijaDetalji.getOdabraneSobe());
		*/
		Rezervacija updateRezervacija = rezervacijaService.save(rezervacija);
		return ResponseEntity.ok().body(updateRezervacija);
	}

	/* brisanje rezervacije */
	@RequestMapping(value = "/api/reserve/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Rezervacija> deleteReservation(
			@PathVariable(value = "id") Long reservationId) {
		Rezervacija rezervacija = rezervacijaService.findOne(reservationId);

		if (rezervacija != null) {
			rezervacijaService.remove(reservationId);
			logger.info("Hotel " + reservationId + " deleted.");
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.error("Hotel not found.");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
