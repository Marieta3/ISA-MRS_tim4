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
import com.ISAtim4.WebAppSpringAirport.domain.Soba;
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
		receiver = (RegistrovaniKorisnik) this.korisnikService
				.findOne(korisnikId);

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
