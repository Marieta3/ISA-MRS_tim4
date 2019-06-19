package com.ISAtim4.WebAppSpringAirport.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ISAtim4.WebAppSpringAirport.domain.Vozilo;
import com.ISAtim4.WebAppSpringAirport.repository.VoziloRepository;


@Service
@Transactional(readOnly = true)
public class VoziloService{
	@Autowired
	private VoziloRepository voziloRepository;
	
	public List<Vozilo> findAll(){
		return voziloRepository.findAll();
	}

	public Vozilo findOne(Long id) {
		return voziloRepository.getOne(id);
	}

	public Page<Vozilo> findAll(Pageable page) {
		return voziloRepository.findAll(page);
	}

	@Transactional(readOnly = false)
	public Vozilo save(Vozilo vozilo) {
		return voziloRepository.save(vozilo);
	}

	@Transactional(readOnly = false)
	public void remove(Long id) {
		voziloRepository.deleteById(id);
	}
	
	/*public void updateCarReservation(List<Long> ids) {
		voziloRepository.updateCarReservation(ids);
	}*/
	public Set<Vozilo> findVozilaIds(List<Long> ids) {
		return voziloRepository.findVozilaIds(ids);
	}
}
