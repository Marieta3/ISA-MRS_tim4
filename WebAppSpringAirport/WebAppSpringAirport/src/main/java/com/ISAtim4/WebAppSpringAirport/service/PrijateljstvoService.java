package com.ISAtim4.WebAppSpringAirport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ISAtim4.WebAppSpringAirport.domain.Prijateljstvo;
import com.ISAtim4.WebAppSpringAirport.domain.RegistrovaniKorisnik;
import com.ISAtim4.WebAppSpringAirport.repository.PrijateljstvoRepository;

@Service
public class PrijateljstvoService {
	@Autowired
	private PrijateljstvoRepository prijateljstvoRepository;

	public List<Prijateljstvo> findAll() {
		return prijateljstvoRepository.findAll();
	}

	public Prijateljstvo findOne(Long id) {
		return prijateljstvoRepository.getOne(id);
	}

	public Page<Prijateljstvo> findAll(Pageable page) {
		return prijateljstvoRepository.findAll(page);
	}

	public Prijateljstvo save(Prijateljstvo friend) {
		return prijateljstvoRepository.save(friend);
	}

	public List<Prijateljstvo> findMyFriends(RegistrovaniKorisnik me) {
		return prijateljstvoRepository.FindMyFriends(me);
	}

	// trazim Prijateljstvo izmedju nas
	public Prijateljstvo getOneFriend(RegistrovaniKorisnik me,
			RegistrovaniKorisnik friend) {
		return prijateljstvoRepository.FindOneFriend(me, friend);
	}
	
	public List<Prijateljstvo> findMyRequests(RegistrovaniKorisnik me){
		return prijateljstvoRepository.FindMyRequests(me);
	}
	
	//nadji request koji je friend saljio mene
	public Prijateljstvo findOneRequest(RegistrovaniKorisnik me, RegistrovaniKorisnik friend){
		return prijateljstvoRepository.findOneRequest(me, friend);
	}

	public List<Prijateljstvo> findPotentionalFriends(RegistrovaniKorisnik me) {
		return prijateljstvoRepository.findPotentionalFriends(me);
	}
	
	public void remove(Long id) {
		prijateljstvoRepository.deleteById(id);
	}


}
