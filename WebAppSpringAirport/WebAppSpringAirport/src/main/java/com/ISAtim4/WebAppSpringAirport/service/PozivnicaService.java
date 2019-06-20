package com.ISAtim4.WebAppSpringAirport.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ISAtim4.WebAppSpringAirport.domain.Bodovanje;
import com.ISAtim4.WebAppSpringAirport.domain.Korisnik;
import com.ISAtim4.WebAppSpringAirport.domain.Pozivnica;
import com.ISAtim4.WebAppSpringAirport.domain.RegistrovaniKorisnik;
import com.ISAtim4.WebAppSpringAirport.repository.PozivnicaRepository;

@Service
@Transactional(readOnly = true)
public class PozivnicaService {
	@Autowired
	private PozivnicaRepository pozivnicaRepository; 
	
	@Autowired
	RezervacijaService rezervacijaService;
	
	@Autowired
	BodovanjeService bodovanjeService;
	
	@Autowired
	KorisnikService korisnikService;
	
	@Transactional(readOnly = false)
	public Pozivnica save(Pozivnica pozivnica) {
		return pozivnicaRepository.save(pozivnica);
	}

	public Pozivnica findOne(Long id) {
		return pozivnicaRepository.getOne(id);
	}

	public List<Pozivnica> findAll() {
		return pozivnicaRepository.findAll();
	}

	public Page<Pozivnica> findAll(Pageable page) {
		return pozivnicaRepository.findAll(page);
	}

	@Transactional(readOnly = false)
	public void remove(Long id) {
		pozivnicaRepository.deleteById(id);
	}
	
	public ArrayList<Pozivnica> findMyInvitations(Korisnik k){
		return pozivnicaRepository.findMyInvitations(k);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Pozivnica accept(Long pozivId) {
		Pozivnica p=findOne(pozivId);
		p.setReagovanoNaPoziv(true);
		p.setPrihvacen(true);
		//dodavanje putnika u rezervaciju
		//Rezervacija r=rezervacijaService.fin
		p.getRezervacija().getKorisnici().add(p.getKomeSalje());
		
		//dobije bonus poene
		Bodovanje b = bodovanjeService.findOne(1L);
		double km = p.getRezervacija().getOdabranaSedista().iterator().next().getLet().getDuzinaPutovanja() *1.0;
		double bonusPoeni = km / b.getKmZaBroj();
		
		RegistrovaniKorisnik me = p.getKomeSalje();
		
		double userPoint = me.getBrojPoena();
		userPoint += bonusPoeni;
		if(userPoint >= b.getMaxBroj())
		{
			userPoint = b.getMaxBroj()*1.0;
		}
		me.setBrojPoena(userPoint);
		korisnikService.save(me);
		
		return save(p); 
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Pozivnica decline(Long pozivId) {
		Pozivnica p=findOne(pozivId);
		p.setReagovanoNaPoziv(true);
		p.setPrihvacen(false);
		return save(p); 
	}
}
