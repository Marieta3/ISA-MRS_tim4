package com.ISAtim4.WebAppSpringAirport.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.ISAtim4.WebAppSpringAirport.domain.AvioKompanija;
import com.ISAtim4.WebAppSpringAirport.domain.Ocena;
import com.ISAtim4.WebAppSpringAirport.repository.AvioKompanijaRepository;
@Service
@Transactional(readOnly = true)
public class AvioKompanijaService {
	@Autowired
	private AvioKompanijaRepository avioKompanijaRepository;
	
	@Autowired
	private OcenaService ocenaService;
	
	
	@Transactional(readOnly = false)
	public AvioKompanija save(AvioKompanija avioKompanija) {
		return avioKompanijaRepository.save(avioKompanija);
	}
	
	public AvioKompanija findOne(Long id) {
		return avioKompanijaRepository.getOne(id);
	}

	public List<AvioKompanija> findAll() {
		return avioKompanijaRepository.findAll();
	}
	
	public Page<AvioKompanija> findAll(Pageable page) {
		return avioKompanijaRepository.findAll(page);
	}
	
	public List<AvioKompanija> findByName(String name){
		return avioKompanijaRepository.findAllByNaziv(name);
	}

	public List<AvioKompanija> containsName(String name){
		return avioKompanijaRepository.pronadjiAvioSadrziNaziv(name);
	}

	@Transactional(readOnly = false)
	public void remove(Long id) {
		avioKompanijaRepository.deleteById(id);
	}
	
	@Transactional(readOnly = false)
	public AvioKompanija create(@Valid @RequestBody AvioKompanija avioKompanija) {
		avioKompanija.setCoord1(31.214535);
		avioKompanija.setCoord2(29.945663);
		return save(avioKompanija);
	}
	
	//@Transactional(readOnly = false)//setuje se ocena
	public AvioKompanija getAvioKompanija(Long id) {
		AvioKompanija aviokompanija = findOne(id);

		
		List<Ocena> ocene = ocenaService.findAllByAvio(aviokompanija);
		aviokompanija.setOcena(Ocena.getProsek(ocene));
		return aviokompanija;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public AvioKompanija update(Long aviokompanijaId,AvioKompanija avioKompanijaDetalji) {
		AvioKompanija avioKompanija = findOne(aviokompanijaId);
		if(avioKompanija==null) {
			return null;
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
		
		AvioKompanija updateAviokompanija = save(avioKompanija);
		
		return updateAviokompanija;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean delete(Long avioKompanijaId) {
		AvioKompanija aviokompanija = findOne(avioKompanijaId);
		if (aviokompanija != null) {
			remove(avioKompanijaId);
			return true;
		} else {
			return false;
		}
	}
}
