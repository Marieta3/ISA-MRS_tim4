package com.ISAtim4.WebAppSpringAirport.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ISAtim4.WebAppSpringAirport.domain.BrzoVozilo;
import com.ISAtim4.WebAppSpringAirport.domain.Vozilo;
import com.ISAtim4.WebAppSpringAirport.dto.BrzaRezervacijaDTO;
import com.ISAtim4.WebAppSpringAirport.service.BrzoVoziloService;
import com.ISAtim4.WebAppSpringAirport.service.VoziloService;

@RestController
public class BrzoVoziloController {
	@Autowired
	private BrzoVoziloService brzoVoziloService;
	
	@Autowired
	private VoziloService voziloService;
	
	@PreAuthorize("hasRole('ROLE_RENT')")
	@RequestMapping(value = "/api/quick/car", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public BrzoVozilo createBrzoVozilo(@Valid @RequestBody BrzaRezervacijaDTO brzoVoziloDTO) {
		BrzoVozilo brzoVozilo=new BrzoVozilo();
		brzoVozilo.setNova_cena(brzoVoziloDTO.getNovaCena());
		brzoVozilo.setStart_date(brzoVoziloDTO.getStartDatum());
		brzoVozilo.setEnd_date(brzoVoziloDTO.getEndDatum());
		Vozilo vozilo=voziloService.findOne(brzoVoziloDTO.getId());
		//TODO: ubaciti proveru da li je mozda rezervisano u tom periodu
		brzoVozilo.setVozilo(vozilo);
		return brzoVoziloService.save(brzoVozilo);
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
