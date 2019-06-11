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
import com.ISAtim4.WebAppSpringAirport.domain.Ocena;
import com.ISAtim4.WebAppSpringAirport.domain.Rezervacija;
import com.ISAtim4.WebAppSpringAirport.domain.Soba;
import com.ISAtim4.WebAppSpringAirport.dto.HotelDTO;
import com.ISAtim4.WebAppSpringAirport.service.HotelService;
import com.ISAtim4.WebAppSpringAirport.service.OcenaService;
import com.ISAtim4.WebAppSpringAirport.service.RezervacijaService;

@RestController
public class HotelController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	HotelService hotelService;
	
	@Autowired
	private OcenaService ocenaService;
	
	@Autowired
	private RezervacijaService rezervacijaService;

	/* da dodamo hotel */
	@RequestMapping(value = "/api/hotels", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	/*
	 * Milan: trenutno sam dodao proveru da li trenutno
	 * ulogovan korisnik ima rolu ADMIN. Kada sredite model svih korisnika i dodate adekvatne role
	 * onda ce pretpostavljam ovde biti provera "hasRole('HOTEL_ADMIN')"
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Hotel createHotel(@Valid @RequestBody Hotel hotel) {
		hotel.setCoord1(31.214535);
		hotel.setCoord2(29.945663);
		return hotelService.save(hotel);
	}
	
	//za PRETRAGU HOTELA
	@RequestMapping(value = "/api/hotels/pretraga", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public List<Hotel> pretragaHotel(@Valid @RequestBody HotelDTO hotel) {
		System.out.println("tip: "+hotel.getTipPretrage()+"\nstring: "+hotel.getLokNaziv()+"\nvreme1: "+hotel.getDatumPolaska()+"\nvreme2: "+hotel.getDatumDolaska());
		List<Rezervacija> rezervacije=rezervacijaService.findAll();
		ArrayList<Hotel> ne_moze=new ArrayList<>();
		ArrayList<Hotel> pronadjeni=new ArrayList<>();
		ArrayList<Hotel> pronadjeni2=new ArrayList<>();
		for(Rezervacija r: rezervacije) {
			//moze samo ako su oba pre pocetka ili oba posle kraja
			if( (r.getSobaZauzetaOd().compareTo(hotel.getDatumPolaska())<=0 && r.getSobaZauzetaDo().compareTo(hotel.getDatumPolaska())>=0) 
					|| (r.getSobaZauzetaOd().compareTo(hotel.getDatumDolaska())<=0 && r.getSobaZauzetaDo().compareTo(hotel.getDatumDolaska()) >= 0) 
					|| (r.getSobaZauzetaOd().compareTo(hotel.getDatumPolaska())>=0 && r.getSobaZauzetaDo().compareTo(hotel.getDatumDolaska())<=0) ) {
				for(Soba s: r.getOdabraneSobe()) {
					if(!ne_moze.contains(s.getHotel())) {
						ne_moze.add(s.getHotel());
					}
				}
			}
			/*if( (hotel.getDatumPolaska().before(r.getSobaZauzetaOd()) && hotel.getDatumDolaska().before(r.getSobaZauzetaOd())) 
					|| (hotel.getDatumPolaska().after(r.getSobaZauzetaDo()) && hotel.getDatumDolaska().after(r.getSobaZauzetaDo())) ) {
				for(Soba s: r.getOdabraneSobe()) {
					if(!pronadjeni.contains(s.getHotel())) {
						pronadjeni.add(s.getHotel());
					}
				}
			}*/
		}
		ArrayList<Hotel> hoteli=(ArrayList<Hotel>) hotelService.findAll();
		for(Hotel h:hoteli) {
			if(!ne_moze.contains(h)) {
				pronadjeni.add(h);
			}
		}
		System.out.println("pretraga po datumu: "+pronadjeni.size());
		if (hotel.getTipPretrage().equals("location")){
			//pretraga po lokaciji
			for(Hotel h: pronadjeni) {
				List<Ocena> ocene = ocenaService.findAllByHotel(h);
				h.setOcena(Ocena.getProsek(ocene));
				if(h.getAdresa().contains(hotel.getLokNaziv())) {
					pronadjeni2.add(h);
				}
			}
			/*List<Hotel> hoteli = hotelService.searchHotelsLocation(hotel.getLokNaziv(),hotel.getDatumPolaska(),hotel.getDatumDolaska());
			for (Hotel hotel2 : hoteli) {
				List<Ocena> ocene = ocenaService.findAllByHotel(hotel2);
				hotel2.setOcena(Ocena.getProsek(ocene));
			}*/
			return pronadjeni2;
		} else{
			//pretraga po nazivu hotela
			for(Hotel h: pronadjeni) {
				List<Ocena> ocene = ocenaService.findAllByHotel(h);
				h.setOcena(Ocena.getProsek(ocene));
				if(h.getNaziv().contains(hotel.getLokNaziv())) {
					pronadjeni2.add(h);
				}
			}
			/*List<Hotel> hoteli = hotelService.searchHotelsName(hotel.getLokNaziv(),hotel.getDatumPolaska(),hotel.getDatumDolaska());
			for (Hotel hotel2 : hoteli) {
				List<Ocena> ocene = ocenaService.findAllByHotel(hotel2);
				hotel2.setOcena(Ocena.getProsek(ocene));
			}*/
			return pronadjeni2;
		}
	}

	/* da uzmemo sve hotele, svima je dozvoljeno */
	@RequestMapping(value = "/api/hotels", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Hotel> getAllHotels() {
		List<Hotel> hoteli = hotelService.findAll();
		for (Hotel hotel2 : hoteli) {
			List<Ocena> ocene = ocenaService.findAllByHotel(hotel2);
			hotel2.setOcena(Ocena.getProsek(ocene));
		}
		return hoteli;
	}

	/* da uzmemo hotel po id-u, svima dozvoljeno */
	@RequestMapping(value = "/api/hotels/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hotel> getHotel(
			@PathVariable(value = "id") Long hotelId) {
		Hotel hotel = hotelService.findOne(hotelId);

		if (hotel == null) {
			return ResponseEntity.notFound().build();
		}
		List<Ocena> ocene = ocenaService.findAllByHotel(hotel);
		hotel.setOcena(Ocena.getProsek(ocene));
		
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
		
		for (Hotel hotel2 : hotels) {
			List<Ocena> ocene = ocenaService.findAllByHotel(hotel2);
			hotel2.setOcena(Ocena.getProsek(ocene));
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
		hotel.setCoord1(hotelDetalji.getCoord1());
		hotel.setCoord2(hotelDetalji.getCoord2());
		List<Ocena> ocene = ocenaService.findAllByHotel(hotel);
		hotel.setOcena(Ocena.getProsek(ocene));
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
			logger.info("Hotel " + hotelId + " deleted.");
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.error("Hotel not found.");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
