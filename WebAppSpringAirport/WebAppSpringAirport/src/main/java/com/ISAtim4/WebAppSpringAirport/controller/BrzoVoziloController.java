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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ISAtim4.WebAppSpringAirport.domain.BrzaSoba;
import com.ISAtim4.WebAppSpringAirport.domain.BrzoVozilo;
import com.ISAtim4.WebAppSpringAirport.domain.RegistrovaniKorisnik;
import com.ISAtim4.WebAppSpringAirport.domain.Sediste;
import com.ISAtim4.WebAppSpringAirport.domain.Vozilo;
import com.ISAtim4.WebAppSpringAirport.dto.BrzaRezervacijaDTO;
import com.ISAtim4.WebAppSpringAirport.dto.BrzaSobaVoziloDTO;
import com.ISAtim4.WebAppSpringAirport.dto.SobaPretragaDTO;
import com.ISAtim4.WebAppSpringAirport.dto.VoziloPretragaDTO;
import com.ISAtim4.WebAppSpringAirport.service.BrzoVoziloService;
import com.ISAtim4.WebAppSpringAirport.service.KorisnikService;
import com.ISAtim4.WebAppSpringAirport.service.SedisteService;
import com.ISAtim4.WebAppSpringAirport.service.VoziloService;

@RestController
public class BrzoVoziloController {
	@Autowired
	private BrzoVoziloService brzoVoziloService;
	
	@Autowired
	private VoziloService voziloService;
	
	@Autowired
	KorisnikService korisnikService;
	
	@Autowired
	SedisteService sedisteService;
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping(value = "/api/brzaVozila/reserve", produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public BrzoVozilo rezervisanjeBrzeVozila(@Valid @RequestBody BrzaSobaVoziloDTO brzoVoziloDTO, Principal user) {
		BrzoVozilo brzoVozilo=brzoVoziloService.findOne(brzoVoziloDTO.getBrz_id());
		RegistrovaniKorisnik me=(RegistrovaniKorisnik) korisnikService.findByKorisnickoIme(user.getName());
		brzoVozilo.setPutnik(me);
		brzoVozilo.setDatumRezervacije(new Date());
		brzoVozilo.setZauzeto(true);
		Sediste sediste =  sedisteService.findOneByLetRowCol(brzoVoziloDTO.getLet_id(), brzoVoziloDTO.getRow_col());
		sediste.setRezervisano(true);
		brzoVozilo.setSediste(sediste);
		
		return brzoVoziloService.save(brzoVozilo);
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping(value = "/api/brzaVozila/{id}", produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public List<BrzoVozilo> getBrzeSobeHotela(@PathVariable(value = "id") Long rentId, @Valid @RequestBody VoziloPretragaDTO voziloDTO) {
		//Hotel hotel=hotelService.findOne(hotelId);
		Date datum1=voziloDTO.getVreme1();
		Calendar cal =Calendar.getInstance();
		cal.setTime(datum1);
		cal.add(Calendar.DATE, voziloDTO.getBrojDana());
		Date datum2=cal.getTime();
		List<BrzoVozilo> sve=brzoVoziloService.findAll();
		List<BrzoVozilo> lista=brzoVoziloService.getBrzaVozila(rentId, datum1, datum2);
		
		return lista;
	}
	
	@PreAuthorize("hasRole('ROLE_RENT')")
	@RequestMapping(value = "/api/quick/car", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public BrzoVozilo createBrzoVozilo(@Valid @RequestBody BrzaRezervacijaDTO brzoVoziloDTO) {
		return brzoVoziloService.create(brzoVoziloDTO);
	}

	/* da uzmemo sve usluge, svima dozvoljeno */
	@RequestMapping(value = "/api/quick/car", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<BrzoVozilo> getAllBrzaVozila() {
		return brzoVoziloService.findAll();
	}
	
	
	/* da uzmemo uslugu po id-u, svima dozvoljeno */
	@RequestMapping(value = "/api/quick/car/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BrzoVozilo> getBrzoVozilo(
			@PathVariable(value = "id") Long brzoVoziloId) {
		BrzoVozilo brzoVozilo = brzoVoziloService.findOne(brzoVoziloId);

		if (brzoVozilo == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(brzoVozilo);
	}

	/* update usluge po id-u */
	@PreAuthorize("hasRole('ROLE_RENT')")
	@RequestMapping(value = "/api/quick/car/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BrzoVozilo> updateBrzoVozilo(
			@PathVariable(value = "id") Long brzoVoziloId,
			@Valid @RequestBody BrzoVozilo brzoVoziloDetalji) {

		BrzoVozilo brzoVozilo = brzoVoziloService.findOne(brzoVoziloId);
		if (brzoVozilo == null) {
			return ResponseEntity.notFound().build();
		}

		BrzoVozilo updateBrzoVozilo = brzoVoziloService.save(brzoVozilo);
		
		return ResponseEntity.ok().body(updateBrzoVozilo);
	}

	/* brisanje usluge */
	@PreAuthorize("hasRole('ROLE_RENT')")
	@RequestMapping(value = "/api/quick/car/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BrzoVozilo> deleteBrzoVozilo(
			@PathVariable(value = "id") Long brzoVoziloId) {
		BrzoVozilo brzoVozilo = brzoVoziloService.findOne(brzoVoziloId);

		if (brzoVozilo != null) {
			brzoVoziloService.remove(brzoVoziloId);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
