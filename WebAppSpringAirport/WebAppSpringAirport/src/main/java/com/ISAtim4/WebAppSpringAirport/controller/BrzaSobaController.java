package com.ISAtim4.WebAppSpringAirport.controller;


import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
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

import com.ISAtim4.WebAppSpringAirport.domain.BrzaSoba;
import com.ISAtim4.WebAppSpringAirport.domain.Hotel;
import com.ISAtim4.WebAppSpringAirport.domain.RegistrovaniKorisnik;
import com.ISAtim4.WebAppSpringAirport.domain.Sediste;
import com.ISAtim4.WebAppSpringAirport.domain.Soba;
import com.ISAtim4.WebAppSpringAirport.dto.BrzaRezervacijaDTO;
import com.ISAtim4.WebAppSpringAirport.dto.BrzaSobaVoziloDTO;
import com.ISAtim4.WebAppSpringAirport.dto.SobaPretragaDTO;
import com.ISAtim4.WebAppSpringAirport.service.BrzaSobaService;
import com.ISAtim4.WebAppSpringAirport.service.HotelService;
import com.ISAtim4.WebAppSpringAirport.service.KorisnikService;
import com.ISAtim4.WebAppSpringAirport.service.SedisteService;
import com.ISAtim4.WebAppSpringAirport.service.SobaService;

@RestController
public class BrzaSobaController {
	@Autowired
	private BrzaSobaService brzaSobaService;
	
	@Autowired
	private SobaService sobaService;
	
	@Autowired
	private HotelService hotelService;
	
	@Autowired
	KorisnikService korisnikService;
	
	@Autowired
	SedisteService sedisteService;
	
	@PreAuthorize("hasRole('ROLE_HOTEL')")
	@PostMapping(value = "/api/quick/room", produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public BrzaSoba createBrzaSoba(@Valid @RequestBody BrzaRezervacijaDTO brzaSobaDTO) {
		
		return brzaSobaService.create(brzaSobaDTO);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping(value = "/api/brzeSobe/reserve", produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public BrzaSoba rezervisanjeBrzeSobe(@Valid @RequestBody BrzaSobaVoziloDTO brzaSobaDTO, Principal user) {
		BrzaSoba brzaSoba=brzaSobaService.findOne(brzaSobaDTO.getBrz_id());
		RegistrovaniKorisnik me=(RegistrovaniKorisnik) korisnikService.findByKorisnickoIme(user.getName());
		brzaSoba.setPutnik(me);
		brzaSoba.setDatumRezervacije(new Date());
		brzaSoba.setZauzeto(true);
		Sediste sediste =  sedisteService.findOneByLetRowCol(brzaSobaDTO.getLet_id(), brzaSobaDTO.getRow_col());
		sediste.setRezervisano(true);
		brzaSoba.setSediste(sediste);
		
		return brzaSobaService.save(brzaSoba);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping(value = "/api/brzeSobe/{id}", produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public List<BrzaSoba> getBrzeSobeHotela(@PathVariable(value = "id") Long hotelId, @Valid @RequestBody SobaPretragaDTO sobaDTO) {
		//Hotel hotel=hotelService.findOne(hotelId);
		Date datum1=sobaDTO.getVremeDolaska();
		Calendar cal =Calendar.getInstance();
		cal.setTime(datum1);
		cal.add(Calendar.DATE, sobaDTO.getBrojNocenja());
		Date datum2=cal.getTime();
		List<BrzaSoba> sve=brzaSobaService.findAll();
		List<BrzaSoba> lista=brzaSobaService.getBrzeSobeHotela(hotelId, datum1, datum2);
		System.out.println("\npronadjene\n\t"+lista.toString());
		System.out.println("\nsve\n\t"+sve.toString());
		System.out.println("\ndat2\n\t"+datum2);
		System.out.println("\ndat1\n\t"+datum1);
		return lista;
	}
	
	/* da uzmemo sve usluge, svima dozvoljeno */
	@GetMapping(value = "/api/quick/room", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<BrzaSoba> getAllBrzeSobe() {
		return brzaSobaService.findAll();
	}
	
	
	/* da uzmemo uslugu po id-u, svima dozvoljeno */
	@GetMapping(value = "/api/quick/room/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BrzaSoba> getBrzaSoba(
			@PathVariable(value = "id") Long brzaSobaId) {
		BrzaSoba brzaSoba = brzaSobaService.findOne(brzaSobaId);

		if (brzaSoba == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(brzaSoba);
	}

	/* update usluge po id-u */
	@PreAuthorize("hasRole('ROLE_HOTEL')")
	@PutMapping(value = "/api/quick/room/{id}", produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BrzaSoba> updateBrzeSobe(
			@PathVariable(value = "id") Long brzaSobaId,
			@Valid @RequestBody BrzaSoba brzaSobaDetalji) {

		BrzaSoba brzaSoba = brzaSobaService.findOne(brzaSobaId);
		if (brzaSoba == null) {
			return ResponseEntity.notFound().build();
		}

		BrzaSoba updateBrzaSoba = brzaSobaService.save(brzaSoba);
		
		return ResponseEntity.ok().body(updateBrzaSoba);
	}

	/* brisanje usluge */
	@PreAuthorize("hasRole('ROLE_HOTEL')")
	@DeleteMapping(value = "/api/quick/room/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BrzaSoba> deleteBrzaSoba(
			@PathVariable(value = "id") Long brzaSobaId) {
		BrzaSoba brzaSoba = brzaSobaService.findOne(brzaSobaId);

		if (brzaSoba != null) {
			brzaSobaService.remove(brzaSobaId);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
