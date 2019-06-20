package com.ISAtim4.WebAppSpringAirport.service;

import java.security.Principal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ISAtim4.WebAppSpringAirport.domain.AdminAvio;
import com.ISAtim4.WebAppSpringAirport.domain.AdminHotel;
import com.ISAtim4.WebAppSpringAirport.domain.AdminRent;
import com.ISAtim4.WebAppSpringAirport.domain.AdminSistem;
import com.ISAtim4.WebAppSpringAirport.domain.Authority;
import com.ISAtim4.WebAppSpringAirport.domain.AvioKompanija;
import com.ISAtim4.WebAppSpringAirport.domain.Hotel;
import com.ISAtim4.WebAppSpringAirport.domain.Korisnik;
import com.ISAtim4.WebAppSpringAirport.domain.RegistrovaniKorisnik;
import com.ISAtim4.WebAppSpringAirport.domain.RentACar;
import com.ISAtim4.WebAppSpringAirport.dto.ChangePswDTO;
import com.ISAtim4.WebAppSpringAirport.dto.KorisnikDTO;
import com.ISAtim4.WebAppSpringAirport.repository.KorisnikRepository;

@Service
@Transactional(readOnly = true)
public class KorisnikService {
	@Autowired
	private KorisnikRepository korisnikRepository;

	@Autowired
	private AvioKompanijaService avioService;
	
	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private RentACarService rentACarService;
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	NotificationService notificationService;

	public List<Korisnik> findAll() {
		return korisnikRepository.findAll();
	}

	public Korisnik findOne(Long id) {
		return korisnikRepository.getOne(id);
	}

	public Page<Korisnik> findAll(Pageable page) {
		return korisnikRepository.findAll(page);
	}

	@Transactional(readOnly = false)
	public Korisnik save(Korisnik korisnik) {
		return korisnikRepository.save(korisnik);
	}

	@Transactional(readOnly = false)
	public void remove(Long id) {
		korisnikRepository.deleteById(id);
	}

	public Korisnik findByKorisnickoImeAndLozinka(String username,
			String password) {
		return korisnikRepository.findByKorisnickoImeAndLozinka(username,
				password);
	}

	public Korisnik findByKorisnickoIme(String username) {
		return korisnikRepository.findByKorisnickoIme(username);
	}

	public Korisnik findOneID(Long id) {
		return korisnikRepository.findOneID(id);
	}
	
	public List<Korisnik>findNotConnectedPeople(List<Long>ids){
		return korisnikRepository.findNotConnectedPeople(ids);
	}
	
	public ArrayList<Korisnik> findAllIds(List<Long> ids){
		return korisnikRepository.findKorisniciIds(ids);
	}
	
	public Set<Korisnik> findAllIdsSet(List<Long> ids){
		return korisnikRepository.findKorisniciIdsSet(ids);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Korisnik create(KorisnikDTO korisnikDTO) {
		String uloga = korisnikDTO.getUloga();
		Korisnik k = null;
		Authority a = null;
		switch (uloga) {
		case "avio": {
			k = new AdminAvio();

			Long id = Long.parseLong(korisnikDTO.adminOf.split("_")[1]);
			AvioKompanija avio = avioService.findOne(id);
			((AdminAvio) k).setAvio_kompanija(avio);
			
			a = authorityService.findByName("ROLE_AVIO");
			break;
		}
		case "rent": {
			k = new AdminRent();

			Long id = Long.parseLong(korisnikDTO.adminOf.split("_")[1]);
			RentACar rent = rentACarService.findOne(id);
			((AdminRent) k).setrentACar(rent);
			
			a = authorityService.findByName("ROLE_RENT");
			break;
		}
		case "hotel": {
			k = new AdminHotel();

			Long id = Long.parseLong(korisnikDTO.adminOf.split("_")[1]);
			Hotel hotel = hotelService.findOne(id);
			((AdminHotel) k).setHotel(hotel);
			
			a = authorityService.findByName("ROLE_HOTEL");
			break;
		}
		default:
			k = new AdminSistem();
			a = authorityService.findByName("ROLE_ADMIN");
			break;
		}
		// generisanje random sifre
		String rndLozinka = randomPsw(8, 2, '6');
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		// hash
		String hashedPassword = passwordEncoder.encode(rndLozinka);

		k.setIme(korisnikDTO.getIme());
		k.setPrezime(korisnikDTO.getPrezime());
		k.setKorisnickoIme(korisnikDTO.getKorisnickoIme());
		k.setLozinka(hashedPassword);
		k.setMail(korisnikDTO.getMail());
		ArrayList<Authority> auth = new ArrayList<>();
		auth.add(a);
		k.setAuthorities(auth);
		try {
			notificationService.sendPswToAdmin(k, rndLozinka);
		} catch (MailException ex) {
			System.out.printf("Error sending mail: {0}",ex.getMessage());
		}
		return save(k);
	}
	
	private static char randomChar() {
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random rand = new SecureRandom();
		return alphabet.charAt(rand.nextInt(alphabet.length()));
	}

	private static String randomPsw(int length, int spacing, char spacerChar) {
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
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Korisnik update(Long korisnikId,KorisnikDTO korisnikDetalji) {
		Korisnik korisnik = findOne(korisnikId);
		if (korisnik == null) {
			return null;
		}
		korisnik.setIme(korisnikDetalji.getIme());
		korisnik.setPrezime(korisnikDetalji.getPrezime());
		korisnik.setKorisnickoIme(korisnikDetalji.getKorisnickoIme());
		// BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		// String hashedPassword =
		// passwordEncoder.encode(korisnikDetalji.getLozinka());
		/*
		 * Marieta: posto trenutno nema izmene lozinke u azuriranju profila
		 */
		System.out.println("\n\n\t"+korisnik.getLozinka()+"\n\n");
		//korisnik.setLozinka(korisnikDetalji.getLozinka());
		korisnik.setMail(korisnikDetalji.getMail());
		korisnik.setSlika(korisnikDetalji.getSlika());
		korisnik.setAdresa(korisnikDetalji.getAdresa());
		korisnik.setTelefon(korisnikDetalji.getTelefon());
		Korisnik updateKorisnik = save(korisnik);
		return updateKorisnik;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public int delete(Principal user,Long korisnikId) {
		Korisnik korisnik = findOne(korisnikId);
		
		Korisnik ulogovanKorisnik = null;
		if (user != null) {
			ulogovanKorisnik = this.findByKorisnickoIme(user.getName());
			if(korisnikId.equals(ulogovanKorisnik.getId())){
				return 1;
			}
		}
		
		if (korisnik != null) {
			remove(korisnikId);
			return 2;
		} else {
			return 3;
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean dodavanjeKorisnikaPriRegistraciji(KorisnikDTO reg_korisnik) {
		Korisnik korisnik = findByKorisnickoIme(reg_korisnik.getKorisnickoIme());
		if (korisnik == null) {
			RegistrovaniKorisnik reg = new RegistrovaniKorisnik();
			reg.setIme(reg_korisnik.getIme());
			reg.setPrezime(reg_korisnik.getPrezime());

			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(reg_korisnik
					.getLozinka());
			reg.setLozinka(hashedPassword);
			reg.setKorisnickoIme(reg_korisnik.getKorisnickoIme());
			reg.setMail(reg_korisnik.getMail());
			reg.setAdresa(reg_korisnik.getAdresa());
			reg.setTelefon(reg_korisnik.getTelefon());
			reg.setBrojPoena(0);
			Authority authority = authorityService.findByName("ROLE_USER");
			ArrayList<Authority> auth = new ArrayList<>();
			auth.add(authority);
			reg.setAuthorities(auth);
			save(reg);

			// send a notification
			try {
				notificationService.sendNotification(reg);
			} catch (MailException ex) {
				System.out.printf("Error sending mail: {0}",ex.getMessage());
			}

			return true;
		} else {
			return false;
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String changePassword(Principal user, ChangePswDTO dto) {
		Korisnik k = null;
		if (user != null) {
			k = findByKorisnickoIme(user.getName());
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			
			String hashedPassword = passwordEncoder.encode(dto.getNewPsw());  //uneta novi pw
			//String oldPsw = passwordEncoder.encode(dto.getOldPsw());		//unteta stari pw
			
			
			if(BCrypt.checkpw(dto.getOldPsw(), k.getLozinka())){
				System.out.println("Old password is correct!");
			}else {
				return ("Old password is not correct!");
			}
			
			if (dto.getOldPsw().equals(dto.getNewPsw())) {
				return "New and old password should not match!";
			} else if (!dto.getNewPsw().equals(dto.getConfirmPsw())) {
				return "Passwords do not match!";
			} else if (dto.getOldPsw().equals("") || dto.getNewPsw().equals("")
					|| dto.getConfirmPsw().equals("")) {
				return "Password should not be empty!";
			}
			// provera lozinke(nije jednaka staroj i dve lozinke se poklapaju)

			k.setLozinka(hashedPassword);
			k.setUlogovanPrviPut(true);
			save(k);
			return "OK";
		} else {
			return "User not found.";
		}
	}
}
