package com.ISAtim4.WebAppSpringAirport.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.ISAtim4.WebAppSpringAirport.domain.Korisnik;
import com.ISAtim4.WebAppSpringAirport.domain.Prijateljstvo;
import com.ISAtim4.WebAppSpringAirport.domain.RegistrovaniKorisnik;
import com.ISAtim4.WebAppSpringAirport.service.KorisnikService;
import com.ISAtim4.WebAppSpringAirport.service.PrijateljstvoService;

@RestController
public class PrijateljstvoController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	PrijateljstvoService prijateljstvoService;

	@Autowired
	KorisnikService korisnikService;

	// Ulogovan korisnik šalji zahtev za {id}
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping(value = "/api/friends/sendrequest/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Prijateljstvo> sendRequest(Principal user,
			@PathVariable(value = "id") Long korisnikId) {

		
		Prijateljstvo request=prijateljstvoService.send(user, korisnikId);
		if (request == null) {
			logger.info("Request is null!");
			return ResponseEntity.notFound().build();
		} 
		else {
			logger.info("Sender " 
					+ " succesfully sent friend request to receiver" + "!");
			
			return ResponseEntity.ok().body(request);
		}
	}

	// Ulogovan korisnik trazi svoje prijatelje
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/api/friends/",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Korisnik>> getFriends(Principal user) {
		RegistrovaniKorisnik me = null;

		if (user != null) {
			me = (RegistrovaniKorisnik) this.korisnikService
					.findByKorisnickoIme(user.getName());
		}
		List<Prijateljstvo> potential_friends = prijateljstvoService
				.findMyFriends(me);
		List<Korisnik> friends = new ArrayList<>();
		for (Prijateljstvo p : potential_friends) {
			if (p.getAccepted() && p.getReacted()) {
				if (p.getReceiver().getId().equals(me.getId())
						&& !p.getSender().getId().equals(me.getId())) {
					friends.add(p.getSender());
				} else if (p.getSender().getId().equals(me.getId())
						&& !p.getReceiver().getId().equals(me.getId())) {
					friends.add(p.getReceiver());
				}
			}
		}

		return ResponseEntity.ok().body(friends);
	}
	
	// Ulogovan korisnik trazi svoje friend requeste, dobije listu reg.kor. koji su saljili request
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/api/friendrequests", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<RegistrovaniKorisnik>> getFriendsRequests(Principal user) {
		RegistrovaniKorisnik me = null;
		List<Prijateljstvo> requests = new ArrayList<>();
		List<RegistrovaniKorisnik> potentialFriends = new ArrayList<>();
		if (user != null) {
			me = (RegistrovaniKorisnik) this.korisnikService
					.findByKorisnickoIme(user.getName());
		}
		requests = prijateljstvoService.findMyRequests(me);
		
		for (Prijateljstvo prijateljstvo : requests) {
			potentialFriends.add(prijateljstvo.getSender());
		}
		return ResponseEntity.ok().body(potentialFriends);
	}
	
	// Ulogovan korisnik trazi nove prijatelje, dobije listu reg.kor. koji jos nisu poslali request, nema odbijen request i nije vec prijatelj
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/api/potentialfriends", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Korisnik>> getPossibleFriends(Principal user) {
		RegistrovaniKorisnik me = null;
		List<Prijateljstvo> connectedPeople = new ArrayList<>();
		List<Korisnik> potentialFriends = new ArrayList<>();
		if (user != null) {
			me = (RegistrovaniKorisnik) this.korisnikService
					.findByKorisnickoIme(user.getName());
		}
		connectedPeople = prijateljstvoService.findPotentionalFriends(me);
		
		List<Long> ids = new ArrayList<>();
		for (Prijateljstvo p : connectedPeople) {
			if (p.getReceiver().getId().equals(me.getId())
					&& !p.getSender().getId().equals(me.getId())) {
				if(!ids.contains(p.getSender().getId())){
					ids.add(p.getSender().getId());
				}
			} else if (p.getSender().getId().equals(me.getId())
					&& !p.getReceiver().getId().equals(me.getId())) {
				if(!ids.contains(p.getReceiver().getId())){
					ids.add(p.getReceiver().getId());
					
				}
			}
		}
		
		potentialFriends = korisnikService.findNotConnectedPeople(ids);
		//izbacimo sve korisnike koje nisu regKor
		List<Korisnik>finalList = new ArrayList<>();
		for (Korisnik k : potentialFriends) {
			if(k.getId().equals(me.getId())){
				continue;
			}
			System.out.println(k.getUloga() + " " + k.getUloga().equals("ROLE_USER") );
			if(k.getUloga().equals("ROLE_USER") && !finalList.contains(k))
				finalList.add(k);
			
		}
		
		return ResponseEntity.ok().body(finalList);
	}
	
	/* prihvata friend requesta od korisnika sa ID om id */
	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping(value = "/api/friendrequests/accept/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Prijateljstvo> acceptRequest(Principal user,
			@PathVariable(value = "id") Long friendId) {

		Prijateljstvo p=prijateljstvoService.accept(user, friendId);
		if(p==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return ResponseEntity.ok().body(p);
		}
	}
	
	/* odbije friend requesta od korisnika sa ID om id */
	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping(value = "/api/friendrequests/reject/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Prijateljstvo> rejectRequest(Principal user,
			@PathVariable(value = "id") Long friendId) {
		Prijateljstvo p=prijateljstvoService.reject(user, friendId);
		if(p==null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}else {
			return ResponseEntity.ok().body(p);
		}
		
	}

	/* brisanje prijatelja */
	@PreAuthorize("hasRole('ROLE_USER')")
	@DeleteMapping(value = "/api/friends/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Prijateljstvo> deleteFriend(Principal user,
			@PathVariable(value = "id") Long friendId) {
		if(prijateljstvoService.delete(user, friendId)) {
			return ResponseEntity.ok().body(new Prijateljstvo());
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
}
