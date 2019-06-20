package com.ISAtim4.WebAppSpringAirport.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ISAtim4.WebAppSpringAirport.domain.BrzaSoba;
import com.ISAtim4.WebAppSpringAirport.domain.BrzoVozilo;
import com.ISAtim4.WebAppSpringAirport.domain.Vozilo;
import com.ISAtim4.WebAppSpringAirport.dto.BrzaRezervacijaDTO;
import com.ISAtim4.WebAppSpringAirport.repository.BrzaSobaRepository;

import com.ISAtim4.WebAppSpringAirport.repository.BrzoVoziloRepository;

@Service
@Transactional(readOnly = true)
public class BrzoVoziloService {
	@Autowired
	private BrzoVoziloRepository brzoVoziloRepository;
	
	@Autowired
	private VoziloService voziloService;
	
	@Transactional(readOnly = false)
	public BrzoVozilo save(BrzoVozilo brzoVozilo) {
		return brzoVoziloRepository.save(brzoVozilo);
	}

	public BrzoVozilo findOne(Long id) {
		return brzoVoziloRepository.getOne(id);
	}

	public List<BrzoVozilo> findAll() {
		return brzoVoziloRepository.findAll();
	}

	public Page<BrzoVozilo> findAll(Pageable page) {
		return brzoVoziloRepository.findAll(page);
	}

	@Transactional(readOnly = false)
	public void remove(Long id) {
		brzoVoziloRepository.deleteById(id);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BrzoVozilo create(BrzaRezervacijaDTO brzoVoziloDTO) {
		BrzoVozilo brzoVozilo=new BrzoVozilo();
		brzoVozilo.setNova_cena(brzoVoziloDTO.getNovaCena());
		brzoVozilo.setStart_date(brzoVoziloDTO.getStartDatum());
		brzoVozilo.setEnd_date(brzoVoziloDTO.getEndDatum());
		Vozilo vozilo=voziloService.findOne(brzoVoziloDTO.getId());
		//TODO: ubaciti proveru da li je mozda rezervisano u tom periodu
		brzoVozilo.setVozilo(vozilo);
		return save(brzoVozilo);
	}
	
	public List<BrzoVozilo> getBrzaVozila(Long rentId, Date vreme1, Date vreme2){
		return brzoVoziloRepository.getBrzaVozila(rentId, vreme1, vreme2);
	}
}
