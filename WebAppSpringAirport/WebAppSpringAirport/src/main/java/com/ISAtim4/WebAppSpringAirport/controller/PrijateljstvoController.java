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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
	@RequestMapping(value = "/api/friends/sendrequest/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Prijateljstvo> sendRequest(Principal user,
			@PathVariable(value = "id") Long korisnikId) {

		RegistrovaniKorisnik me = null;
		RegistrovaniKorisnik receiver = null;
		Prijateljstvo request = new Prijateljstvo();

		if (user != null) {
			me = (RegistrovaniKorisnik) this.korisnikService
					.findByKorisnickoIme(user.getName());
		}
		receiver = (RegistrovaniKorisnik) this.korisnikService.findOneID(korisnikId);

		if (me == null) {
			logger.info("Sender user is null!");
			return ResponseEntity.notFound().build();
		} else if (receiver == null) {
			logger.info("Receiver user is null!");
			return ResponseEntity.notFound().build();

		} else if (receiver.getId() == me.getId()) {
			logger.info("Cant send request to myself!");
			return ResponseEntity.badRequest().build();

		} else {
			logger.info("Sender " + me.getUsername()
					+ " succesfully sent friend request to "
					+ receiver.getKorisnickoIme() + "!");
			request.setAccepted(false);
			request.setReacted(false);
			request.setDatum(new Date());
			request.setReceiver(receiver);
			request.setSender(me);
			prijateljstvoService.save(request);
			return ResponseEntity.ok().body(request);
		}
	}

	// Ulogovan korisnik trazi svoje prijatelje
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/api/friends/", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Korisnik>> getFriends(Principal user) {
		RegistrovaniKorisnik me = null;

		if (user != null) {
			me = (RegistrovaniKorisnik) this.korisnikService
					.findByKorisnickoIme(user.getName());
		}
		List<Prijateljstvo> potential_friends = prijateljstvoService
				.findMyFriends(me);
		List<Korisnik> friends = new ArrayList<Korisnik>();
		for (Prijateljstvo p : potential_friends) {
			if (p.getAccepted() && p.getReacted()) {
				if (p.getReceiver().getId() == me.getId()
						&& p.getSender().getId() != me.getId()) {
					friends.add(p.getSender());
				} else if (p.getSender().getId() == me.getId()
						&& p.getReceiver().getId() != me.getId()) {
					friends.add(p.getReceiver());
				}
			}
		}

		return ResponseEntity.ok().body(friends);
	}
	
	// Ulogovan korisnik trazi svoje friend requeste, dobije listu reg.kor. koji su saljili request
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/api/friendrequests", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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
	@RequestMapping(value = "/api/potentialfriends", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Korisnik>> getPossibleFriends(Principal user) {
		RegistrovaniKorisnik me = null;
		List<Prijateljstvo> connectedPeople = new ArrayList<>();
		List<Korisnik> potentialFriends = new ArrayList<>();
		if (user != null) {
			me = (RegistrovaniKorisnik) this.korisnikService
					.findByKorisnickoIme(user.getName());
		}
		connectedPeople = prijateljstvoService.findPotentionalFriends(me);
		
		List<Long> ids = new ArrayList<Long>();
		for (Prijateljstvo p : connectedPeople) {
			if (p.getReceiver().getId() == me.getId()
					&& p.getSender().getId() != me.getId()) {
				if(!ids.contains(p.getSender().getId())){
					ids.add(p.getSender().getId());
				}
			} else if (p.getSender().getId() == me.getId()
					&& p.getReceiver().getId() != me.getId()) {
				if(!ids.contains(p.getReceiver().getId())){
					ids.add(p.getReceiver().getId());
					
				}
			}
		}
		
		potentialFriends = korisnikService.findNotConnectedPeople(ids);
		//izbacimo sve korisnike koje nisu regKor
		List<Korisnik>finalList = new ArrayList<>();
		for (Korisnik k : potentialFriends) {
			if(k.getId()==me.getId()){
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
	@RequestMapping(value = "/api/friendrequests/accept/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Prijateljstvo> acceptRequest(Principal user,
			@PathVariable(value = "id") Long friendId) {

		RegistrovaniKorisnik me = null;
		if (user != null) {
			me = (RegistrovaniKorisnik) this.korisnikService.findByKorisnickoIme(user.getName());
		}
		RegistrovaniKorisnik friend = (RegistrovaniKorisnik) this.korisnikService.findOneID(friendId);
		if (friend != null && me!= null) {
			Prijateljstvo p = prijateljstvoService.findOneRequest(me, friend);
			if(p != null){
				p.setAccepted(true);
				p.setReacted(true);
				System.out.println("Request accepted!");
				prijateljstvoService.save(p);
				return ResponseEntity.ok().body(p);
			}else{
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	/* odbije friend requesta od korisnika sa ID om id */
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/api/friendrequests/reject/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Prijateljstvo> rejectRequest(Principal user,
			@PathVariable(value = "id") Long friendId) {
		RegistrovaniKorisnik me = null;
		if (user != null) {
			me = (RegistrovaniKorisnik) this.korisnikService.findByKorisnickoIme(user.getName());
		}
		RegistrovaniKorisnik friend = (RegistrovaniKorisnik) this.korisnikService.findOneID(friendId);
		if (friend != null && me!= null) {
			Prijateljstvo p = prijateljstvoService.findOneRequest(me, friend);
			if(p != null){
				p.setAccepted(false);
				p.setReacted(true);
				System.out.println("Request rejected!");
				prijateljstvoService.save(p);
				return ResponseEntity.ok().body(p);
			}else{
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/* brisanje prijatelja */
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/api/friends/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Prijateljstvo> deleteFriend(Principal user,
			@PathVariable(value = "id") Long friendId) {
		System.out.println("Az ID " + friendId);
		RegistrovaniKorisnik me = null;
		if (user != null) {
			me = (RegistrovaniKorisnik) this.korisnikService.findByKorisnickoIme(user.getName());
		}
		RegistrovaniKorisnik friend = (RegistrovaniKorisnik) this.korisnikService.findOneID(friendId);
		if (friend != null && me!= null) {
			Prijateljstvo p = prijateljstvoService.getOneFriend(me, friend);
			System.out.println(p != null);
			if(p != null){
				prijateljstvoService.remove(p.getId());
				System.out.println("Succesful deletion!");
				return ResponseEntity.ok().body(new Prijateljstvo());
			}else{
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
