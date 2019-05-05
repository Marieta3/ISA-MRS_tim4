package com.ISAtim4.WebAppSpringAirport.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.ISAtim4.WebAppSpringAirport.domain.AvioKompanija;
import com.ISAtim4.WebAppSpringAirport.service.AvioKompanijaService;

@RestController
public class AvioKompanijaController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AvioKompanijaService aviokompanijaService;

	/* da snimimo avioKompaniju */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/api/avioKompanije", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public AvioKompanija createAvioKompanija(@Valid @RequestBody AvioKompanija avioKompanija) {
		return aviokompanijaService.save(avioKompanija);
	}

	/* da uzmemo sve avioKompanije, svima dozvoljeno */
	@RequestMapping(value = "/api/avioKompanije", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AvioKompanija> getAllAvioKompanije() {
		return aviokompanijaService.findAll();
	}

	/* da uzmemo avioKompaniju po id-u, svima dozvoljeno*/
	@RequestMapping(value = "/api/avioKompanije/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AvioKompanija> getAvioKompanija(
			@PathVariable(value = "id") Long idAviokompanije) {
		AvioKompanija aviokompanija = aviokompanijaService.findOne(idAviokompanije);

		if (aviokompanija == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(aviokompanija);
	}
	
	/* da uzmemo avioKompaniju po nazivu, svima dozvoljeno */
	
	@RequestMapping(value = "/api/avioKompanije/search/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AvioKompanija>> getAvioKompanijaByName(
			@PathVariable(value = "name") String avioKompanijaName) {
		List<AvioKompanija> avioKompanije = aviokompanijaService.containsName(avioKompanijaName);

		if (avioKompanije == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(avioKompanije);
	}

	/* update avioKompanije po id-u */
	@PreAuthorize("hasAnyRole('ROLE_AVIO', 'ROLE_ADMIN')")
	@RequestMapping(value = "/api/avioKompanije/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AvioKompanija> updateAviokompanije(
			@PathVariable(value = "id") Long aviokompanijaId,
			@Valid @RequestBody AvioKompanija avioKompanijaDetalji) {

		AvioKompanija avioKompanija = aviokompanijaService.findOne(aviokompanijaId);
		if (avioKompanija == null) {
			return ResponseEntity.notFound().build();
		}

		avioKompanija.setNaziv(avioKompanijaDetalji.getNaziv());
		avioKompanija.setAdmin(avioKompanijaDetalji.getAdmin());
		avioKompanija.setAdresa(avioKompanijaDetalji.getAdresa());
		avioKompanija.setOpis(avioKompanijaDetalji.getOpis());
		avioKompanija.setSlika(avioKompanijaDetalji.getSlika());
		AvioKompanija updateAviokompanija = aviokompanijaService.save(avioKompanija);
		return ResponseEntity.ok().body(updateAviokompanija);
	}

	/* brisanje avioKompanije */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/api/avioKompanije/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AvioKompanija> deleteAviokompanije(
			@PathVariable(value = "id") Long avioKompanijaId) {
		AvioKompanija aviokompanija = aviokompanijaService.findOne(avioKompanijaId);

		if (aviokompanija != null) {
			aviokompanijaService.remove(avioKompanijaId);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
