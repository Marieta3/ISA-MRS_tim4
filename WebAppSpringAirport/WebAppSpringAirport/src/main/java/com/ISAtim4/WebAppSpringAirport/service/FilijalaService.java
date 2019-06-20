package com.ISAtim4.WebAppSpringAirport.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ISAtim4.WebAppSpringAirport.domain.Destinacija;
import com.ISAtim4.WebAppSpringAirport.domain.Filijala;
import com.ISAtim4.WebAppSpringAirport.domain.RentACar;
import com.ISAtim4.WebAppSpringAirport.domain.Rezervacija;
import com.ISAtim4.WebAppSpringAirport.domain.Vozilo;
import com.ISAtim4.WebAppSpringAirport.dto.FilijalaDTO;
import com.ISAtim4.WebAppSpringAirport.repository.FilijalaRepository;

@Service
@Transactional(readOnly = true)
public class FilijalaService {
	@Autowired
	private FilijalaRepository branchRepository;

	@Autowired
	private VoziloService voziloService;
	
	@Autowired
	private RezervacijaService rezervacijaService;
	
	@Autowired
	private RentACarService rentService;
	
	@Transactional(readOnly = false)
	public Filijala save(Filijala branch) {
		return branchRepository.save(branch);
	}

	public Filijala findOne(Long id) {
		return branchRepository.getOne(id);
	}

	public List<Filijala> findAll() {
		return branchRepository.findAll();
	}
	
	public List<Filijala> findAllByRentACar(RentACar r) {
		return branchRepository.findAllByRentACar(r);
	}

	public Page<Filijala> findAll(Pageable page) {
		return branchRepository.findAll(page);
	}

	@Transactional(readOnly = false)
	public void remove(Long id) {
		branchRepository.deleteById(id);
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Filijala create(FilijalaDTO filijalaDTO) {
		Filijala filijala = new Filijala();

		filijala.setAdresa(filijalaDTO.getAdresa());
		filijala.setTelefon(filijalaDTO.getTelefon());
		filijala.setOpis(filijalaDTO.getOpis());
		filijala.setSlika(filijalaDTO.getSlika());

		RentACar rent = rentService.findOne(filijalaDTO.getIdRent());
		filijala.setRentACar(rent);
		return save(filijala);
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Filijala update(Long filijalaId,Filijala filijalaDetalji) {
		Filijala filijala = findOne(filijalaId);
		if (filijala == null) {
			return null;
		}

		filijala.setOpis(filijalaDetalji.getOpis());
		filijala.setAdresa(filijalaDetalji.getAdresa());
		filijala.setTelefon(filijalaDetalji.getTelefon());
		filijala.setSlika(filijalaDetalji.getSlika());

		Filijala updateFilijala = save(filijala);
		return updateFilijala;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public int delete( Long filijalaId) {
		Filijala filijala = findOne(filijalaId);
		ArrayList<Vozilo> carList = (ArrayList<Vozilo>) voziloService.findAll();
		if (filijala != null) {
			ArrayList<Rezervacija> aktivne = (ArrayList<Rezervacija>) rezervacijaService.findAll();
			for (Rezervacija rezervacija : aktivne) {
				if(rezervacija.getAktivnaRezervacija()){
					Set<Vozilo>  v= rezervacija.getOdabranaVozila();
					for (Vozilo vozilo2 : v) {
						for (Vozilo vozilo : carList) {		//ne sme brisati filijalu sa rezervisanim vozilama
							if(vozilo2.getId() == vozilo.getId()){
								return 1;
						}
					}
					}
				}
			}
			remove(filijalaId);
			return 2;
		} else {
			return 3;
		}
	}
}
