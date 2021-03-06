package com.ISAtim4.WebAppSpringAirport.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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
import org.springframework.web.bind.annotation.RestController;

import com.ISAtim4.WebAppSpringAirport.domain.Bodovanje;
import com.ISAtim4.WebAppSpringAirport.domain.Korisnik;
import com.ISAtim4.WebAppSpringAirport.domain.NeregistrovaniPutnik;
import com.ISAtim4.WebAppSpringAirport.domain.Pozivnica;
import com.ISAtim4.WebAppSpringAirport.domain.RegistrovaniKorisnik;
import com.ISAtim4.WebAppSpringAirport.domain.Rezervacija;
import com.ISAtim4.WebAppSpringAirport.domain.Sediste;
import com.ISAtim4.WebAppSpringAirport.domain.Soba;
import com.ISAtim4.WebAppSpringAirport.domain.Vozilo;
import com.ISAtim4.WebAppSpringAirport.dto.CancelReservationDTO;
import com.ISAtim4.WebAppSpringAirport.dto.RezervacijaDTO;
import com.ISAtim4.WebAppSpringAirport.service.BodovanjeService;
import com.ISAtim4.WebAppSpringAirport.service.KorisnikService;
import com.ISAtim4.WebAppSpringAirport.service.LetService;
import com.ISAtim4.WebAppSpringAirport.service.PozivnicaService;
import com.ISAtim4.WebAppSpringAirport.service.RezervacijaService;
import com.ISAtim4.WebAppSpringAirport.service.SedisteService;
import com.ISAtim4.WebAppSpringAirport.service.SobaService;
import com.ISAtim4.WebAppSpringAirport.service.VoziloService;

@RestController
public class RezervacijaController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	RezervacijaService rezervacijaService;

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
	BodovanjeService bodovanjeService;

	/* da dodamo rezervaciju */
	@PostMapping(value = "/api/reserve", produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	public Rezervacija createReservation(
			@Valid @RequestBody RezervacijaDTO rezervacijaDTO, Principal user) throws ParseException {
		/*
		 * RegistrovaniKorisnik me=(RegistrovaniKorisnik)
		 * korisnikService.findByKorisnickoIme(user.getName());
		 * 
		 * //rezervacija Rezervacija rezervacija=new Rezervacija();
		 * 
		 * rezervacija.getKorisnici().add(me);
		 * 
		 * if(!rezervacijaDTO.getSedista().isEmpty()) { Set<Sediste>
		 * sedista=sedisteService
		 * .findAllByLetRowCol(rezervacijaDTO.getId_leta(),
		 * rezervacijaDTO.getSedista()); for(Sediste s: sedista) {
		 * s.setRezervisano(true);
		 * 
		 * } rezervacija.setOdabranaSedista(sedista); }else { //greska, mora se
		 * rezervisati sediste } if(!rezervacijaDTO.getSobe().isEmpty()) {
		 * //rezervacija
		 * .setOdabraneSobe(sobaService.updateReservedRooms(rezervacijaDTO
		 * .getSobe())); Set<Soba>
		 * sobe=sobaService.findSobeIds(rezervacijaDTO.getSobe()); for(Soba s:
		 * sobe) { s.setRezervisana(true);
		 * 
		 * } rezervacija.setOdabraneSobe(sobe);
		 * rezervacija.setSobaZauzetaOd(rezervacijaDTO.getSobaOD()); Calendar
		 * cal=Calendar.getInstance(); cal.setTime(rezervacijaDTO.getSobaOD());
		 * cal.add(Calendar.DATE, rezervacijaDTO.getBrojNocenja()); Date
		 * sobaRezervisanaDo=cal.getTime();
		 * rezervacija.setSobaZauzetaDo(sobaRezervisanaDo); }
		 * if(!rezervacijaDTO.getVozila().isEmpty()) {
		 * //rezervacija.setOdabranaVozila
		 * (voziloService.updateCarReservation(rezervacijaDTO.getVozila()));
		 * Set<Vozilo>
		 * vozila=voziloService.findVozilaIds(rezervacijaDTO.getVozila());
		 * for(Vozilo v:vozila) { v.setRezervisano(true); }
		 * rezervacija.setOdabranaVozila(vozila);
		 * rezervacija.setVoziloZauzetoOd(rezervacijaDTO.getVoziloOD());
		 * rezervacija.setVoziloZauzetoDo(rezervacijaDTO.getVoziloDO()); }
		 * rezervacija.setCena(rezervacijaDTO.getUkupnaCena());
		 * rezervacija.setDatumRezervacije(new Date());
		 * //me.getRezervacije().add(rezervacija);
		 * 
		 * //pozivnice if(!rezervacijaDTO.getPozvani_prijatelji().isEmpty()) {
		 * ArrayList<Korisnik>
		 * pozvani=korisnikService.findAllIds(rezervacijaDTO.
		 * getPozvani_prijatelji()); //ne bi trebalo u for petljiii for(Korisnik
		 * k: pozvani) { Pozivnica pozivnica=new Pozivnica();
		 * pozivnica.setDatumSlanja(new Date()); pozivnica.setKoSalje(me);
		 * pozivnica.setKomeSalje((RegistrovaniKorisnik) k);
		 * pozivnica.setRezervacija(rezervacija); pozivnica.setPrihvacen(false);
		 * pozivnica.setReagovanoNaPoziv(false);
		 * pozivnicaService.save(pozivnica); } }
		 */
		Rezervacija rez = rezervacijaService.create(rezervacijaDTO, user);
		System.out.println("\n\n\tcontroller\n\t" + rez.toString());
		return rez;
		// return rezervacijaService.save(rezervacija);
	}

	/*
	 * kreira se rezervacija za preview ali se nista ne cuva u bazi pozvani
	 * korisnici se dodaju u listu putnika kako bi se prikazali u preview-u
	 */
	@PostMapping(value = "/api/reserve/preview", produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	public Rezervacija previewReservation(
			@Valid @RequestBody RezervacijaDTO rezervacijaDTO, Principal user) throws ParseException {
		RegistrovaniKorisnik me = (RegistrovaniKorisnik) korisnikService
				.findByKorisnickoIme(user.getName());

		// rezervacija
		Rezervacija rezervacija = new Rezervacija();

		rezervacija.getKorisnici().add(me);

		if (!rezervacijaDTO.getSedista().isEmpty()) {
			Set<Sediste> sedista = sedisteService.findAllByLetRowCol(
					rezervacijaDTO.getId_leta(), rezervacijaDTO.getSedista());

			rezervacija.setOdabranaSedista(sedista);
		} else {
			// greska, mora se rezervisati sediste
		}
		if (!rezervacijaDTO.getSobe().isEmpty()) {
			// rezervacija.setOdabraneSobe(sobaService.updateReservedRooms(rezervacijaDTO.getSobe()));
			Set<Soba> sobe = sobaService.findSobeIds(rezervacijaDTO.getSobe());

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
			Set<Vozilo> vozila = voziloService.findVozilaIds(rezervacijaDTO
					.getVozila());

			rezervacija.setOdabranaVozila(vozila);
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

		if (!rezervacijaDTO.getPozvani_prijatelji().isEmpty()) {
			ArrayList<Korisnik> pozvani = korisnikService
					.findAllIds(rezervacijaDTO.getPozvani_prijatelji());
			for (Korisnik rk : pozvani) {
				rezervacija.getKorisnici().add((RegistrovaniKorisnik) rk);
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
			}
		}
		return rezervacija;
	}

	/* da uzmemo sve rezervacije, svima je dozvoljeno */
	@GetMapping(value = "/api/reserve", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Rezervacija> getAllReservations() {
		return rezervacijaService.findAll();
	}

	/* da uzmemo rezervaciju po id-u, svima dozvoljeno */
	@GetMapping(value = "/api/reserve/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Rezervacija> getReservation(
			@PathVariable(value = "id") Long reservationId) {
		Rezervacija rezervacija = rezervacijaService.findOne(reservationId);
		
		if (rezervacija == null) {
			return ResponseEntity.notFound().build();
		}
		System.out.println(rezervacija.getOdabranaSedista().iterator().next().getLet().getAvioKompanija().getNaziv());
		
		return ResponseEntity.ok().body(rezervacija);
	}

	@GetMapping(value = "/api/myReservations", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<Rezervacija> getMyReservations(Principal user) {
		RegistrovaniKorisnik me = (RegistrovaniKorisnik) korisnikService
				.findByKorisnickoIme(user.getName());
		List<Rezervacija> rezervacije = rezervacijaService.findAll();
		ArrayList<Rezervacija> moje = new ArrayList<Rezervacija>();
		for (Rezervacija r : rezervacije) {
			if (r.getKorisnici().contains(me)) {
				if(r.getAktivnaRezervacija())
				{
					moje.add(r);
				}
			}
		}
		return moje;
	}

	@GetMapping(value = "/api/myReservationHistory", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<Rezervacija> myReservationHistory(Principal user) {
		RegistrovaniKorisnik me = (RegistrovaniKorisnik) korisnikService
				.findByKorisnickoIme(user.getName());
		List<Rezervacija> rezervacije = rezervacijaService.findAll();
		ArrayList<Rezervacija> moje = new ArrayList<Rezervacija>();
		Date now = new Date();
		for (Rezervacija r : rezervacije) {
			Date d1 = r.getOdabranaSedista().iterator().next().getLet()
					.getVremeDolaska();
			if (r.getKorisnici().contains(me)) {
				if (now.after(d1)) {
					if (r.getVoziloZauzetoDo() != null) {
						if (now.after(r.getVoziloZauzetoDo())) {
							if (r.getSobaZauzetaDo() != null) {	
								if (now.after(r.getSobaZauzetaDo())) {
									r.setAktivnaRezervacija(false);
									rezervacijaService.save(r);
									moje.add(r);				// korisnik je napustao, vracao i sletio
								} else {
									continue; // korisnik jos nije napustao sobu
								}
							} else {
								r.setAktivnaRezervacija(false);
								rezervacijaService.save(r);
								moje.add(r); // korisnik je vracao vozilo i sletio
							}
						} else {
							continue; // korisnik jos nije vracao vozilo
						}
					} else {	//nije rezervisao vozilo
						if (r.getSobaZauzetaDo() != null) {
							if (now.after(r.getSobaZauzetaDo())) {
								r.setAktivnaRezervacija(false);
								rezervacijaService.save(r);
								moje.add(r);	//korinsnik je napustao i sletio
							} else {
								continue; // korisnik jos nije napustao sobu
							}
						} else { 
							r.setAktivnaRezervacija(false);
							rezervacijaService.save(r);
							moje.add(r); // korisnik je sletio
						}
					}
				} else {
					continue; // korisnik jos nije sletio
				}
			}
		}
		return moje;
	}
	
	/* update rezervacije po id-u */
	@PostMapping(value = "/api/reserve/cancel/{id}",  produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Rezervacija> cancelRezervacija(
			@PathVariable(value = "id") Long reservationId,
			@Valid @RequestBody CancelReservationDTO rezer) {
		
		// ha lemondasy valamit számoldd újra az össz valuet cena stb
		Rezervacija rezervacija = rezervacijaService.findOne(reservationId);
		if (rezervacija == null) {
			return ResponseEntity.notFound().build();
		}

		boolean e1 = rezer.isFlightID();
		boolean e2 = rezer.isHotelID();
		boolean e3 =  rezer.isCarID();
		
		Calendar nowCal = Calendar.getInstance();
		Calendar rCal = Calendar.getInstance();

		
		if(e1)
		{	//ako ima manje od 3 sata ne moze
			nowCal.setTime(new Date());
			rCal.setTime(rezervacija.getOdabranaSedista().iterator().next().getLet().getVremePolaska());
			//provera 
			nowCal.add(Calendar.HOUR_OF_DAY, 3);
			if(nowCal.after(rCal))
			{
				System.out.println("Too late to cancel - let:" + rCal.getTime());
				return ResponseEntity.badRequest().build();
			}
		}
		else if(e2)
		{	//ako ima manje od 2 dana 
			nowCal.setTime(new Date());
			if((rezervacija.getSobaZauzetaOd() != null))
				rCal.setTime(rezervacija.getSobaZauzetaOd());
			//provera 
			nowCal.add(Calendar.DATE, 2);
			if(nowCal.after(rCal))
			{
				System.out.println("Too late to cancel - soba:" + rCal.getTime());
				return ResponseEntity.badRequest().build();
			}
		}else if(e3)
		{	//ako ima manje od 2 dana 
			nowCal.setTime(new Date());
			if((rezervacija.getVoziloZauzetoOd() != null))
				rCal.setTime(rezervacija.getVoziloZauzetoOd());
			//provera 
			nowCal.add(Calendar.DATE, 2);
			if(nowCal.after(rCal))
			{
				System.out.println("Too late to cancel - car:" + rCal.getTime());
				return ResponseEntity.badRequest().build();
			}
		}
		
		
		if(e1)			//ako je birao let --> brišem celu rezervaciju
		{
			for (Sediste s : rezervacija.getOdabranaSedista()) {
				s.setRezervisano(false);
			}
			List<Pozivnica> p =  pozivnicaService.findAll();
			for (Pozivnica pozivnica : p) {
				if(pozivnica.getRezervacija().getId() == rezervacija.getId())
				{
					pozivnicaService.remove(pozivnica.getId());
				}
			}
			
			Bodovanje b = bodovanjeService.findOne(1L);
			double km = rezervacija.getOdabranaSedista().iterator().next().getLet().getDuzinaPutovanja() *1.0;
			double bonusPoeni = km / b.getKmZaBroj();
			
			for (RegistrovaniKorisnik r : rezervacija.getKorisnici()) {
				
				double userPoint = r.getBrojPoena();
				userPoint -= bonusPoeni;
				if(userPoint >= b.getMaxBroj())
				{
					userPoint = b.getMaxBroj()*1.0;
				}if(userPoint< 0)
				{
					userPoint = 0;
				}
				r.setBrojPoena(userPoint);
				korisnikService.save(r);
			}
			rezervacijaService.remove(rezervacija.getId());
			return ResponseEntity.ok().body(rezervacija);

		}else if(e2 && e3)			//ako hoće da canceluje smestaj i transport, cena je samo let
		{
			rezervacija.getOdabraneSobe().clear();
			rezervacija.getOdabranaVozila().clear();
			double cena = 0;
			for (Sediste s : rezervacija.getOdabranaSedista()) {
				cena += s.getCena();
			}
			rezervacija.setCena(cena);;
		}else if(e2)		//ako hoće da canceluje smestaj
		{
			rezervacija.getOdabraneSobe().clear();
			double cena = 0;
			for (Sediste s : rezervacija.getOdabranaSedista()) {
				cena += s.getCena();
			}
			
			if((rezervacija.getOdabranaVozila()!= null) && (rezervacija.getOdabranaVozila().size()!= 0))
			{
				int brojDana = 0;
				if((rezervacija.getVoziloZauzetoDo() == null) || (rezervacija.getVoziloZauzetoOd() == null))
				{
					brojDana = 1;
				}else{
				    long diff = rezervacija.getVoziloZauzetoDo().getTime() - rezervacija.getVoziloZauzetoOd().getTime();
				    brojDana =  (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
				}
			    for (Vozilo v : rezervacija.getOdabranaVozila()) {
					cena += v.getCena() * brojDana;
				}
			}
			rezervacija.setCena(cena);
			
		}else if(e3)		//ako hoće da canceluje smestaj
		{
			rezervacija.getOdabranaVozila().clear();
			double cena = 0;
			for (Sediste s : rezervacija.getOdabranaSedista()) {
				cena += s.getCena();
			}
			
			if((rezervacija.getOdabraneSobe()!= null) && (rezervacija.getOdabraneSobe().size()!= 0))
			{
				int brojDana = 0;
				if((rezervacija.getSobaZauzetaDo() == null) || (rezervacija.getSobaZauzetaOd() == null))
				{
					brojDana = 1;
				}else{
				    long diff = rezervacija.getSobaZauzetaDo().getTime() - rezervacija.getSobaZauzetaOd().getTime();
				    brojDana =  (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
				}
			    for (Soba v : rezervacija.getOdabraneSobe()) {
					cena += v.getCena() * brojDana;
				}
			}
			rezervacija.setCena(cena);
		}
		
		rezervacijaService.save(rezervacija);
		return ResponseEntity.ok().body(rezervacija);
	}

	/* update rezervacije po id-u */
	@PutMapping(value = "/api/reserve/{id}",  produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Rezervacija> updateRezervacije(
			@PathVariable(value = "id") Long reservationId,
			@Valid @RequestBody Rezervacija rezervacijaDetalji) {

		Rezervacija rezervacija = rezervacijaService.findOne(reservationId);
		if (rezervacija == null) {
			return ResponseEntity.notFound().build();
		}

		/*
		 * OVDE TREBA DA SE DODAJU ATRIBUTI REZERVACIJE
		 */
		rezervacija.setAktivnaRezervacija(rezervacijaDetalji
				.getAktivnaRezervacija());
		/*
		 * rezervacija.setDatumRezervacije(rezervacijaDetalji.getDatumRezervacije
		 * ());
		 * rezervacija.setOdabranaSedista(rezervacijaDetalji.getOdabranaSedista
		 * ());
		 * rezervacija.setOdabranaVozila(rezervacijaDetalji.getOdabranaVozila
		 * ());
		 * rezervacija.setOdabraneSobe(rezervacijaDetalji.getOdabraneSobe());
		 */
		Rezervacija updateRezervacija = rezervacijaService.save(rezervacija);
		return ResponseEntity.ok().body(updateRezervacija);
	}

	/* brisanje rezervacije */
	@DeleteMapping(value = "/api/reserve/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Rezervacija> deleteReservation(
			@PathVariable(value = "id") Long reservationId) {
		Rezervacija rezervacija = rezervacijaService.findOne(reservationId);

		if (rezervacija != null) {
			rezervacijaService.remove(reservationId);
			logger.info("Hotel " + reservationId + " deleted.");
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.error("Hotel not found.");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
