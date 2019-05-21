package com.ISAtim4.WebAppSpringAirport.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import com.ISAtim4.WebAppSpringAirport.domain.Let;
import com.ISAtim4.WebAppSpringAirport.domain.Sediste;
import com.ISAtim4.WebAppSpringAirport.dto.LetDTO;
import com.ISAtim4.WebAppSpringAirport.service.LetService;

@RestController
public class LetController {

	@Autowired
	private LetService letService;
	
	/* da snimimo let */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_AVIO')")
	@RequestMapping(value = "/api/let", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public Let createLet(@Valid @RequestBody Let let) {
		let.setBrojRedova(let.getBrojRedovaFC()+let.getBrojRedovaEC()+let.getBrojRedovaBC());
		for(int i = 1; i <= let.getBrojRedovaFC();i++){
			for (int j=1; j<= let.getBrojKolona();j++){
				Sediste s = new Sediste();
				s.setBrojReda(i);
				s.setBrojKolone(j);
				s.setKlasa("f");
				let.getSedista().add(s);
			}
		}
		for(int i = let.getBrojRedovaFC()+1; i <= let.getBrojRedovaFC()+let.getBrojRedovaEC();i++){
			for (int j=1; j<= let.getBrojKolona();j++){
				Sediste s = new Sediste();
				s.setBrojReda(i);
				s.setBrojKolone(j);
				s.setKlasa("e");
				let.getSedista().add(s);
			}
		}
		for(int i = let.getBrojRedovaFC()+let.getBrojRedovaEC()+1; i <= let.getBrojRedova();i++){
			for (int j=1; j<= let.getBrojKolona();j++){
				Sediste s = new Sediste();
				s.setBrojReda(i);
				s.setBrojKolone(j);
				s.setKlasa("b");
				let.getSedista().add(s);
			}
		}
		return letService.save(let);
	}

	/* da uzmemo sve letove, svima dozvoljeno */
	@RequestMapping(value = "/api/let", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Let> getAllLetovi() {
		List<Let> pronadjeni=letService.findAll();
		for(Let let:pronadjeni) {
			if(let.getSedista().isEmpty()) {
				let.setSedista(popuniSedista(let));
			}
		}
		return pronadjeni;
	}
	
	private Set<Sediste> popuniSedista(Let let){
		Set<Sediste> sedista=new HashSet<>();
		let.setBrojRedova(let.getBrojRedovaFC()+let.getBrojRedovaEC()+let.getBrojRedovaBC());
		for(int i = 1; i <= let.getBrojRedovaFC();i++){
			for (int j=1; j<= let.getBrojKolona();j++){
				Sediste s = new Sediste();
				if(i==1 && j>3) {
					s.setRezervisano(true);
				}
				s.setBrojReda(i);
				s.setBrojKolone(j);
				s.setKlasa("f");
				sedista.add(s);
			}
		}
		for(int i = let.getBrojRedovaFC()+1; i <= let.getBrojRedovaFC()+let.getBrojRedovaEC();i++){
			for (int j=1; j<= let.getBrojKolona();j++){
				Sediste s = new Sediste();
				if(i>2 && j%3==0) {
					s.setRezervisano(true);
				}
				s.setBrojReda(i);
				s.setBrojKolone(j);
				s.setKlasa("e");
				sedista.add(s);
			}
		}
		for(int i = let.getBrojRedovaFC()+let.getBrojRedovaEC()+1; i <= let.getBrojRedova();i++){
			for (int j=1; j<= let.getBrojKolona();j++){
				Sediste s = new Sediste();
				if(i%3==1 && j>=2) {
					s.setRezervisano(true);
				}
				s.setBrojReda(i);
				s.setBrojKolone(j);
				s.setKlasa("b");
				sedista.add(s);
			}
		}
		return sedista;
	}
	/* PRETRAGA letova, svima dozvoljeno */
	@RequestMapping(value = "/api/let/pretraga", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public List<Let> pretragaLetova(@Valid @RequestBody LetDTO let) {
		
		 if (let.getTipPutovanja().equals("oneway")){
			return letService.findFlightsOneWay(let.getMestoPolaska(),let.getMestoDolaska());
		} else {
			//onda je round-trip
			return letService.findFlightsTwoWay(let.getMestoPolaska(),let.getMestoDolaska(),let.getVremePolaska(),
					let.getVremeDolaska());
		}
	}

	/* da uzmemo let po id-u, svima dozvoljeno*/
	@RequestMapping(value = "/api/let/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Let> getLet(
			@PathVariable(value = "id") Long idLeta) {
		Let let = letService.findOne(idLeta);

		if (let == null) {
			return ResponseEntity.notFound().build();
		}
		if(let.getSedista().isEmpty()) {
			let.setSedista(popuniSedista(let));
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
	@RequestMapping(value = "/api/let/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Let> updateLeta(
			@PathVariable(value = "id") Long letId,
			@Valid @RequestBody Let letDetalji) {

		Let let = letService.findOne(letId);
		if (let == null) {
			return ResponseEntity.notFound().build();
		}

		let.setPocetnaDestinacija(letDetalji.getPocetnaDestinacija());
		let.setKrajnjaDestinacija(letDetalji.getKrajnjaDestinacija());
		let.setVremePolaska(letDetalji.getVremePolaska());
		let.setVremeDolaska(letDetalji.getVremeDolaska());
		let.setDuzinaPutovanja(letDetalji.getDuzinaPutovanja());
		
		//ova dva mogu da izazovu problem
		let.setAvio_kompanija(letDetalji.getAvio_kompanija()); 
		let.setSedista(letDetalji.getSedista());
		
		
		let.setModel(letDetalji.getModel());
		let.setBrojRedova(letDetalji.getBrojRedova());
		let.setBrojKolona(letDetalji.getBrojKolona());
		let.setBrojRedovaEC(letDetalji.getBrojRedovaEC());
		let.setBrojRedovaBC(letDetalji.getBrojRedovaBC());
		let.setBrojRedovaFC(letDetalji.getBrojRedovaFC());
		
		Let updateLet = letService.save(let);
		return ResponseEntity.ok().body(updateLet);
	}

	/* brisanje leta */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_AVIO')")
	@RequestMapping(value = "/api/let/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Let> deleteLeta(
			@PathVariable(value = "id") Long letId) {
		Let let = letService.findOne(letId);

		if (let != null) {
			letService.remove(letId);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
