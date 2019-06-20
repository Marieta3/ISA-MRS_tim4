package com.ISAtim4.WebAppSpringAirport.service;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ISAtim4.WebAppSpringAirport.domain.Korisnik;
import com.ISAtim4.WebAppSpringAirport.domain.NeregistrovaniPutnik;
import com.ISAtim4.WebAppSpringAirport.domain.Pozivnica;
import com.ISAtim4.WebAppSpringAirport.domain.RegistrovaniKorisnik;
import com.ISAtim4.WebAppSpringAirport.domain.Rezervacija;
import com.ISAtim4.WebAppSpringAirport.domain.Sediste;
import com.ISAtim4.WebAppSpringAirport.domain.Soba;
import com.ISAtim4.WebAppSpringAirport.domain.Vozilo;
import com.ISAtim4.WebAppSpringAirport.dto.RezervacijaDTO;
import com.ISAtim4.WebAppSpringAirport.repository.RezervacijaRepository;

@Service
@Transactional(readOnly = true)
public class RezervacijaService {
	@Autowired
	private RezervacijaRepository rezervacijaRepository;

	@Autowired
	LetService letservice;

	@Autowired
	SobaService sobaService;

	@Autowired
	VoziloService voziloService;

	@Autowired
	KorisnikService korisnikService;

	@Autowired
	SedisteService sedisteService;

	@Autowired
	PozivnicaService pozivnicaService;
	
	@Autowired
	NotificationService notificationService;

	@Transactional(readOnly = false)
	public Rezervacija save(Rezervacija rezervacija) {
		return rezervacijaRepository.save(rezervacija);
	}

	public Rezervacija findOne(Long id) {
		return rezervacijaRepository.getOne(id);
	}

	public List<Rezervacija> findAll() {
		return rezervacijaRepository.findAll();
	}

	public Page<Rezervacija> findAll(Pageable page) {
		return rezervacijaRepository.findAll(page);
	}

	public List<Rezervacija> findAllByUser(RegistrovaniKorisnik rk) {
		return rezervacijaRepository.findAllByUser(rk);
	}

	@Transactional(readOnly = false)
	public void remove(Long id) {
		rezervacijaRepository.deleteById(id);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Rezervacija create(RezervacijaDTO rezervacijaDTO, Principal user) throws ParseException {
		RegistrovaniKorisnik me = (RegistrovaniKorisnik) korisnikService.findByKorisnickoIme(user.getName());
		System.out.println("\n\n\tservice\n\t" + rezervacijaDTO.toString());
		// rezervacija
		Rezervacija rezervacija = new Rezervacija();

		rezervacija.getKorisnici().add(me);

		if (!rezervacijaDTO.getSedista().isEmpty()) {
			Set<Sediste> sedista = sedisteService.findAllByLetRowCol(rezervacijaDTO.getId_leta(),
					rezervacijaDTO.getSedista());
			for (Sediste s : sedista) {
				if (!s.isRezervisano()) {
					s.setRezervisano(true);
					System.out.println("\n\n\tservice\n\t" + s.toString());
				} else {
					throw new RuntimeException("Error: seat already reserved");
				}

			}
			rezervacija.setOdabranaSedista(sedista);
		} else {
			// greska, mora se rezervisati sediste
			return null;
		}
		if (!rezervacijaDTO.getSobe().isEmpty()) {
			// rezervacija.setOdabraneSobe(sobaService.updateReservedRooms(rezervacijaDTO.getSobe()));
			Set<Soba> sobe = sobaService.findSobeIds(rezervacijaDTO.getSobe());
			for (Soba s : sobe) {
				s.setRezervisana(true);

			}
			rezervacija.setOdabraneSobe(sobe);
			rezervacija.setSobaZauzetaOd(rezervacijaDTO.getSobaOD());
			Calendar cal = Calendar.getInstance();
			cal.setTime(rezervacijaDTO.getSobaOD());
			cal.add(Calendar.DATE, rezervacijaDTO.getBrojNocenja());
			Date sobaRezervisanaDo = cal.getTime();
			rezervacija.setSobaZauzetaDo(sobaRezervisanaDo);
		}
		if (!rezervacijaDTO.getVozila().isEmpty()) {
			// rezervacija.setOdabranaVozila(voziloService.updateCarReservation(rezervacijaDTO.getVozila()));
			Set<Vozilo> vozila = voziloService.findVozilaIds(rezervacijaDTO.getVozila());
			for (Vozilo v : vozila) {
				v.setRezervisano(true);
			}
			rezervacija.setOdabranaVozila(vozila);
			rezervacija.setVoziloZauzetoOd(rezervacijaDTO.getVoziloOD());
			rezervacija.setVoziloZauzetoOd(rezervacijaDTO.getVoziloOD());
			Calendar cal = Calendar.getInstance();
			cal.setTime(rezervacijaDTO.getVoziloOD());
			cal.add(Calendar.DATE, rezervacijaDTO.getBrojDana());
			Date voziloRezervisanaDo = cal.getTime();
			rezervacija.setVoziloZauzetoDo(voziloRezervisanaDo);
		}
		rezervacija.setCena(rezervacijaDTO.getUkupnaCena());
		rezervacija.setDatumRezervacije(new Date());
		// me.getRezervacije().add(rezervacija);

		// pozivnice
		if (!rezervacijaDTO.getPozvani_prijatelji().isEmpty()) {
			ArrayList<Korisnik> pozvani = korisnikService.findAllIds(rezervacijaDTO.getPozvani_prijatelji());
			// ne bi trebalo u for petljiii
			for (Korisnik k : pozvani) {
				Pozivnica pozivnica = new Pozivnica();
				pozivnica.setDatumSlanja(new Date());
				pozivnica.setKoSalje(me);
				pozivnica.setKomeSalje((RegistrovaniKorisnik) k);
				pozivnica.setRezervacija(rezervacija);
				pozivnica.setPrihvacen(false);
				pozivnica.setReagovanoNaPoziv(false);
				pozivnicaService.save(pozivnica);
				try {
					notificationService.sendInvitation(me,pozivnica);
				} catch (MailException ex) {
					System.out.printf("Error sending mail: {0}",ex.getMessage());
				}
			}
		}
		
		if(!rezervacijaDTO.getNeregistrovani().isEmpty()) {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			for(String str: rezervacijaDTO.getNeregistrovani()) {
				NeregistrovaniPutnik nereg=new NeregistrovaniPutnik();
				String tokens[]=str.split(" ");
				nereg.setIme(tokens[0]);
				nereg.setPrezime(tokens[1]);
				nereg.setBrojPasosa(tokens[2]);
				nereg.setDatumRodjenja(sdf.parse(tokens[3]));
				nereg.setEmail(tokens[4]);
				rezervacija.getNeregistrovani().add(nereg);
				try {
					notificationService.sendInvitationNereg(nereg);
				} catch (MailException ex) {
					System.out.printf("Error sending mail: {0}",ex.getMessage());
				}
			}
		}
		System.out.println("\n\n\tservice\n\t" + rezervacija.toString());
		
		
		return save(rezervacija);
	}
	
	
}
