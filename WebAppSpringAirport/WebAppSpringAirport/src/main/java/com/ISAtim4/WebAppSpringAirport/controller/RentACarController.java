package com.ISAtim4.WebAppSpringAirport.controller;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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

import com.ISAtim4.WebAppSpringAirport.domain.Filijala;
import com.ISAtim4.WebAppSpringAirport.domain.Ocena;
import com.ISAtim4.WebAppSpringAirport.domain.RentACar;
import com.ISAtim4.WebAppSpringAirport.domain.Rezervacija;
import com.ISAtim4.WebAppSpringAirport.domain.Vozilo;
import com.ISAtim4.WebAppSpringAirport.dto.RentAcarDTO;
import com.ISAtim4.WebAppSpringAirport.dto.Chart1DTO;
import com.ISAtim4.WebAppSpringAirport.dto.Chart2DTO;
import com.ISAtim4.WebAppSpringAirport.service.FilijalaService;
import com.ISAtim4.WebAppSpringAirport.service.OcenaService;
import com.ISAtim4.WebAppSpringAirport.service.RentACarService;
import com.ISAtim4.WebAppSpringAirport.service.RezervacijaService;
import com.ISAtim4.WebAppSpringAirport.service.VoziloService;

@RestController
public class RentACarController {
	//private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RentACarService rentACarService;
	
	@Autowired
	private VoziloService voziloService;
	
	@Autowired
	private FilijalaService filijalaService;

	@Autowired
	private RezervacijaService rezervacijaService;
	
	@Autowired
	private OcenaService ocenaService;
	
	/* da snimimo RentAcar */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/api/rentACars", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public RentACar createRentAcar(@Valid @RequestBody RentACar rentACar) {
		rentACar.setCoord1(31.214535);
		rentACar.setCoord2(29.945663);
		return rentACarService.save(rentACar);
	}

	//za PRETRAGU rentacar
	@RequestMapping(value = "/api/rentACars/pretraga", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public List<RentACar> pretragaRentAcar(@Valid @RequestBody RentAcarDTO rent) {
		if (rent.getTipPretrage().equals("location")){
			//pretraga po lokaciji
			List<RentACar> rents =  rentACarService.searchRentsLocation(rent.getLokNaziv(),rent.getDatumPolaska(),rent.getDatumDolaska());
			for (RentACar r : rents) {
				List<Ocena> ocene = ocenaService.findAllByRent(r);
				r.setOcena(Ocena.getProsek(ocene));
			}
			return rents;
		} else {
			//pretraga po nazivu hotela
			List<RentACar> rents = rentACarService.searchRentsName(rent.getLokNaziv(),rent.getDatumPolaska(),rent.getDatumDolaska());
			for (RentACar r : rents) {
				List<Ocena> ocene = ocenaService.findAllByRent(r);
				r.setOcena(Ocena.getProsek(ocene));
			}
			return rents;
		}
	}

	
	
	/* da uzmemo sve RentAcar, svima dozvoljeno */
	
	@RequestMapping(value = "/api/rentACars", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<RentACar> getAllRentAcar() {
		List<RentACar> rents = rentACarService.findAll();
		for (RentACar r : rents) {
			List<Ocena> ocene = ocenaService.findAllByRent(r);
			r.setOcena(Ocena.getProsek(ocene));
		}
		return rents;
		
	}

	/* da uzmemo RentAcar po id-u, svima dozvoljeno */
	@RequestMapping(value = "/api/rentACars/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentACar> getRentAcar(
			@PathVariable(value = "id") Long rentAcarId) {
		RentACar rentACar = rentACarService.findOne(rentAcarId);
		
		if (rentACar == null) {
			return ResponseEntity.notFound().build();
		}
		
		List<Ocena> ocene = ocenaService.findAllByRent(rentACar);
		rentACar.setOcena(Ocena.getProsek(ocene));
	

		return ResponseEntity.ok().body(rentACar);
	}
	

	@PreAuthorize("hasRole('ROLE_RENT')")
	@RequestMapping(value = "/api/rentACars/chart1/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Chart1DTO> getChart1(
			@PathVariable(value = "id") Long rentAcarId) {
		RentACar rentACar = rentACarService.findOne(rentAcarId);
		
		if (rentACar == null) {
			return ResponseEntity.notFound().build();
		}
		
		List<Ocena> ocene = ocenaService.findAllByRent(rentACar);
		rentACar.setOcena(Ocena.getProsek(ocene));
		
		
		double sum = 0.0;
		int i = 0;
		
		List<RentACar> rents = rentACarService.findAll();
		for (RentACar r : rents) {
			List<Ocena> oceneR = ocenaService.findAllByRent(r);
			r.setOcena(Ocena.getProsek(oceneR));
			if(r.getOcena()!= 0.0)
			{
				++i;
				sum += r.getOcena();
			}
		}
		double avg = 0.0;
		if(i != 0){
			avg =  sum/i;
		}
		
		Chart1DTO retval = new Chart1DTO();
		retval.setService(rentACar.getNaziv());
		retval.setServiceRating(rentACar.getOcena());
		retval.setOthers("Services average");
		retval.setOthersRating(avg);
		
		return ResponseEntity.ok().body(retval);
	}
	
	@PreAuthorize("hasRole('ROLE_RENT')")
	@RequestMapping(value = "/api/rentACars/chart2/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Chart2DTO>> getChart2(
			@PathVariable(value = "id") Long rentAcarId) {
		RentACar rent = rentACarService.findOne(rentAcarId);
		List<Filijala> filijale =  filijalaService.findAllByRentACar(rent);

		List<Chart2DTO> retVal = new ArrayList<>();
		
		double sum = 0.0;
		int i = 0;
		for (Filijala filijala : filijale) {
			Set<Vozilo> carList =  filijala.getVozila();
			for (Vozilo v : carList) {
				List<Ocena> ocene = ocenaService.findAllByVozilo(v);
				v.setOcena(Ocena.getProsek(ocene));
				if(v.getOcena()!= 0.0)
				{
					++i;
					sum += v.getOcena();
				}
				Chart2DTO r2 = new Chart2DTO();
				r2.setCar(v.getProizvodjac() + " " + v.getModel() + " (" +v.getGodina() + ")" );
				r2.setCarRating(v.getOcena());
				retVal.add(r2);
			}
		}

		double avg = 0.0;
		if(i != 0){
			avg =  sum/i;
		}
		
		retVal.add(new Chart2DTO("Average", avg));
		
		return ResponseEntity.ok().body(retVal);
	}
	
	@PreAuthorize("hasRole('ROLE_RENT')")
	@RequestMapping(value = "/api/rentACars/chart4/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Double>> getChart4(
			@PathVariable(value = "id") Long rentAcarId) {
		RentACar rent = rentACarService.findOne(rentAcarId);
		List<Rezervacija> rezervacije = new ArrayList<>( rezervacijaService.findAll());

		List<Double> retVal = new ArrayList<>();
		for(int i = 0; i<= 11; i++)
		{
			retVal.add(0.0);
		}
		System.out.println(retVal.size());
		

		Calendar currDate = Calendar.getInstance();
		Calendar reserveDate = Calendar.getInstance();

		currDate.setTime(new Date());
		
		for (Rezervacija r : rezervacije) {
			reserveDate.setTime(r.getDatumRezervacije());
			if(currDate.get(Calendar.YEAR) == reserveDate.get(Calendar.YEAR)) {  //gledamo samo ovogodišnje rezervacije 
				if(r.getOdabranaVozila().size()!= 0){	//ako je korisnik rezervisao vozilo
					for (Vozilo v : r.getOdabranaVozila()) {
						if(v.getFilijala().getRentACar().equals(rent))
						{
						    long diff = r.getVoziloZauzetoDo().getTime() - r.getVoziloZauzetoOd().getTime();
						    int brojDana =  (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
						    System.out.println(r.getVoziloZauzetoDo());
						    System.out.println(r.getVoziloZauzetoOd());
						    System.out.println(brojDana);
						    
						    switch (reserveDate.get(Calendar.MONTH)) {
							case 0:
								retVal.set(0, retVal.get(0) + (brojDana * v.getCena() ));
								break;
							case 1:
								retVal.set(1, retVal.get(1) + (brojDana * v.getCena() ));
								break;
							case 2:
								retVal.set(2, retVal.get(2) + (brojDana * v.getCena() ));
								break;
							case 3:
								retVal.set(3, retVal.get(3) + (brojDana * v.getCena() ));
								break;
							case 4:
								retVal.set(4, retVal.get(4) + (brojDana * v.getCena() ));
								break;
							case 5:
								retVal.set(5, retVal.get(5) + (brojDana * v.getCena() ));
								break;
							case 6:
								retVal.set(6, retVal.get(6) + (brojDana * v.getCena() ));
								break;
							case 7:
								retVal.set(7, retVal.get(7) + (brojDana * v.getCena() ));
								break;
							case 8:
								retVal.set(8, retVal.get(8) + (brojDana * v.getCena() ));
								break;
							case 9:
								retVal.set(9, retVal.get(9) + (brojDana * v.getCena() ));
								break;
							case 10:
								retVal.set(10, retVal.get(10) + (brojDana * v.getCena() ));
								break;
							case 11:
								retVal.set(11, retVal.get(11) + (brojDana * v.getCena() ));
								break;
							default:
								break;
							}
							
						}
					}
				}
			}
		}
		
		return ResponseEntity.ok().body(retVal);
	}

	/* da uzmemo RentAcar po nazivu, svima dozvoljeno */
	
	@RequestMapping(value = "/api/rentACars/search/{name}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RentACar>> getRentAcarByName(
			@PathVariable(value = "name") String rentACarName) {
		List<RentACar> rentACars = rentACarService.containsName(rentACarName);

		if (rentACars == null) {
			return ResponseEntity.notFound().build();
		}
		
		for (RentACar r : rentACars) {
			List<Ocena> ocene = ocenaService.findAllByRent(r);
			r.setOcena(Ocena.getProsek(ocene));
		}
		
		return ResponseEntity.ok().body(rentACars);
	}

	/* update RentAcar po id-u */
	@PreAuthorize("hasAnyRole('ROLE_RENT', 'ROLE_ADMIN')")
	@RequestMapping(value = "/api/rentACars/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentACar> updateRentAcar(
			@PathVariable(value = "id") Long rentAcarId,
			@Valid @RequestBody RentACar rentAcarDetalji) {

		RentACar rentACar = rentACarService.findOne(rentAcarId);
		if (rentACar == null) {
			return ResponseEntity.notFound().build();
		}
		rentACar.setNaziv(rentAcarDetalji.getNaziv());
		rentACar.setAdresa(rentAcarDetalji.getAdresa());
		rentACar.setOpis(rentAcarDetalji.getOpis());
		//rentACar.setSlika(rentAcarDetalji.getSlika());
		rentACar.setCoord1(rentAcarDetalji.getCoord1());
		rentACar.setCoord2(rentAcarDetalji.getCoord2());
		RentACar updateRentACar = rentACarService.save(rentACar);
		return ResponseEntity.ok().body(updateRentACar);
	}

	/* brisanje RentAcar */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/api/rentACars/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RentACar> deleteRentAcar(
			@PathVariable(value = "id") Long rentAcarId) {
		RentACar rentACar = rentACarService.findOne(rentAcarId);
		if (rentACar != null) {
			rentACarService.remove(rentAcarId);
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
