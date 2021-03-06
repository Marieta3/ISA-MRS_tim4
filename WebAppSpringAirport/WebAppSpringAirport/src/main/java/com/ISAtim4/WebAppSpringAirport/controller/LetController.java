package com.ISAtim4.WebAppSpringAirport.controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.ISAtim4.WebAppSpringAirport.domain.AdminAvio;
import com.ISAtim4.WebAppSpringAirport.domain.Let;
import com.ISAtim4.WebAppSpringAirport.domain.Ocena;
import com.ISAtim4.WebAppSpringAirport.domain.Sediste;
import com.ISAtim4.WebAppSpringAirport.dto.LetDTO;
import com.ISAtim4.WebAppSpringAirport.service.KorisnikService;
import com.ISAtim4.WebAppSpringAirport.service.LetService;
import com.ISAtim4.WebAppSpringAirport.service.OcenaService;

@RestController
public class LetController {

	@Autowired
	private LetService letService;

	@Autowired
	private OcenaService ocenaService;
	
	@Autowired
	KorisnikService korisnikService;
	
	
	/* da snimimo let */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_AVIO')")
	@PostMapping(value = "/api/let", produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public Let createLet(@Valid @RequestBody Let let) {
		return letService.create(let);
	}

	/* da uzmemo sve letove, svima dozvoljeno */
	@GetMapping(value = "/api/let",  produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Let> getAllLetovi() {
		List<Let> pronadjeni=letService.findAll();
		/*for(Let let:pronadjeni) {
			if(let.getSedista().isEmpty()) {
				let.setSedista(popuniSedista(let));
				letService.save(let);
			}
		}*/
		for (Let let : pronadjeni) {
			List<Ocena> ocene = ocenaService.findAllByLet(let);
			let.setOcena(Ocena.getProsek(ocene));
		}
		System.out.println("vreme polaska: "+pronadjeni.get(0).getVremePolaska());
		return pronadjeni;
	}
	
	
	/* PRETRAGA letova, svima dozvoljeno */
	@PostMapping(value = "/api/let/pretraga", produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public List<Let> pretragaLetova(@Valid @RequestBody LetDTO let) {
		
		 if (let.getTipPutovanja().equals("oneway")){
			return letService.findFlightsOneWay(let.getMestoPolaska(),let.getMestoDolaska(), let.getVreme1(), let.getVreme2());
		} else {
			//onda je round-trip
			return letService.findFlightsTwoWay(let.getMestoPolaska(),let.getMestoDolaska(),let.getVreme1(),
					let.getVreme2());
		}
	}
	
	@PostMapping(value = "/api/let/pretraga/{id}", produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public List<Let> pretragaLetova(@PathVariable(value = "id") Long id,
			@Valid @RequestBody LetDTO let) {
		return letService.findFlightsByAvio(let.getMestoPolaska(),let.getMestoDolaska(), let.getVreme1(), let.getVreme2(), id);
	}

	/* da uzmemo let po id-u, svima dozvoljeno*/
	@GetMapping(value = "/api/let/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Let> getLet(
			@PathVariable(value = "id") Long idLeta) {
		Let let = letService.getLet(idLeta);

		if (let == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok().body(let);
	}
	
	/* da uzmemo avion po nazivu, svima dozvoljeno */
	/*
	@RequestMapping(value = "/api/avion/search/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Avion>> getAvionByName(
			@PathVariable(value = "name") String avionName) {
		List<Avion> avion = LetService.containsName(avionName);

		if (avion == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(avion);
	}*/

	/* update leta po id-u */
	@PreAuthorize("hasAnyRole('ROLE_AVIO', 'ROLE_ADMIN')")
	@PutMapping(value = "/api/let/{id}",  produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Let> updateLeta(
			@PathVariable(value = "id") Long letId,
			@Valid @RequestBody Let letDetalji) {

		Let let = letService.update(letId, letDetalji);
		if (let == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok().body(let);
	}

	/* brisanje leta */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_AVIO')")
	@DeleteMapping(value = "/api/let/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Let> deleteLeta(
			@PathVariable(value = "id") Long letId) {

		if (letService.delete(letId)) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PreAuthorize("hasRole('ROLE_AVIO')")
	@GetMapping(value = "/api/letoviKompanije",  produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Let> getLetoviKompanije(Principal user) {
		AdminAvio me = null;

		if (user != null) {
			me = (AdminAvio) this.korisnikService.findByKorisnickoIme(user.getName());
		}
		List<Let> letovi =  letService.findAllByAvioKompanija(me.getAvio_kompanija());
		
		for (Let let : letovi) {
			List<Ocena> ocene = ocenaService.findAllByLet(let);
			let.setOcena(Ocena.getProsek(ocene));
		}
		
		return letovi;
	}
}
