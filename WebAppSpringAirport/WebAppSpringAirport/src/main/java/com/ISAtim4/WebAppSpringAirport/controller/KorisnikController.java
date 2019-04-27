package com.ISAtim4.WebAppSpringAirport.controller;

import java.security.Principal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
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
import com.ISAtim4.WebAppSpringAirport.domain.ChangePswDTO;
import com.ISAtim4.WebAppSpringAirport.domain.Korisnik;
import com.ISAtim4.WebAppSpringAirport.domain.RegistrovaniKorisnik;
import com.ISAtim4.WebAppSpringAirport.dto.KorisnikDTO;
import com.ISAtim4.WebAppSpringAirport.service.AuthorityService;
import com.ISAtim4.WebAppSpringAirport.service.KorisnikService;
import com.ISAtim4.WebAppSpringAirport.service.NotificationService;


@RestController
public class KorisnikController {
private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	NotificationService notificationService;

	/* da dodamo korisnika */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/api/users", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public Korisnik createKorisnik(@Valid @RequestBody KorisnikDTO korisnikDTO) {
		String uloga = korisnikDTO.getUloga();
		Korisnik k=null;
		Authority a=null;
		switch(uloga) {
			case "avio": {
				k=new AdminAvio();
				a=authorityService.findByName("ROLE_AVIO");
				break;
			}
			case "rent": {
				k=new AdminRent();
				a=authorityService.findByName("ROLE_RENT");
				break;
			}
			case "hotel": {
				k=new AdminHotel();
				a=authorityService.findByName("ROLE_HOTEL");
				break;
			}
			default:
				k=new AdminSistem();
				a=authorityService.findByName("ROLE_ADMIN");
				break;
		}
		//generisanje random sifre
		String rndLozinka=randomPsw(8, 2, '6');
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		//hash
		String hashedPassword = passwordEncoder.encode(rndLozinka);
		
		k.setIme(korisnikDTO.getIme());
		k.setPrezime(korisnikDTO.getPrezime());
		k.setKorisnickoIme(korisnikDTO.getKorisnickoIme());
		k.setLozinka(hashedPassword);
		k.setMail(korisnikDTO.getMail());
		ArrayList<Authority> auth = new ArrayList<Authority>();
		auth.add(a);
		k.setAuthorities(auth);
		try{
			notificationService.sendPswToAdmin(k, rndLozinka);
		} catch(MailException ex) {
			logger.info("Error sending mail: "+ex.getMessage());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return korisnikService.save(k);
	}
	private static char randomChar() {
		String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random rand = new SecureRandom();
		return ALPHABET.charAt(rand.nextInt(ALPHABET.length()));
	}

	public static String randomPsw(int length, int spacing, char spacerChar) {
		String str = "";
		int spacer = 0;
		while (length > 0) {
			if (spacer == spacing) {
				str += spacerChar;
				spacer = 0;
			}
			length--;
			spacer++;
			str += randomChar();
		}
		return str;
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
		//BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		//String hashedPassword = passwordEncoder.encode(korisnikDetalji.getLozinka());
		/*
		 * Marieta: posto trenutno nema izmene lozinke u azuriranju profila
		 */
		korisnik.setLozinka(korisnikDetalji.getLozinka());
		korisnik.setMail(korisnikDetalji.getMail());
		korisnik.setSlika(korisnikDetalji.getSlika());
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
			
			//send a notification
			try{
				notificationService.sendNotification(reg);
			} catch(MailException ex) {
				logger.info("Error sending mail: "+ex.getMessage());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping("/api/whoami")
	//@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_HOTEL', 'ROLE_RENT', 'ROLE_AVIO')")
	public Korisnik korisnik(Principal user) {
		Korisnik k=null;
		if(user!=null) {
			k=this.korisnikService.findByKorisnickoIme(user.getName());
			System.out.println("----------------------------enabled: "+k.isEnabled());
		}
		return k;
	}
	@RequestMapping(value="/api/updatePassword", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_HOTEL', 'ROLE_RENT', 'ROLE_AVIO')")
	public ResponseEntity<Korisnik> updatePassword(Principal user, @Valid @RequestBody ChangePswDTO dto ){
		Korisnik k=null;
		if(user!=null) {
			k=this.korisnikService.findByKorisnickoIme(user.getName());
			//da li je dobra stara lozinka
			//provera lozinke(nije jednaka staroj i dve lozinke se poklapaju)
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(dto.getNewPsw());
			k.setLozinka(hashedPassword);
			k.setUlogovanPrviPut(true);
			return ResponseEntity.ok().body(korisnikService.save(k));
		}else {
			return ResponseEntity.notFound().build();
		}
		
		
	}
	
	/* da registrujemo korisnika sa verifikacionog linka */
	@RequestMapping(value = "/api/users/enabled/{korIme}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Korisnik> getKorisnikRegistracija(
			@PathVariable(value = "korIme") String korisnickoIme) {
		
		//BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		byte[] decodedBytes = Base64.getDecoder().decode(korisnickoIme);
		String decodedKorisnickoIme = new String(decodedBytes);
		Korisnik korisnik = korisnikService.findByKorisnickoIme(decodedKorisnickoIme);
		
		if (korisnik == null) {
			logger.info("Dati korisnik ne postoji!");
			return ResponseEntity.notFound().build();
		} 
		else {
			logger.info("Dati korisnik je enabled i moze da se loginuje sad");
			korisnik.setEnabled(true);
			korisnikService.save(korisnik);
			return ResponseEntity.ok().body(korisnik);
		}
	}

	
	
}
