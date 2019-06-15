package com.ISAtim4.WebAppSpringAirport.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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
import com.ISAtim4.WebAppSpringAirport.domain.Let;
import com.ISAtim4.WebAppSpringAirport.domain.Ocena;
import com.ISAtim4.WebAppSpringAirport.dto.LetDTO;
import com.ISAtim4.WebAppSpringAirport.service.AvioKompanijaService;
import com.ISAtim4.WebAppSpringAirport.service.OcenaService;

@RestController
public class AvioKompanijaController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AvioKompanijaService aviokompanijaService;
	
	@Autowired
	private OcenaService ocenaService;

	/* da snimimo avioKompaniju */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/api/avioKompanije", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public AvioKompanija createAvioKompanija(@Valid @RequestBody AvioKompanija avioKompanija) {
		avioKompanija.setCoord1(31.214535);
		avioKompanija.setCoord2(29.945663);
		return aviokompanijaService.save(avioKompanija);
	}

	/* da uzmemo sve avioKompanije, svima dozvoljeno */
	@RequestMapping(value = "/api/avioKompanije", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AvioKompanija> getAllAvioKompanije() {
		List<AvioKompanija> avios = aviokompanijaService.findAll();
		for (AvioKompanija a : avios) {
			List<Ocena> ocene = ocenaService.findAllByAvio(a);
			a.setOcena(Ocena.getProsek(ocene));
		}
		return avios;
	}

	/* da uzmemo avioKompaniju po id-u, svima dozvoljeno*/
	@RequestMapping(value = "/api/avioKompanije/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AvioKompanija> getAvioKompanija(
			@PathVariable(value = "id") Long idAviokompanije) {
		AvioKompanija aviokompanija = aviokompanijaService.findOne(idAviokompanije);

		if (aviokompanija == null) {
			return ResponseEntity.notFound().build();
		}
		List<Ocena> ocene = ocenaService.findAllByAvio(aviokompanija);
		aviokompanija.setOcena(Ocena.getProsek(ocene));
		
		return ResponseEntity.ok().body(aviokompanija);
	}
	
	/* da uzmemo letove po id-u aviokompanije, svima dozvoljeno*/
	@RequestMapping(value = "/api/avioKompanije/flights/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Let>> getFlightsOfAvioKompanija(
			@PathVariable(value = "id") Long idAviokompanije) throws ParseException {
		AvioKompanija aviokompanija = aviokompanijaService.findOne(idAviokompanije);

		if (aviokompanija == null) {
			return ResponseEntity.notFound().build();
		}
		
		//ocene
		ArrayList<Let> listaLetova = new ArrayList<>();
		
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		for (Let let : aviokompanija.getListaLetova()) {
			List<Ocena> ocene = ocenaService.findAllByLet(let);
			let.setOcena(Ocena.getProsek(ocene));
			listaLetova.add(let);
		}
		return ResponseEntity.ok().body(listaLetova);
	}
	
	/* pretraga letova start, end, startdate, enddate*/
	@RequestMapping(value = "/api/avioKompanije/searchFlights/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Let>> pretragaLetovaAvioKompanija(
			@PathVariable(value = "id") Long idAviokompanije,
			@Valid @RequestBody LetDTO sLet) {
		AvioKompanija aviokompanija = aviokompanijaService.findOne(idAviokompanije);
		
		if (aviokompanija == null) {
			return ResponseEntity.notFound().build();
		}
		
		ArrayList<Let> listaLetova = new ArrayList<>();
		String start,end;
		Date startDate, endDate;
		start = sLet.getMestoPolaska();
		end = sLet.getMestoDolaska();
		startDate = sLet.getVreme1();
		endDate = sLet.getVreme2();
		System.out.println(start + end + startDate.toString() + endDate.toString());
		if(!endDate.after(startDate)){
			return ResponseEntity.badRequest().build();
		}
		
		for (Let let : aviokompanija.getListaLetova()) {
			if(let.getPocetnaDestinacija().trim().toLowerCase().contains(start.trim().toLowerCase()) &&
					let.getKrajnjaDestinacija().trim().toLowerCase().contains(end.trim().toLowerCase())){
				if((let.getVremePolaska().after(startDate)&&let.getVremePolaska().before(endDate))||
					(let.getVremeDolaska().after(startDate)&&let.getVremePolaska().before(endDate))	)
				{
					listaLetova.add(let);
				}
			}
		}
		return ResponseEntity.ok().body(listaLetova);
	}
	
	/* da uzmemo avioKompaniju po nazivu, svima dozvoljeno */
	
	@RequestMapping(value = "/api/avioKompanije/search/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AvioKompanija>> getAvioKompanijaByName(
			@PathVariable(value = "name") String avioKompanijaName) {
		List<AvioKompanija> avioKompanije = aviokompanijaService.containsName(avioKompanijaName);

		if (avioKompanije == null) {
			return ResponseEntity.notFound().build();
		}
		for (AvioKompanija a : avioKompanije) {
			List<Ocena> ocene = ocenaService.findAllByAvio(a);
			a.setOcena(Ocena.getProsek(ocene));
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
		avioKompanija.setCoord1(avioKompanijaDetalji.getCoord1());
		avioKompanija.setCoord2(avioKompanijaDetalji.getCoord2());
		
		List<Ocena> ocene = ocenaService.findAllByAvio(avioKompanija);
		avioKompanija.setOcena(Ocena.getProsek(ocene));
		
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
			logger.info("Brisanje uspesan!");
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
