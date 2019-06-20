package com.ISAtim4.WebAppSpringAirport.service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.ISAtim4.WebAppSpringAirport.domain.Let;
import com.ISAtim4.WebAppSpringAirport.domain.Ocena;
import com.ISAtim4.WebAppSpringAirport.domain.Sediste;
import com.ISAtim4.WebAppSpringAirport.repository.LetRepository;

@Service
@Transactional(readOnly = true)
public class LetService {

	@Autowired
	private LetRepository letRepository;

	@Autowired
	private OcenaService ocenaService;
	
	@Transactional(readOnly = false)
	public Let save(Let let) {
		return letRepository.save(let);
	}

	public Let findOne(Long id) {
		return letRepository.getOne(id);
	}

	public List<Let> findAll() {
		return letRepository.findAll();
	}

	public Page<Let> findAll(Pageable page) {
		return letRepository.findAll(page);
	}

	@Transactional(readOnly = false)
	public void remove(Long id) {
		letRepository.deleteById(id);
	}
	
	public List<Let> findFlightsOneWay(String mestoPolaska, String mestoDolaska, Date vreme1, Date vreme2) {
		return letRepository.findFlightsOneWay(mestoPolaska,mestoDolaska, vreme1, vreme2);
	}
	public List<Let> findFlightsPolazakDolazak(String mestoPolaska, String mestoDolaska) {
		return letRepository.findFlightsPolazakDolazak(mestoPolaska,mestoDolaska);
	}
	public List<Let> findFlightsTwoWay(String mestoPolaska, String mestoDolaska, Date vremePolaska, Date vremeDolaska){
		return letRepository.findFlightsTwoWay(mestoPolaska,mestoDolaska,vremePolaska,vremeDolaska);
	}
	
	public List<Let> findFlightsByAvio(String mestoPolaska, String mestoDolaska, Date vremePolaska, Date vremeDolaska, Long idAvio)
	{
		return letRepository.findFlightsByAvio(mestoPolaska, mestoDolaska, vremePolaska, vremeDolaska, idAvio);
	}
	
	public List<Let> findAllByAvioKompanija(AvioKompanija avio_kompanija){
		return letRepository.findAllByAvioKompanija(avio_kompanija);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Let create(Let let) {
		let.setBrojRedova(let.getBrojRedovaFC()+let.getBrojRedovaEC()+let.getBrojRedovaBC());
		for(int i = 1; i <= let.getBrojRedovaFC();i++){
			for (int j=1; j<= let.getBrojKolona();j++){
				Sediste s = new Sediste();
				s.setBrojReda(i);
				s.setBrojKolone(j);
				s.setKlasa("f");
				s.setCena(100.0);
				s.setRow_col(i+"_"+j);
				let.getSedista().add(s);
			}
		}
		for(int i = let.getBrojRedovaFC()+1; i <= let.getBrojRedovaFC()+let.getBrojRedovaEC();i++){
			for (int j=1; j<= let.getBrojKolona();j++){
				Sediste s = new Sediste();
				s.setBrojReda(i);
				s.setBrojKolone(j);
				s.setKlasa("e");
				s.setCena(40.0);
				s.setRow_col(i+"_"+j);
				let.getSedista().add(s);
			}
		}
		for(int i = let.getBrojRedovaFC()+let.getBrojRedovaEC()+1; i <= let.getBrojRedova();i++){
			for (int j=1; j<= let.getBrojKolona();j++){
				Sediste s = new Sediste();
				s.setBrojReda(i);
				s.setBrojKolone(j);
				s.setKlasa("b");
				s.setCena(90.0);
				s.setRow_col(i+"_"+j);
				let.getSedista().add(s);
			}
		}
		return save(let);
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
				s.setLet(let);
				s.setBrojReda(i);
				s.setBrojKolone(j);
				s.setKlasa("f");
				s.setRow_col(i+"_"+j);
				sedista.add(s);
			}
		}
		for(int i = let.getBrojRedovaFC()+1; i <= let.getBrojRedovaFC()+let.getBrojRedovaEC();i++){
			for (int j=1; j<= let.getBrojKolona();j++){
				Sediste s = new Sediste();
				if(i>2 && j%3==0) {
					s.setRezervisano(true);
				}
				s.setLet(let);
				s.setBrojReda(i);
				s.setBrojKolone(j);
				s.setKlasa("e");
				s.setRow_col(i+"_"+j);
				sedista.add(s);
			}
		}
		for(int i = let.getBrojRedovaFC()+let.getBrojRedovaEC()+1; i <= let.getBrojRedova();i++){
			for (int j=1; j<= let.getBrojKolona();j++){
				Sediste s = new Sediste();
				if(i%3==1 && j>=2) {
					s.setRezervisano(true);
				}
				s.setLet(let);
				s.setBrojReda(i);
				s.setBrojKolone(j);
				s.setKlasa("b");
				s.setRow_col(i+"_"+j);
				sedista.add(s);
			}
		}
		return sedista;
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Let getLet(Long idLeta) {
		Let let = findOne(idLeta);

		if (let == null) {
			return null;
		}
		if(let.getSedista().isEmpty()) {
			let.setSedista(popuniSedista(let));
			save(let);
		}
		List<Ocena> ocene = ocenaService.findAllByLet(let);
		let.setOcena(Ocena.getProsek(ocene));
		return let;
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Let update(Long letId,Let letDetalji) {
		Let let = findOne(letId);
		if (let == null) {
			return null;
		}

		let.setPocetnaDestinacija(letDetalji.getPocetnaDestinacija());
		let.setKrajnjaDestinacija(letDetalji.getKrajnjaDestinacija());
		let.setVremePolaska(letDetalji.getVremePolaska());
		let.setVremeDolaska(letDetalji.getVremeDolaska());
		let.setDuzinaPutovanja(letDetalji.getDuzinaPutovanja());
		
		//ova dva mogu da izazovu problem
		let.setAvioKompanija(letDetalji.getAvioKompanija()); 
		let.setSedista(letDetalji.getSedista());
		
		
		let.setModel(letDetalji.getModel());
		let.setBrojRedova(letDetalji.getBrojRedova());
		let.setBrojKolona(letDetalji.getBrojKolona());
		let.setBrojRedovaEC(letDetalji.getBrojRedovaEC());
		let.setBrojRedovaBC(letDetalji.getBrojRedovaBC());
		let.setBrojRedovaFC(letDetalji.getBrojRedovaFC());
		
		List<Ocena> ocene = ocenaService.findAllByLet(let);
		let.setOcena(Ocena.getProsek(ocene));
		
		Let updateLet = save(let);
		return updateLet;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean delete(Long id) {
		Let let = findOne(id);

		if (let != null) {
			remove(id);
			return true;
		} else {
			return false;
		}
	}
}
