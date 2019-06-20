package com.ISAtim4.WebAppSpringAirport.service;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.ISAtim4.WebAppSpringAirport.domain.Prijateljstvo;
import com.ISAtim4.WebAppSpringAirport.domain.RegistrovaniKorisnik;
import com.ISAtim4.WebAppSpringAirport.repository.PrijateljstvoRepository;

@Service
@Transactional(readOnly = true)
public class PrijateljstvoService {
	@Autowired
	private PrijateljstvoRepository prijateljstvoRepository;

	@Autowired
	KorisnikService korisnikService;
	
	public List<Prijateljstvo> findAll() {
		return prijateljstvoRepository.findAll();
	}

	public Prijateljstvo findOne(Long id) {
		return prijateljstvoRepository.getOne(id);
	}

	public Page<Prijateljstvo> findAll(Pageable page) {
		return prijateljstvoRepository.findAll(page);
	}

	@Transactional(readOnly = false)
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
	
	@Transactional(readOnly = false)
	public void remove(Long id) {
		prijateljstvoRepository.deleteById(id);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Prijateljstvo send(Principal user,Long korisnikId) {
		RegistrovaniKorisnik me = null;
		RegistrovaniKorisnik receiver = null;
		Prijateljstvo request = new Prijateljstvo();

		if (user != null) {
			me = (RegistrovaniKorisnik) this.korisnikService
					.findByKorisnickoIme(user.getName());
		}
		receiver = (RegistrovaniKorisnik) this.korisnikService.findOneID(korisnikId);

		if (me == null) {
			System.out.println("Sender user is null!");
			//return ResponseEntity.notFound().build();
			return null;
		} else if (receiver == null) {
			System.out.println("Receiver user is null!");
			//return ResponseEntity.notFound().build();
			return null;

		} else if (receiver.getId().equals(me.getId())) {
			System.out.println("Cant send request to myself!");
			//return ResponseEntity.badRequest().build();
			return null;

		} else {
			System.out.println("Sender " + me.getUsername()
					+ " succesfully sent friend request to "
					+ receiver.getKorisnickoIme() + "!");
			request.setAccepted(false);
			request.setReacted(false);
			request.setDatum(new Date());
			request.setReceiver(receiver);
			request.setSender(me);
			save(request);
			return request;
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Prijateljstvo accept(Principal user, Long friendId) {
		RegistrovaniKorisnik me = null;
		if (user != null) {
			me = (RegistrovaniKorisnik) this.korisnikService.findByKorisnickoIme(user.getName());
		}
		RegistrovaniKorisnik friend = (RegistrovaniKorisnik) this.korisnikService.findOneID(friendId);
		if (friend != null && me!= null) {
			Prijateljstvo p = findOneRequest(me, friend);
			if(p != null){
				p.setAccepted(true);
				p.setReacted(true);
				System.out.println("Request accepted!");
				save(p);
				return p;
			}else{
				return null;
			}
		} else {
			return null;
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Prijateljstvo reject(Principal user, Long friendId) {
		
		RegistrovaniKorisnik me = null;
		if (user != null) {
			me = (RegistrovaniKorisnik) this.korisnikService.findByKorisnickoIme(user.getName());
		}
		RegistrovaniKorisnik friend = (RegistrovaniKorisnik) this.korisnikService.findOneID(friendId);
		if (friend != null && me!= null) {
			Prijateljstvo p = findOneRequest(me, friend);
			if(p != null){
				p.setAccepted(false);
				p.setReacted(true);
				System.out.println("Request rejected!");
				save(p);
				return p;
			}else{
				return null;
			}
		} else {
			return null;
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean delete(Principal user,Long friendId) {
		System.out.println("Az ID " + friendId);
		RegistrovaniKorisnik me = null;
		if (user != null) {
			me = (RegistrovaniKorisnik) this.korisnikService.findByKorisnickoIme(user.getName());
		}
		RegistrovaniKorisnik friend = (RegistrovaniKorisnik) this.korisnikService.findOneID(friendId);
		if (friend != null && me!= null) {
			Prijateljstvo p = getOneFriend(me, friend);
			System.out.println(p != null);
			if(p != null){
				remove(p.getId());
				System.out.println("Succesful deletion!");
				return true;
			}else{
				return false;
			}
		} else {
			return false;
		}
	}
}
