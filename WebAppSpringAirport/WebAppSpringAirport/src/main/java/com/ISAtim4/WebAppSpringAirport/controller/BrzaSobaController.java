package com.ISAtim4.WebAppSpringAirport.controller;


import java.util.List;

import javax.validation.Valid;

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

import com.ISAtim4.WebAppSpringAirport.domain.BrzaSoba;
import com.ISAtim4.WebAppSpringAirport.domain.Soba;
import com.ISAtim4.WebAppSpringAirport.dto.BrzaSobaDTO;
import com.ISAtim4.WebAppSpringAirport.service.BrzaSobaService;
import com.ISAtim4.WebAppSpringAirport.service.SobaService;

@RestController
public class BrzaSobaController {
	@Autowired
	private BrzaSobaService brzaSobaService;
	
	@Autowired
	private SobaService sobaService;
	
	@PreAuthorize("hasRole('ROLE_HOTEL')")
	@PostMapping(value = "/api/quick/room",  produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public BrzaSoba createBrzaSoba(@Valid @RequestBody BrzaSobaDTO brzaSobaDTO) {
		BrzaSoba brzaSoba=new BrzaSoba();
		brzaSoba.setNova_cena(brzaSobaDTO.getNovaCena());
		brzaSoba.setStart_date(brzaSobaDTO.getStartDatum());
		brzaSoba.setEnd_date(brzaSobaDTO.getEndDatum());
		Soba soba=sobaService.findOne(brzaSobaDTO.getIdSobe());
		//TODO: ubaciti proveru da li je mozda rezervisana u top periodu
		brzaSoba.setSoba(soba);
		return brzaSobaService.save(brzaSoba);
	}

	/* da uzmemo sve usluge, svima dozvoljeno */
	@GetMapping(value = "/api/quick/room", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<BrzaSoba> getAllBrzeSobe() {
		return brzaSobaService.findAll();
	}
	
	
	/* da uzmemo uslugu po id-u, svima dozvoljeno */
	@GetMapping(value = "/api/quick/room/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BrzaSoba> getBrzaSoba(
			@PathVariable(value = "id") Long brzaSobaId) {
		BrzaSoba brzaSoba = brzaSobaService.findOne(brzaSobaId);

		if (brzaSoba == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(brzaSoba);
	}

	/* update usluge po id-u */
	@PreAuthorize("hasRole('ROLE_HOTEL')")
	@PutMapping(value = "/api/quick/room/{id}", produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BrzaSoba> updateBrzeSobe(
			@PathVariable(value = "id") Long brzaSobaId,
			@Valid @RequestBody BrzaSoba brzaSobaDetalji) {

		BrzaSoba brzaSoba = brzaSobaService.findOne(brzaSobaId);
		if (brzaSoba == null) {
			return ResponseEntity.notFound().build();
		}

		BrzaSoba updateBrzaSoba = brzaSobaService.save(brzaSoba);
		
		return ResponseEntity.ok().body(updateBrzaSoba);
	}

	/* brisanje usluge */
	@PreAuthorize("hasRole('ROLE_HOTEL')")
	@DeleteMapping(value = "/api/quick/room/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BrzaSoba> deleteBrzaSoba(
			@PathVariable(value = "id") Long brzaSobaId) {
		BrzaSoba brzaSoba = brzaSobaService.findOne(brzaSobaId);

		if (brzaSoba != null) {
			brzaSobaService.remove(brzaSobaId);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
