package com.ISAtim4.WebAppSpringAirport.controller;

import java.security.Principal;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.ISAtim4.WebAppSpringAirport.domain.AdminAvio;
import com.ISAtim4.WebAppSpringAirport.domain.AdminHotel;
import com.ISAtim4.WebAppSpringAirport.domain.AdminRent;
import com.ISAtim4.WebAppSpringAirport.domain.AvioKompanija;
import com.ISAtim4.WebAppSpringAirport.domain.Hotel;
import com.ISAtim4.WebAppSpringAirport.domain.Korisnik;
import com.ISAtim4.WebAppSpringAirport.domain.Ocena;
import com.ISAtim4.WebAppSpringAirport.domain.RentACar;
import com.ISAtim4.WebAppSpringAirport.dto.ChangePswDTO;
import com.ISAtim4.WebAppSpringAirport.dto.KorisnikDTO;
import com.ISAtim4.WebAppSpringAirport.service.AuthorityService;
import com.ISAtim4.WebAppSpringAirport.service.AvioKompanijaService;
import com.ISAtim4.WebAppSpringAirport.service.HotelService;
import com.ISAtim4.WebAppSpringAirport.service.KorisnikService;
import com.ISAtim4.WebAppSpringAirport.service.NotificationService;
import com.ISAtim4.WebAppSpringAirport.service.OcenaService;
import com.ISAtim4.WebAppSpringAirport.service.RentACarService;

@RestController
public class KorisnikController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private HotelService hotelService;
	
	@Autowired 
	private AvioKompanijaService avioService;
	
	@Autowired
	private RentACarService rentACarService;
	
	@Autowired
	private OcenaService ocenaService;
	
	@Autowired
	private AuthorityService authorityService;

	@Autowired
	NotificationService notificationService;

	/* da dodamo korisnika */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping(value = "/api/users",produces= MediaType.APPLICATION_JSON_VALUE, consumes=MediaType.APPLICATION_JSON_VALUE)
	public Korisnik createKorisnik(@Valid @RequestBody KorisnikDTO korisnikDTO) {
		
		return korisnikService.create(korisnikDTO);
	}

	private static char randomChar() {
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random rand = new SecureRandom();
		return alphabet.charAt(rand.nextInt(alphabet.length()));
	}

	public static String randomPsw(int length, int spacing, char spacerChar) {
		StringBuilder str = new StringBuilder();
		//String str = "";
		int spacer = 0;
		while (length > 0) {
			if (spacer == spacing) {
				str.append(spacerChar);
				spacer = 0;
			}
			length--;
			spacer++;
			str.append(randomChar());
		}
		return str.toString();
	}

	/* da uzmemo sve korisnike */
	//@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_HOTEL', 'ROLE_RENT', 'ROLE_AVIO')")
	@RequestMapping(value = "/api/users",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Korisnik> getAllKorisnici() {
		return korisnikService.findAll();
	}

	/* da uzmemo korisnika po id-u */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(value = "/api/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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
	@PutMapping(value = "/api/users/{id}",  produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Korisnik> updateKorisnika(
			@PathVariable(value = "id") Long korisnikId,
			@Valid @RequestBody KorisnikDTO korisnikDetalji) {

		Korisnik korisnik = korisnikService.update(korisnikId, korisnikDetalji);
		if (korisnik == null) {
			return ResponseEntity.notFound().build();
		}

		
		return ResponseEntity.ok().body(korisnik);

	}

	/* brisanje korisnika */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping(value = "/api/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Korisnik> deleteKorisnika(Principal user,
			@PathVariable(value = "id") Long korisnikId) {
		
		
		int flag=korisnikService.delete(user, korisnikId);
		if(flag==1) {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}else if(flag==2) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping(value = "/api/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Korisnik> dodavanjeKorisnikaPriRegistraciji(
			@Valid @RequestBody KorisnikDTO reg_korisnik) {
		
		
		if(korisnikService.dodavanjeKorisnikaPriRegistraciji(reg_korisnik)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
	}

	@GetMapping("/api/whoami")
	// @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_HOTEL', 'ROLE_RENT', 'ROLE_AVIO')")
	public Korisnik korisnik(Principal user) {
		Korisnik k = null;
		
		if (user != null) {
			k = this.korisnikService.findByKorisnickoIme(user.getName());
			logger.info("----------------------------enabled: {0}",k.isEnabled());
		}
		return k;
	}
	/*@RequestMapping("/api/myReservations")
	@PreAuthorize("hasRole('ROLE_USER')")
	public Set<Rezervacija> myReservations(Principal user) {
		RegistrovaniKorisnik rk = null;
		
		if (user != null) {
			rk = (RegistrovaniKorisnik) this.korisnikService.findByKorisnickoIme(user.getName());
			
		}
		return rk.getRezervacije();
	}*/
	@GetMapping("/api/hotel_admin")
	@PreAuthorize("hasRole('ROLE_HOTEL')")
	public AdminHotel adminHotel(Principal user) {
		AdminHotel ah=null;
		if(user!=null) {
			ah=(AdminHotel) this.korisnikService.findByKorisnickoIme(user.getName());
		}
		return ah;
	}
	
	@GetMapping("/api/rent_admin")
	@PreAuthorize("hasRole('ROLE_HOTEL')")
	public AdminRent adminRent(Principal user) {
		AdminRent ah=null;
		if(user!=null) {
			ah=(AdminRent) this.korisnikService.findByKorisnickoIme(user.getName());
		}
		return ah;
	}
	
	@GetMapping("/api/myhotel")
	@PreAuthorize("hasRole('ROLE_HOTEL')")
	public Hotel getMyHotel(Principal user) {
		AdminHotel ah = new AdminHotel();
		if(user!=null) {
			ah=(AdminHotel) this.korisnikService.findByKorisnickoIme(user.getName());
			List<Ocena> ocene = ocenaService.findAllByHotel(ah.getHotel());
			ah.getHotel().setOcena(Ocena.getProsek(ocene));
		}
		return ah.getHotel();
	}
	
	@GetMapping("/api/myavio")
	@PreAuthorize("hasRole('ROLE_AVIO')")
	public AvioKompanija getMyAvio(Principal user) {
		AdminAvio aa=new AdminAvio();
		if(user!=null) {
			aa=(AdminAvio) this.korisnikService.findByKorisnickoIme(user.getName());

			List<Ocena> ocene = ocenaService.findAllByAvio(aa.getAvio_kompanija());
			aa.getAvio_kompanija().setOcena(Ocena.getProsek(ocene));
		}
		return aa.getAvio_kompanija();
	}
	
	@GetMapping("/api/myrent")
	@PreAuthorize("hasRole('ROLE_RENT')")
	public RentACar getMyRent(Principal user) {
		AdminRent ah=new AdminRent();
		if(user!=null) {
			ah=(AdminRent) this.korisnikService.findByKorisnickoIme(user.getName());
			List<Ocena> ocene = ocenaService.findAllByRent(ah.getrentACar());
			ah.getrentACar().setOcena(Ocena.getProsek(ocene));
		}
		return ah.getrentACar();
	}
	
	
	@PutMapping(value = "/api/updatePassword", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_HOTEL', 'ROLE_RENT', 'ROLE_AVIO')")
	public String updatePassword(Principal user,
			@Valid @RequestBody ChangePswDTO dto) {
		return korisnikService.changePassword(user, dto);

	}

	/* da registrujemo korisnika sa verifikacionog linka */
	@GetMapping(value = "/api/users/enabled/{korIme}", produces = MediaType.TEXT_PLAIN_VALUE)
	public RedirectView getKorisnikRegistracija(
			@PathVariable(value = "korIme") String korisnickoIme, RedirectAttributes redirectAttributes, HttpServletResponse resp){

		// BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		byte[] decodedBytes = Base64.getDecoder().decode(korisnickoIme);
		String decodedKorisnickoIme = new String(decodedBytes);
		Korisnik korisnik = korisnikService
				.findByKorisnickoIme(decodedKorisnickoIme);

		if (korisnik == null) {
			logger.info("Dati korisnik ne postoji!");
			return new RedirectView("/index.html");
			
		} else {
			logger.info("Dati korisnik je enabled i moze da se loginuje sad");
			korisnik.setEnabled(true);
			korisnikService.save(korisnik);
			return new RedirectView("/index.html");
		}
	}

}
