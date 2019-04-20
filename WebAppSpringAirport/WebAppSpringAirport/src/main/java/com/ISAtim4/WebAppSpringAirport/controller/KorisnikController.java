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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ISAtim4.WebAppSpringAirport.domain.Korisnik;
import com.ISAtim4.WebAppSpringAirport.domain.RegistrovaniKorisnik;
import com.ISAtim4.WebAppSpringAirport.service.KorisnikService;

@RestController
public class KorisnikController {
private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private KorisnikService korisnikService;

	/* da snimimo korisnika */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/api/users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public Korisnik createKorisnik(@Valid @RequestBody Korisnik korisnik) {
		return korisnikService.save(korisnik);
	}

	/* da uzmemo sve korisnike */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/api/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Korisnik> getAllKorisnici() {
		return korisnikService.findAll();
	}

	/* da uzmemo korisnika po id-u */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/api/users/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Korisnik> getKorisnik(
			@PathVariable(value = "id") Long idKorisnika) {
		Korisnik korisnik = korisnikService.findOne(idKorisnika);

		if (korisnik == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(korisnik);
	}

	/* update korisnika po id-u */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/api/users/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Korisnik> updateKorisnika(
			@PathVariable(value = "id") Long korisnikId,
			@Valid @RequestBody Korisnik korisnikDetalji) {

		Korisnik korisnik = korisnikService.findOne(korisnikId);
		if (korisnik == null) {
			return ResponseEntity.notFound().build();
		}
		korisnik.setIme(korisnikDetalji.getIme());
		korisnik.setPrezime(korisnikDetalji.getPrezime());
		korisnik.setKorisnickoIme(korisnikDetalji.getKorisnickoIme());
		korisnik.setLozinka(korisnikDetalji.getLozinka());
		korisnik.setMail(korisnikDetalji.getMail());

		Korisnik updateKorisnik = korisnikService.save(korisnik);
		return ResponseEntity.ok().body(updateKorisnik);
	}

	/* brisanje korisnika */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/api/users/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Korisnik> deleteKorisnika(
			@PathVariable(value = "id") Long korisnikId) {
		Korisnik korisnik = korisnikService.findOne(korisnikId);
		if (korisnik != null) {
			korisnikService.remove(korisnikId);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/api/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Korisnik> dodavanjeKorisnikaPriRegistraciji(@Valid @RequestBody Korisnik reg_korisnik) {	
		Korisnik korisnik = korisnikService.findByKorisnickoIme(reg_korisnik.getKorisnickoIme());
		if (korisnik == null) {
			RegistrovaniKorisnik reg = new RegistrovaniKorisnik();
			reg.setIme(reg_korisnik.getIme());
			reg.setPrezime(reg_korisnik.getPrezime());
			
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(reg_korisnik.getLozinka());
			reg.setLozinka(hashedPassword);
			reg.setKorisnickoIme(reg_korisnik.getKorisnickoIme());
			reg.setMail(reg_korisnik.getMail());
			korisnikService.save(reg);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
	
}
