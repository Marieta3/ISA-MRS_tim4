package com.ISAtim4.WebAppSpringAirport.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.List;

import javax.validation.Valid;

import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ISAtim4.WebAppSpringAirport.domain.Let;
import com.ISAtim4.WebAppSpringAirport.domain.RegistrovaniKorisnik;
import com.ISAtim4.WebAppSpringAirport.domain.Rezervacija;
import com.ISAtim4.WebAppSpringAirport.domain.Sediste;
import com.ISAtim4.WebAppSpringAirport.domain.Soba;
import com.ISAtim4.WebAppSpringAirport.domain.Vozilo;
import com.ISAtim4.WebAppSpringAirport.dto.HotelDTO;
import com.ISAtim4.WebAppSpringAirport.dto.RezervacijaDTO;
import com.ISAtim4.WebAppSpringAirport.service.KorisnikService;
import com.ISAtim4.WebAppSpringAirport.service.LetService;
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
	
	/* da dodamo rezervaciju */
	@RequestMapping(value = "/api/reserve", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	public Rezervacija createReservation(@Valid @RequestBody RezervacijaDTO rezervacijaDTO, Principal user) {
		
		Rezervacija rezervacija=new Rezervacija();
		RegistrovaniKorisnik me=(RegistrovaniKorisnik) korisnikService.findByKorisnickoIme(user.getName());
		rezervacija.getKorisnici().add(me);
		
		if(!rezervacijaDTO.getSedista().isEmpty()) {
			Set<Sediste> sedista=sedisteService.findAllByLetRowCol(rezervacijaDTO.getId_leta(),rezervacijaDTO.getSedista());
			for(Sediste s: sedista) {
				s.setRezervisano(true);
				
			}
			rezervacija.setOdabranaSedista(sedista);
		}else {
			//greska, mora se rezervisati sediste
		}
		if(!rezervacijaDTO.getSobe().isEmpty()) {
			//rezervacija.setOdabraneSobe(sobaService.updateReservedRooms(rezervacijaDTO.getSobe()));
			Set<Soba> sobe=sobaService.findSobeIds(rezervacijaDTO.getSobe());
			for(Soba s: sobe) {
				s.setRezervisana(true);
				
			}
			rezervacija.setOdabraneSobe(sobe);
			rezervacija.setSobaZauzetaOd(rezervacijaDTO.getSobaOD());
			rezervacija.setSobaZauzetaDo(rezervacijaDTO.getSobaDO());
		}
		if(!rezervacijaDTO.getVozila().isEmpty()) {
			//rezervacija.setOdabranaVozila(voziloService.updateCarReservation(rezervacijaDTO.getVozila()));
			Set<Vozilo> vozila=voziloService.findVozilaIds(rezervacijaDTO.getVozila());
			for(Vozilo v:vozila) {
				v.setRezervisano(true);
			}
			rezervacija.setOdabranaVozila(vozila);
			rezervacija.setVoziloZauzetoOd(rezervacijaDTO.getVoziloOD());
			rezervacija.setVoziloZauzetoDo(rezervacijaDTO.getVoziloDO());
		}
		rezervacija.setCena(rezervacijaDTO.getUkupnaCena());
		rezervacija.setDatumRezervacije(new Date());
		//me.getRezervacije().add(rezervacija);
		return rezervacijaService.save(rezervacija);
	}

	/* da uzmemo sve rezervacije, svima je dozvoljeno */
	@RequestMapping(value = "/api/reserve", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Rezervacija> getAllReservations() {
		return rezervacijaService.findAll();
	}

	/* da uzmemo rezervaciju po id-u, svima dozvoljeno */
	@RequestMapping(value = "/api/reserve/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Rezervacija> getReservation(
			@PathVariable(value = "id") Long reservationId) {
		Rezervacija rezervacija = rezervacijaService.findOne(reservationId);

		if (rezervacija == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(rezervacija);
	}
	
	@RequestMapping(value = "/api/myReservations", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<Rezervacija> getMyReservations(Principal user) {
		RegistrovaniKorisnik me=(RegistrovaniKorisnik) korisnikService.findByKorisnickoIme(user.getName());
		List<Rezervacija> rezervacije=rezervacijaService.findAll();
		ArrayList<Rezervacija> moje=new ArrayList<Rezervacija>();
		for(Rezervacija r: rezervacije) {
			if(r.getKorisnici().contains(me)) {
				moje.add(r);
			}
		}
		return moje;
	}

	/* update rezervacije po id-u */
	@RequestMapping(value = "/api/reserve/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Rezervacija> updateRezervacije(
			@PathVariable(value = "id") Long reservationId,
			@Valid @RequestBody Rezervacija rezervacijaDetalji) {

		Rezervacija rezervacija = rezervacijaService.findOne(reservationId);
		if (rezervacija == null) {
			return ResponseEntity.notFound().build();
		}
		
		/* OVDE TREBA DA SE DODAJU ATRIBUTI REZERVACIJE
		*/
		rezervacija.setAktivnaRezervacija(rezervacijaDetalji.getAktivnaRezervacija());
		/*rezervacija.setDatumRezervacije(rezervacijaDetalji.getDatumRezervacije());
		rezervacija.setOdabranaSedista(rezervacijaDetalji.getOdabranaSedista());
		rezervacija.setOdabranaVozila(rezervacijaDetalji.getOdabranaVozila());
		rezervacija.setOdabraneSobe(rezervacijaDetalji.getOdabraneSobe());
		*/
		Rezervacija updateRezervacija = rezervacijaService.save(rezervacija);
		return ResponseEntity.ok().body(updateRezervacija);
	}

	/* brisanje rezervacije */
	@RequestMapping(value = "/api/reserve/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
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
