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

import com.ISAtim4.WebAppSpringAirport.domain.BrzoSediste;
import com.ISAtim4.WebAppSpringAirport.domain.Sediste;
import com.ISAtim4.WebAppSpringAirport.dto.BrzaRezervacijaDTO;
import com.ISAtim4.WebAppSpringAirport.service.BrzoSedisteService;
import com.ISAtim4.WebAppSpringAirport.service.SedisteService;

@RestController
public class BrzoSedisteController {
	@Autowired
	private BrzoSedisteService brzoSedisteService;

	@Autowired
	private SedisteService sedisteService;

	@PreAuthorize("hasRole('ROLE_AVIO')")
	@RequestMapping(value = "/api/quick/seat", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public BrzoSediste createBrzoSediste(@Valid @RequestBody BrzaRezervacijaDTO brzoSedisteDTO) {
		
		return brzoSedisteService.create(brzoSedisteDTO);
	}

	/* da uzmemo sve usluge, svima dozvoljeno */
	@RequestMapping(value = "/api/quick/seat", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<BrzoSediste> getAllBrzaVozila() {
		return brzoSedisteService.findAll();
	}

	/* da uzmemo uslugu po id-u, svima dozvoljeno */
	@RequestMapping(value = "/api/quick/seat/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BrzoSediste> getBrzoSediste(@PathVariable(value = "id") Long brzoSedisteId) {
		BrzoSediste brzoSediste = brzoSedisteService.findOne(brzoSedisteId);

		if (brzoSediste == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(brzoSediste);
	}

	/* update usluge po id-u */
	@PreAuthorize("hasRole('ROLE_AVIO')")
	@RequestMapping(value = "/api/quick/seat/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BrzoSediste> updateBrzoSediste(@PathVariable(value = "id") Long brzoSedisteId,
			@Valid @RequestBody BrzoSediste brzoSedisteDetalji) {

		BrzoSediste brzoSediste = brzoSedisteService.findOne(brzoSedisteId);
		if (brzoSediste == null) {
			return ResponseEntity.notFound().build();
		}

		BrzoSediste updateBrzoSediste = brzoSedisteService.save(brzoSediste);

		return ResponseEntity.ok().body(updateBrzoSediste);
	}

	/* brisanje usluge */
	@PreAuthorize("hasRole('ROLE_AVIO')")
	@RequestMapping(value = "/api/quick/seat/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BrzoSediste> deleteBrzoSediste(@PathVariable(value = "id") Long brzoSedisteId) {
		BrzoSediste brzoSediste = brzoSedisteService.findOne(brzoSedisteId);

		if (brzoSediste != null) {
			brzoSedisteService.remove(brzoSedisteId);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
