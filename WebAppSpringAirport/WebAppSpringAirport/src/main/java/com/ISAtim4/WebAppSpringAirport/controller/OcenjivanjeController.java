package com.ISAtim4.WebAppSpringAirport.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ISAtim4.WebAppSpringAirport.domain.AvioKompanija;
import com.ISAtim4.WebAppSpringAirport.domain.Hotel;
import com.ISAtim4.WebAppSpringAirport.domain.Let;
import com.ISAtim4.WebAppSpringAirport.domain.Ocena;
import com.ISAtim4.WebAppSpringAirport.domain.RegistrovaniKorisnik;
import com.ISAtim4.WebAppSpringAirport.domain.RentACar;
import com.ISAtim4.WebAppSpringAirport.domain.Soba;
import com.ISAtim4.WebAppSpringAirport.domain.Vozilo;
import com.ISAtim4.WebAppSpringAirport.dto.OcenaDTO;
import com.ISAtim4.WebAppSpringAirport.service.AvioKompanijaService;
import com.ISAtim4.WebAppSpringAirport.service.HotelService;
import com.ISAtim4.WebAppSpringAirport.service.KorisnikService;
import com.ISAtim4.WebAppSpringAirport.service.LetService;
import com.ISAtim4.WebAppSpringAirport.service.OcenaService;
import com.ISAtim4.WebAppSpringAirport.service.RentACarService;
import com.ISAtim4.WebAppSpringAirport.service.RezervacijaService;
import com.ISAtim4.WebAppSpringAirport.service.SobaService;
import com.ISAtim4.WebAppSpringAirport.service.VoziloService;

@RestController
public class OcenjivanjeController {
	//private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	RezervacijaService rezervacijaService;

	@Autowired
	OcenaService ocenaService;
	
	@Autowired
	KorisnikService korisnikService;

	@Autowired
	AvioKompanijaService avioKompanijaService;

	@Autowired
	LetService letService;

	@Autowired
	RentACarService rentACarService;

	@Autowired
	VoziloService voziloService;

	@Autowired
	HotelService hotelService;

	@Autowired
	SobaService sobaService;
	

	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping(value = "/api/ocenjivanje", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Ocena>> rejectRequest(Principal user,
			@Valid @RequestBody List<OcenaDTO> ocene) {
		RegistrovaniKorisnik me = (RegistrovaniKorisnik) korisnikService.findByKorisnickoIme(user.getName());
		ArrayList <Ocena> svakaOcena = (ArrayList<Ocena>)ocenaService.findAll();
		ArrayList<Ocena> kreiraneOcene = new ArrayList<>();
		String entity;
		int ocena;
		Long id;
		
		mainLoop :for (OcenaDTO o : ocene) {	//iteriram kroz dobijene entitete+ocene
			String[] temp = o.getEntitetId().trim().split("_");
			entity = temp[0];
			id = Long.parseLong(temp[1]);
			ocena = o.getOcena();

			if(ocena <= 0 || ocena > 5) //ako 0 to znaci da korisnik nije ocenio taj servis
			{
				continue mainLoop;
			}
			
			for (Ocena o1 : svakaOcena) {	//proverimo da li je korisnik već ocenjivao ovaj servis
											//ako jeste, onda vracamo na mainLoop
				if(entity.equals("avio")){
					if(o1.getAvio()!= null && o1.getKorisnik_id()!= null)
					{
						if(o1.getAvio().getId() == id && o1.getKorisnik_id().equals(me.getId()))
						{
							continue mainLoop;
						}
					}
				}else if(entity.equals("let")){
					if(o1.getLet()!= null && o1.getKorisnik_id()!= null)
					{
						if(o1.getLet().getId() == id && o1.getKorisnik_id().equals(me.getId()))
						{
							continue mainLoop;
						}
					}	
				}else if(entity.equals("hotel")){
					if(o1.getHotel()!= null && o1.getKorisnik_id()!= null)
					{
						if(o1.getHotel().getId() == id && o1.getKorisnik_id().equals(me.getId()))
						{
							continue mainLoop;
						}
					}	
				}else if(entity.equals("soba")){
					if(o1.getSoba()!= null && o1.getKorisnik_id()!= null)
					{
						if(o1.getSoba().getId() == id && o1.getKorisnik_id().equals(me.getId()))
						{
							continue mainLoop;
						}
					}
				}else if(entity.equals("rent")){
					if(o1.getRent()!= null && o1.getKorisnik_id()!= null)
					{
						if(o1.getRent().getId() == id && o1.getKorisnik_id().equals(me.getId()))
						{
							continue mainLoop;
						}
					}
				}else if(entity.equals("vozilo")){
					if(o1.getVozilo()!= null && o1.getKorisnik_id()!= null)
					{
						if(o1.getVozilo().getId() == id && o1.getKorisnik_id().equals(me.getId()))
						{
							continue mainLoop;
						}
					}
				}
			}
			//ovde vec sigurno znamo da korisnik nije jos ocenio neki entitet, pa treba samo oceniti i sacuvati ocenu
			Ocena rating = new Ocena();
			rating.setDatumOcenjivanja(new Date());
			rating.setOcena(ocena);
			rating.setKorisnik_id(me.getId());
			rating.setRezervacija(rezervacijaService.findOne((long) o.getRezervacijaId()));
			
			if(entity.equals("avio")){
				AvioKompanija a = avioKompanijaService.findOne(id);
				rating.setAvio(a);
			}else if(entity.equals("let")){
				Let l = letService.findOne(id);
				rating.setLet(l);
			}else if(entity.equals("hotel")){
				Hotel h = hotelService.findOne(id);
				rating.setHotel(h);
			}else if(entity.equals("soba")){
				Soba s = sobaService.findOne(id);
				rating.setSoba(s);
			}else if(entity.equals("rent")){
				RentACar r = rentACarService.findOne(id);
				rating.setRent(r);
			}else if(entity.equals("vozilo")){
				Vozilo v = voziloService.findOne(id);
				rating.setVozilo(v);
			}
			kreiraneOcene.add(rating);
			ocenaService.save(rating);
			
		}
		if(kreiraneOcene.size()== 0)
		{
			return ResponseEntity.badRequest().build();
		}
		
		return ResponseEntity.ok().body(kreiraneOcene);
	}
}