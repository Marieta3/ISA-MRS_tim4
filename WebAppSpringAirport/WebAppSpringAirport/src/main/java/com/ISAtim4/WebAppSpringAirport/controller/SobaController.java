package com.ISAtim4.WebAppSpringAirport.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

import com.ISAtim4.WebAppSpringAirport.domain.AdminHotel;
import com.ISAtim4.WebAppSpringAirport.domain.Hotel;
import com.ISAtim4.WebAppSpringAirport.domain.Ocena;
import com.ISAtim4.WebAppSpringAirport.domain.Rezervacija;
import com.ISAtim4.WebAppSpringAirport.domain.Soba;
import com.ISAtim4.WebAppSpringAirport.domain.Usluga;
import com.ISAtim4.WebAppSpringAirport.dto.SobaDTO;
import com.ISAtim4.WebAppSpringAirport.dto.SobaPretragaDTO;
import com.ISAtim4.WebAppSpringAirport.service.HotelService;
import com.ISAtim4.WebAppSpringAirport.service.KorisnikService;
import com.ISAtim4.WebAppSpringAirport.service.OcenaService;
import com.ISAtim4.WebAppSpringAirport.service.RezervacijaService;
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
	
	@Autowired
	KorisnikService korisnikService;

	@Autowired
	OcenaService ocenaService;
	
	@Autowired
	RezervacijaService rezervacijaService;
	
	/* da snimimo sobu */
	@PreAuthorize("hasRole('ROLE_HOTEL')")
	@RequestMapping(method = RequestMethod.POST,value = "/api/sobe",  produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public Soba createSoba(@Valid @RequestBody SobaDTO sobaDTO) {
		Soba soba=new Soba();
		soba.setBrojKreveta(sobaDTO.getBrojKreveta());
		soba.setOpis(sobaDTO.getOpis());
		soba.setSlika(sobaDTO.getSlika());
		soba.setOcena(0.0);
		
		Hotel hotel=hotelService.findOne(sobaDTO.getIdHotela());
		soba.setHotel(hotel);
		
		//ne trb pristupati bazi kroz for petlju!!!
		System.out.println("\n\n\tbroj usluga: "+sobaDTO.getUsluge().size());
		if(!sobaDTO.getUsluge().isEmpty()) {
			System.out.println("ako sobaDTO.usluge nije prazna lista");
			ArrayList<Usluga> usluge=(ArrayList<Usluga>) uslugaService.findAllSelected(sobaDTO.getUsluge());
			soba.setUsluge(usluge);
		}
		return sobaService.save(soba);
	}

	/* da uzmemo sve sobe, svima dozvoljeno */
	@GetMapping(value = "/api/sobe", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Soba> getAllSobe() {
		List<Soba> sobe = sobaService.findAll();
		for (Soba soba : sobe) {
			List<Ocena> ocene = ocenaService.findAllBySoba(soba);
			soba.setOcena(Ocena.getProsek(ocene));
		}
		
		return sobe;
	}
	@GetMapping(value = "/api/sobeHotela/{hotel_id}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Soba> getSobeByHotel(@PathVariable(value = "hotel_id") Long hotel_id) {
		
		Hotel hotel=hotelService.findOne(hotel_id);
		List<Soba> sobe = sobaService.findAllByHotel(hotel);
		for (Soba soba : sobe) {
			List<Ocena> ocene = ocenaService.findAllBySoba(soba);
			soba.setOcena(Ocena.getProsek(ocene));
		}
		return sobe;
		
	}
	
	@PostMapping(value = "/api/sobeHotela/pretraga/{hotel_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Soba> getSobeByHotelPretraga(@PathVariable(value = "hotel_id") Long hotel_id, @Valid @RequestBody SobaPretragaDTO sobaDTO) {
		
		Hotel hotel=hotelService.findOne(hotel_id);
		
		//List<Soba> sobe = sobaService.findAllByHotel(hotel);
		List<Rezervacija> rezervacije=rezervacijaService.findAll();
		ArrayList<Soba> ne_moze=new ArrayList<>();
		ArrayList<Soba> pronadjene=new ArrayList<>();
		Date datum1=sobaDTO.getVremeDolaska();
		Calendar cal =Calendar.getInstance();
		cal.setTime(datum1);
		cal.add(Calendar.DATE, sobaDTO.getBrojNocenja());
		Date datum2=cal.getTime();
		//za svaku rezervaciju
		for(Rezervacija r: rezervacije) {
			//ako se preklapaju datumi
			if( (r.getSobaZauzetaOd().compareTo(datum1)<=0 && r.getSobaZauzetaDo().compareTo(datum1)>=0) 
					|| (r.getSobaZauzetaOd().compareTo(datum2)<=0 && r.getSobaZauzetaDo().compareTo(datum2) >= 0) 
					|| (r.getSobaZauzetaOd().compareTo(datum1)>=0 && r.getSobaZauzetaDo().compareTo(datum2)<=0) ) {
				System.out.println("+++---***\n\n\t broj soba u rezervaciji: "+r.getOdabraneSobe().size());
				//za svaku sobu u rezervaciji
				for(Soba s: r.getOdabraneSobe()) {
					//ako soba pripada trazenom hotelu
					if(s.getHotel().equals(hotel)) {
						System.out.println("------------to je taj hotel "+hotel.getNaziv());
						//ako jos nije dodata u listu zauzetih
						if(!ne_moze.contains(s) ){
							//dodaj u listu zauzetih
							ne_moze.add(s);
						}
					}
				}
			}
		}
		System.out.println("broj rezervisanih soba: "+ne_moze.size());
		//za svaku sobu trazenog hotela
		for (Soba soba : hotel.getSobe()) {
			List<Ocena> ocene = ocenaService.findAllBySoba(soba);
			soba.setOcena(Ocena.getProsek(ocene));
			//ako se ne nalazi u listi zauzetih
			if(!ne_moze.contains(soba)) {
				//dodaj u listu pronadjenih
				pronadjene.add(soba);
			}
		}
		return pronadjene;
		
	}
	@PreAuthorize("hasRole('ROLE_HOTEL')")
	@GetMapping(value = "/api/sobeHotela", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Soba> getSobeHotela(Principal user) {
		AdminHotel me = new AdminHotel();

		if (user != null) {
			me = (AdminHotel) this.korisnikService.findByKorisnickoIme(user.getName());
		}
		List<Soba> sobe =  sobaService.findAllByHotel(me.getHotel());
		
		for (Soba soba : sobe) {
			List<Ocena> ocene = ocenaService.findAllBySoba(soba);
			soba.setOcena(Ocena.getProsek(ocene));
		}
		
		return sobe;
	}
	/* da uzmemo sobu po id-u, svima dozvoljeno */
	@GetMapping(value = "/api/sobe/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Soba> getSoba(
			@PathVariable(value = "id") Long sobaId) {
		Soba soba = sobaService.findOne(sobaId);

		if (soba == null) {
			return ResponseEntity.notFound().build();
		}
		List<Ocena> ocene = ocenaService.findAllBySoba(soba);
		soba.setOcena(Ocena.getProsek(ocene));
		
		return ResponseEntity.ok().body(soba);
	}

	/* update sobe po id-u */
	@PreAuthorize("hasRole('ROLE_HOTEL')")
	@PutMapping(value = "/api/sobe/{id}", produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Soba> updateSobe(
			@PathVariable(value = "id") Long sobaId,
			@Valid @RequestBody SobaDTO sobaDTO) {

		Soba soba = sobaService.findOne(sobaId);
		if (soba == null) {
			return ResponseEntity.notFound().build();
		}else{
			ArrayList<Rezervacija> aktivne = (ArrayList<Rezervacija>) rezervacijaService.findAll();
			for (Rezervacija rezervacija : aktivne) {
				if(rezervacija.getAktivnaRezervacija()){
					Set<Soba>  s= rezervacija.getOdabraneSobe();
					for (Soba soba2 : s) {
						if(soba2.getId() == soba.getId()){
							return new ResponseEntity<>(HttpStatus.FORBIDDEN);
					}
					}
				}
			}
		}

		soba.setOpis(sobaDTO.getOpis());
		soba.setSlika(sobaDTO.getSlika());
		soba.setBrojKreveta(sobaDTO.getBrojKreveta());
		
		if(!sobaDTO.getUsluge().isEmpty()) {
			ArrayList<Usluga> usluge=(ArrayList<Usluga>) uslugaService.findAllSelected(sobaDTO.getUsluge());
			soba.setUsluge(usluge);		
		}
		Soba updateSoba = sobaService.save(soba);
		return ResponseEntity.ok().body(updateSoba);
	}

	/* brisanje sobe */
	@PreAuthorize("hasRole('ROLE_HOTEL')")
	@DeleteMapping(value = "/api/sobe/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Soba> deleteSoba(
			@PathVariable(value = "id") Long sobaId) {
		Soba soba = sobaService.findOne(sobaId);

		if (soba != null) {
			ArrayList<Rezervacija> aktivne = (ArrayList<Rezervacija>) rezervacijaService.findAll();
			for (Rezervacija rezervacija : aktivne) {
				if(rezervacija.getAktivnaRezervacija()){
					Set<Soba>  s= rezervacija.getOdabraneSobe();
					for (Soba soba2 : s) {
						if(soba2.getId() == soba.getId()){
							return new ResponseEntity<>(HttpStatus.FORBIDDEN);
					}
					}
				}
			}
			sobaService.remove(sobaId);
			logger.info("Room " + sobaId + " deleted!");
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.error("Room " + sobaId + " not found!");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
