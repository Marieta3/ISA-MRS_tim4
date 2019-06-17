package com.ISAtim4.WebAppSpringAirport.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
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
import com.ISAtim4.WebAppSpringAirport.dto.Chart1DTO;
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
		//ArrayList<Hotel> ne_moze=new ArrayList<>();
		ArrayList<Soba> ne_moze=new ArrayList<>();
		ArrayList<Hotel> pronadjeni=new ArrayList<>();
		ArrayList<Hotel> pronadjeni2=new ArrayList<>();
		Date pocetak=hotel.getDatumPolaska();
		Date kraj=hotel.getDatumDolaska();
		for(Rezervacija r: rezervacije) {
			//moze samo ako su oba pre pocetka ili oba posle kraja
			if( (r.getSobaZauzetaOd().compareTo(pocetak)<=0 && r.getSobaZauzetaDo().compareTo(pocetak)>=0) 
					|| (r.getSobaZauzetaOd().compareTo(kraj)<=0 && r.getSobaZauzetaDo().compareTo(kraj) >= 0) 
					|| (r.getSobaZauzetaOd().compareTo(pocetak)>=0 && r.getSobaZauzetaDo().compareTo(kraj)<=0) ) {
				//dodaju se sve sobe koje su zauzete
				for(Soba s: r.getOdabraneSobe()) {
					if(!ne_moze.contains(s)) {
						ne_moze.add(s);
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
			for(Soba s: h.getSobe()) {
				//ako se soba ne nalazi u listi zauzetih, dodaj hotel kome pripada i iteriraj sledeci hotel
				if(!ne_moze.contains(s)) {
					pronadjeni.add(h);
					break;
				}
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
	
	@PreAuthorize("hasRole('ROLE_HOTEL')")
	@RequestMapping(value = "/api/hotels/chart1/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Chart1DTO> getChart1(
			@PathVariable(value = "id") Long hotelId) {
		Hotel hotel = hotelService.findOne(hotelId);
		
		if (hotel == null) {
			return ResponseEntity.notFound().build();
		}
		
		List<Ocena> ocene = ocenaService.findAllByHotel(hotel);
		hotel.setOcena(Ocena.getProsek(ocene));
		
		
		double sum = 0.0;
		int i = 0;
		
		List<Hotel> hotels = hotelService.findAll();
		for (Hotel r : hotels) {
			List<Ocena> oceneR = ocenaService.findAllByHotel(r);
			r.setOcena(Ocena.getProsek(oceneR));
			if(r.getOcena()!= 0.0)
			{
				++i;
				sum += r.getOcena();
			}
		}
		double avg = 0.0;
		if(i != 0){
			avg =  sum/i;
		}
		avg = BigDecimal.valueOf(avg)
			    .setScale(2, RoundingMode.HALF_UP)
			    .doubleValue();
		
		Chart1DTO retval = new Chart1DTO();
		retval.setService(hotel.getNaziv());
		retval.setServiceRating(hotel.getOcena());
		retval.setOthers("Services average");
		retval.setOthersRating(avg);
		
		return ResponseEntity.ok().body(retval);
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
