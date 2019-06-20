package com.ISAtim4.WebAppSpringAirport.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ISAtim4.WebAppSpringAirport.domain.Korisnik;
import com.ISAtim4.WebAppSpringAirport.domain.Pozivnica;
import com.ISAtim4.WebAppSpringAirport.service.KorisnikService;
import com.ISAtim4.WebAppSpringAirport.service.PozivnicaService;

@RestController
public class PozivnicaController {
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private PozivnicaService pozivnicaService;
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(value = "/api/myInvitations", produces = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<Pozivnica> getPozivnice(Principal user){
		Korisnik me = this.korisnikService.findByKorisnickoIme(user.getName());
		ArrayList<Pozivnica> pozivnice = pozivnicaService.findMyInvitations(me);
		Calendar now = Calendar.getInstance();
		Calendar pozivnica = Calendar.getInstance();
		
		for (Pozivnica p : pozivnice) {
			if(p.getKomeSalje().getId().equals(me.getId()))
			{
				// provera da li je  korisnik reagovao u tri dana
				if(!p.getReagovanoNaPoziv())
				{
					now.setTime(new Date());
					pozivnica.setTime(p.getDatumSlanja());
					pozivnica.add(Calendar.DATE, 3);
					if(pozivnica.before(now))
						//prosla su 3 dana
					{
						pozivnicaService.decline(p.getId());
						pozivnice.remove(p);
					}
					
					pozivnica.setTime(p.getRezervacija().getOdabranaSedista().iterator().next().getLet().getVremePolaska());
					now.add(Calendar.HOUR_OF_DAY, 3);
					if(now.after(pozivnica))
						//ima manje od 3 sata do poletanja leta
					{
						pozivnicaService.decline(p.getId());
						pozivnice.remove(p);
					}
				}
			}
		}
		
		return  pozivnice;
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping(value = "/api/pozivi/Accept/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pozivnica> acceptInvitation(@PathVariable(value = "id") Long pozivId){
		
		return ResponseEntity.ok().body(pozivnicaService.accept(pozivId)); 
	}
	
	@PreAuthorize("hasRole('ROLE_USER')")
	@PutMapping(value = "/api/pozivi/Decline/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pozivnica> declineInvitation(@PathVariable(value = "id") Long pozivId){
		return ResponseEntity.ok().body(pozivnicaService.decline(pozivId));
	}
}
