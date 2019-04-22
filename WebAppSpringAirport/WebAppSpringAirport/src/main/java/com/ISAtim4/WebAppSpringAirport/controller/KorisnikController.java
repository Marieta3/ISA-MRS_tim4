package com.ISAtim4.WebAppSpringAirport.controller;

import java.security.Principal;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ISAtim4.WebAppSpringAirport.domain.AdminAvio;
import com.ISAtim4.WebAppSpringAirport.domain.AdminHotel;
import com.ISAtim4.WebAppSpringAirport.domain.AdminRent;
import com.ISAtim4.WebAppSpringAirport.domain.AdminSistem;
import com.ISAtim4.WebAppSpringAirport.domain.Authority;
import com.ISAtim4.WebAppSpringAirport.domain.Korisnik;
import com.ISAtim4.WebAppSpringAirport.domain.RegistrovaniKorisnik;
import com.ISAtim4.WebAppSpringAirport.dto.KorisnikDTO;
import com.ISAtim4.WebAppSpringAirport.service.AuthorityService;
import com.ISAtim4.WebAppSpringAirport.service.KorisnikService;


@RestController
public class KorisnikController {
private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private AuthorityService authorityService;

	/* da dodamo korisnika */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/api/users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public Korisnik createKorisnik(@Valid @RequestBody KorisnikDTO korisnikDTO) {
		String uloga = korisnikDTO.getUloga();
		switch(uloga) {
			case "avio": {
				AdminAvio avio = new AdminAvio();
				avio.setIme(korisnikDTO.getIme());
				avio.setPrezime(korisnikDTO.getPrezime());
				avio.setKorisnickoIme(korisnikDTO.getKorisnickoIme());
				avio.setLozinka(korisnikDTO.getLozinka());
				avio.setMail(korisnikDTO.getMail());
				Authority authority = authorityService.findByName("ROLE_AVIO");
				ArrayList<Authority> auth = new ArrayList<Authority>();
				auth.add(authority);
				avio.setAuthorities(auth);
				return korisnikService.save(avio);
			}
			case "rent": {
				AdminRent rent = new AdminRent();
				rent.setIme(korisnikDTO.getIme());
				rent.setPrezime(korisnikDTO.getPrezime());
				rent.setKorisnickoIme(korisnikDTO.getKorisnickoIme());
				rent.setLozinka(korisnikDTO.getLozinka());
				rent.setMail(korisnikDTO.getMail());
				Authority authority = authorityService.findByName("ROLE_RENT");
				ArrayList<Authority> auth = new ArrayList<Authority>();
				auth.add(authority);
				rent.setAuthorities(auth);	
				return korisnikService.save(rent);
			}
			case "hotel": {
				AdminHotel hotel = new AdminHotel();
				hotel.setIme(korisnikDTO.getIme());
				hotel.setPrezime(korisnikDTO.getPrezime());
				hotel.setKorisnickoIme(korisnikDTO.getKorisnickoIme());
				hotel.setLozinka(korisnikDTO.getLozinka());
				hotel.setMail(korisnikDTO.getMail());
				Authority authority = authorityService.findByName("ROLE_HOTEL");
				ArrayList<Authority> auth = new ArrayList<Authority>();
				auth.add(authority);
				hotel.setAuthorities(auth);	
				return korisnikService.save(hotel);
			}
			default:
				AdminSistem sis = new AdminSistem();
				sis.setIme(korisnikDTO.getIme());
				sis.setPrezime(korisnikDTO.getPrezime());
				sis.setKorisnickoIme(korisnikDTO.getKorisnickoIme());
				sis.setLozinka(korisnikDTO.getLozinka());
				sis.setMail(korisnikDTO.getMail());
				Authority authority = authorityService.findByName("ROLE_ADMIN");
				ArrayList<Authority> auth = new ArrayList<Authority>();
				auth.add(authority);
				sis.setAuthorities(auth);	
				return korisnikService.save(sis);
		}
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
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER','ROLE_AVIO','ROLE_RENT','ROLE_HOTEL')")
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
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(korisnikDetalji.getLozinka());
		korisnik.setLozinka(hashedPassword);
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
			Authority authority = authorityService.findByName("ROLE_USER");
			ArrayList<Authority> auth = new ArrayList<Authority>();
			auth.add(authority);
			reg.setAuthorities(auth);
			korisnikService.save(reg);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping("/api/whoami")
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_HOTEL', 'ROLE_RENT', 'ROLE_AVIO')")
	public Korisnik korisnik(Principal user) {
		return this.korisnikService.findByKorisnickoIme(user.getName());
	}
	
	
}
