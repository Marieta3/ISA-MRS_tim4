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
import com.ISAtim4.WebAppSpringAirport.domain.Soba;
import com.ISAtim4.WebAppSpringAirport.dto.BrzaRezervacijaDTO;
import com.ISAtim4.WebAppSpringAirport.repository.BrzaSobaRepository;
@Service
@Transactional(readOnly = true)
public class BrzaSobaService {
	@Autowired
	private BrzaSobaRepository brzaSobaRepository;
	
	@Autowired
	private SobaService sobaService;
	
	@Transactional(readOnly = false)
	public BrzaSoba save(BrzaSoba brzaSoba) {
		return brzaSobaRepository.save(brzaSoba);
	}

	public BrzaSoba findOne(Long id) {
		return brzaSobaRepository.getOne(id);
	}

	public List<BrzaSoba> findAll() {
		return brzaSobaRepository.findAll();
	}

	public Page<BrzaSoba> findAll(Pageable page) {
		return brzaSobaRepository.findAll(page);
	}

	@Transactional(readOnly = false)
	public void remove(Long id) {
		brzaSobaRepository.deleteById(id);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public BrzaSoba create(BrzaRezervacijaDTO brzaSobaDTO) {
		BrzaSoba brzaSoba=new BrzaSoba();
		brzaSoba.setNova_cena(brzaSobaDTO.getNovaCena());
		brzaSoba.setStart_date(brzaSobaDTO.getStartDatum());
		brzaSoba.setEnd_date(brzaSobaDTO.getEndDatum());
		Soba soba=sobaService.findOne(brzaSobaDTO.getId());
		//TODO: ubaciti proveru da li je mozda rezervisana u tom periodu
		brzaSoba.setSoba(soba);
		return save(brzaSoba);
	}
	
	public List<BrzaSoba> getBrzeSobeHotela(Long hotelId, Date vreme1, Date vreme2){
		return brzaSobaRepository.getBrzeSobeHotela(hotelId, vreme1, vreme2);
	}
}
