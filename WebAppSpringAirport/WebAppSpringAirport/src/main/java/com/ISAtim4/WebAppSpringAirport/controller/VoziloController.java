package com.ISAtim4.WebAppSpringAirport.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.ISAtim4.WebAppSpringAirport.domain.Filijala;
import com.ISAtim4.WebAppSpringAirport.domain.Ocena;
import com.ISAtim4.WebAppSpringAirport.domain.RentACar;
import com.ISAtim4.WebAppSpringAirport.domain.Rezervacija;
import com.ISAtim4.WebAppSpringAirport.domain.Vozilo;
import com.ISAtim4.WebAppSpringAirport.dto.VoziloDTO;
import com.ISAtim4.WebAppSpringAirport.dto.VoziloPretragaDTO;
import com.ISAtim4.WebAppSpringAirport.service.FilijalaService;
import com.ISAtim4.WebAppSpringAirport.service.OcenaService;
import com.ISAtim4.WebAppSpringAirport.service.RentACarService;
import com.ISAtim4.WebAppSpringAirport.service.RezervacijaService;
import com.ISAtim4.WebAppSpringAirport.service.VoziloService;

@RestController
public class VoziloController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private VoziloService voziloService;

	@Autowired
	private RezervacijaService rezervacijaService;

	@Autowired
	private FilijalaService filijalaService;

	@Autowired
	private RentACarService rentService;

	@Autowired
	private OcenaService ocenaService;

	/* da snimimo vozilo */
	@PreAuthorize("hasRole('ROLE_RENT')")
	@PostMapping(value = "/api/cars", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Vozilo createCar(@Valid @RequestBody VoziloDTO v) {
		Vozilo vozilo = new Vozilo();
		vozilo.setProizvodjac(v.getProizvodjac());
		vozilo.setModel(v.getModel());
		vozilo.setGodina(v.getGodina());
		vozilo.setTablica(v.getTablica());
		vozilo.setCena(v.getCena());
		vozilo.setBrojMesta(v.getBrojMesta());
		vozilo.setFilijala(filijalaService.findOne(v.getFilijala_id()));
		return voziloService.save(vozilo);
	}

	/* da uzmemo sve vozila, svima dozvoljeno */
	@GetMapping(value = "/api/cars", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Vozilo> getAllCars() {
		List<Vozilo> cars = voziloService.findAll();
		for (Vozilo vozilo : cars) {
			List<Ocena> ocene = ocenaService.findAllByVozilo(vozilo);
			vozilo.setOcena(Ocena.getProsek(ocene));
		}
		return cars;
	}

	/* da uzmemo vozilo po id-u, svima dozvoljeno */
	@GetMapping(value = "/api/cars/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Vozilo> getCar(
			@PathVariable(value = "id") Long hotelId) {
		Vozilo vozilo = voziloService.findOne(hotelId);

		if (vozilo == null) {
			return ResponseEntity.notFound().build();
		}
		List<Ocena> ocene = ocenaService.findAllByVozilo(vozilo);
		vozilo.setOcena(Ocena.getProsek(ocene));
		return ResponseEntity.ok().body(vozilo);
	}

	/* da uzmemo sve vozila za neki rent-a-car, svima dozvoljeno */
	@GetMapping(value = "/api/cars/rent/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Vozilo> getAllCarsByRentACar(
			@PathVariable(value = "id") Long rentId) {
		logger.info("ID je " + rentId);
		RentACar rent = rentService.findOne(rentId);

		List<Filijala> filijale = filijalaService.findAllByRentACar(rent);

		List<Vozilo> cars = new ArrayList<Vozilo>();

		for (Filijala filijala : filijale) {
			Set<Vozilo> carList = filijala.getVozila();
			for (Vozilo v : carList) {
				cars.add(v);
				List<Ocena> ocene = ocenaService.findAllByVozilo(v);
				v.setOcena(Ocena.getProsek(ocene));
			}
		}

		return cars;
	}

	@PostMapping(value = "/api/voziloRent/pretraga/{rent_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Vozilo> getVozilaByRACPretraga(
			@PathVariable(value = "rent_id") Long rent_id,
			@Valid @RequestBody VoziloPretragaDTO voziloDTO) {

		RentACar rentAC = rentService.findOne(rent_id);
		List<Filijala> filijale = filijalaService.findAllByRentACar(rentAC);

		List<Rezervacija> rezervacije = rezervacijaService.findAll();
		ArrayList<Vozilo> ne_moze = new ArrayList<>();
		ArrayList<Vozilo> pronadjene = new ArrayList<>();
		Date datum1 = voziloDTO.getVreme1();

		Date datum2 = voziloDTO.getVreme2();
		System.out.println("\n\n\t" + datum2);
		// za svaku rezervaciju
		for (Rezervacija r : rezervacije) {
			// ako se preklapaju datumi
			// if v1 <= datum1 and v2 >= datum1
			if ((r.getVoziloZauzetoOd().compareTo(datum1) <= 0 && r
					.getVoziloZauzetoDo().compareTo(datum1) >= 0)
			// if v1 <= datum2 and v2 >= datum2
					|| (r.getVoziloZauzetoOd().compareTo(datum2) <= 0 && r
							.getVoziloZauzetoDo().compareTo(datum2) >= 0)
					// v1 >= datum1 and v2 <= datum2
					|| (r.getVoziloZauzetoOd().compareTo(datum1) >= 0 && r
							.getVoziloZauzetoDo().compareTo(datum2) <= 0)) {
				System.out
						.println("+++---***\n\n\t broj vozila u rezervaciji: "
								+ r.getOdabraneSobe().size());
				// za svako vozilo u rezervaciji
				for (Vozilo v : r.getOdabranaVozila()) {
					// ako soba pripada trazenom hotelu
					if (v.getFilijala().getRentACar().equals(rentAC)) {
						System.out.println("------------to je taj RAC "
								+ rentAC.getNaziv());
						// ako jos nije dodata u listu zauzetih
						if (!ne_moze.contains(v)) {
							// dodaj u listu zauzetih
							ne_moze.add(v);
						}
					}
				}
			}
		}
		System.out.println("broj rezervisanih soba: " + ne_moze.size());
		for (Filijala filijala : filijale) {
			Set<Vozilo> carList = filijala.getVozila();
			for (Vozilo v : carList) {
				List<Ocena> ocene = ocenaService.findAllByVozilo(v);
				v.setOcena(Ocena.getProsek(ocene));

				// ako se ne nalazi u listi zauzetih
				if (!ne_moze.contains(v)) {
					// dodaj u listu pronadjenih
					pronadjene.add(v);
				}
			}
		}
		return pronadjene;

	}

	/* update vozila po id-u */
	@PreAuthorize("hasRole('ROLE_RENT')")
	@PutMapping(value = "/api/cars/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Vozilo> updateCar(
			@PathVariable(value = "id") Long carId,
			@Valid @RequestBody VoziloDTO voziloDetalji) {

		Vozilo vozilo = voziloService.findOne(carId);
		if (vozilo == null) {
			return ResponseEntity.notFound().build();
		} else {
			ArrayList<Rezervacija> aktivne = (ArrayList<Rezervacija>) rezervacijaService
					.findAll();
			for (Rezervacija rezervacija : aktivne) {
				if (rezervacija.getAktivnaRezervacija()) {
					Set<Vozilo> v = rezervacija.getOdabranaVozila();
					for (Vozilo vozilo2 : v) {
						if (vozilo2.getId() == vozilo.getId()) {
							return new ResponseEntity<>(HttpStatus.FORBIDDEN);
						}
					}
				}
			}
		}

		vozilo.setProizvodjac(voziloDetalji.getProizvodjac());
		vozilo.setModel(voziloDetalji.getModel());
		vozilo.setGodina(voziloDetalji.getGodina());
		vozilo.setTablica(voziloDetalji.getTablica());
		vozilo.setCena(voziloDetalji.getCena());
		vozilo.setBrojMesta(voziloDetalji.getBrojMesta());

		Vozilo updateVozilo = voziloService.save(vozilo);
		return ResponseEntity.ok().body(updateVozilo);
	}

	/* brisanje vozila */
	@PreAuthorize("hasRole('ROLE_RENT')")
	@DeleteMapping(value = "/api/cars/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> deleteCar(
			@PathVariable(value = "id") Long carId) {
		/*
		 * Vozilo vozilo = voziloService.findOne(carId);
		 * 
		 * if (vozilo != null) { ArrayList<Rezervacija> aktivne =
		 * (ArrayList<Rezervacija>) rezervacijaService.findAll(); for
		 * (Rezervacija rezervacija : aktivne) {
		 * if(rezervacija.getAktivnaRezervacija()){ Set<Vozilo> v=
		 * rezervacija.getOdabranaVozila(); for (Vozilo vozilo2 : v) {
		 * if(vozilo2.getId() == vozilo.getId()){ return new
		 * ResponseEntity<>(HttpStatus.FORBIDDEN); } } } }
		 * voziloService.remove(carId); return new
		 * ResponseEntity<>(HttpStatus.OK); } else { return new
		 * ResponseEntity<>(HttpStatus.NOT_FOUND);
		 */
		boolean success = voziloService.remove(carId);
		if (success) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}

	}
}
