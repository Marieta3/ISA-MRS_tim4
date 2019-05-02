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

import com.ISAtim4.WebAppSpringAirport.domain.Hotel;
import com.ISAtim4.WebAppSpringAirport.service.HotelService;

@RestController
public class HotelController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	HotelService hotelService;

	/* da dodamo hotel */
	@RequestMapping(value = "/api/hotels", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	/*
	 * Milan: trenutno sam dodao proveru da li trenutno
	 * ulogovan korisnik ima rolu ADMIN. Kada sredite model svih korisnika i dodate adekvatne role
	 * onda ce pretpostavljam ovde biti provera "hasRole('HOTEL_ADMIN')"
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Hotel createHotel(@Valid @RequestBody Hotel hotel) {
		return hotelService.save(hotel);
	}

	/* da uzmemo sve hotele, svima je dozvoljeno */
	@RequestMapping(value = "/api/hotels", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Hotel> getAllHotels() {
		return hotelService.findAll();
	}

	/* da uzmemo hotel po id-u, svima dozvoljeno */
	@RequestMapping(value = "/api/hotels/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hotel> getHotel(
			@PathVariable(value = "id") Long hotelId) {
		Hotel hotel = hotelService.findOne(hotelId);

		if (hotel == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(hotel);
	}
	
	/* da uzmemo RentAcar po nazivu, svima dozvoljeno */
	
	@RequestMapping(value = "/api/hotels/search/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Hotel>> getHotelsByName(
			@PathVariable(value = "name") String hotelName) {
		List<Hotel> hotels = hotelService.containsName(hotelName);

		if (hotels == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(hotels);
	}

	/* update hotela po id-u */
	@RequestMapping(value = "/api/hotels/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('ROLE_HOTEL', 'ROLE_ADMIN')")
	public ResponseEntity<Hotel> updateHotela(
			@PathVariable(value = "id") Long hotelId,
			@Valid @RequestBody Hotel hotelDetalji) {

		Hotel hotel = hotelService.findOne(hotelId);
		if (hotel == null) {
			return ResponseEntity.notFound().build();
		}

		hotel.setNaziv(hotelDetalji.getNaziv());
		hotel.setAdresa(hotelDetalji.getAdresa());
		hotel.setOpis(hotelDetalji.getOpis());
		hotel.setSlika(hotelDetalji.getSlika());
		Hotel updateHotel = hotelService.save(hotel);
		return ResponseEntity.ok().body(updateHotel);
	}

	/* brisanje hotela */
	@RequestMapping(value = "/api/hotels/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Hotel> deleteHotel(
			@PathVariable(value = "id") Long hotelId) {
		Hotel hotel = hotelService.findOne(hotelId);

		if (hotel != null) {
			hotelService.remove(hotelId);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
