package com.ISAtim4.WebAppSpringAirport.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.ISAtim4.WebAppSpringAirport.domain.AvioKompanija;
import com.ISAtim4.WebAppSpringAirport.domain.Hotel;
import com.ISAtim4.WebAppSpringAirport.domain.Let;
import com.ISAtim4.WebAppSpringAirport.domain.Ocena;
import com.ISAtim4.WebAppSpringAirport.domain.RegistrovaniKorisnik;
import com.ISAtim4.WebAppSpringAirport.domain.RentACar;
import com.ISAtim4.WebAppSpringAirport.domain.Rezervacija;
import com.ISAtim4.WebAppSpringAirport.domain.Soba;
import com.ISAtim4.WebAppSpringAirport.domain.Vozilo;
import com.ISAtim4.WebAppSpringAirport.dto.OcenaDTO;
import com.ISAtim4.WebAppSpringAirport.repository.OcenaRepository;

@Service
@Transactional(readOnly = true)
public class OcenaService {
	@Autowired
	private OcenaRepository ocenaRepository;

	@Autowired
	RezervacijaService rezervacijaService;

	
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
	@Transactional(readOnly = false)
	public Ocena save(Ocena ocena) {
		return ocenaRepository.save(ocena);
	}

	public Ocena findOne(Long id) {
		return ocenaRepository.getOne(id);
	}

	public List<Ocena> findAll() {
		return ocenaRepository.findAll();
	}
	
	public List<Ocena> findAllByRezervacija(Rezervacija r) {
		return ocenaRepository.findAllByRezervacija(r);
	}
	
	public List<Ocena> findAllByRent(RentACar r) {
		return ocenaRepository.findAllByRent(r);
	}
	public List<Ocena> findAllByVozilo(Vozilo v) {
		return ocenaRepository.findAllByVozilo(v);
	}
	public List<Ocena> findAllByLet(Let l) {
		return ocenaRepository.findAllByLet(l);
	}
	public List<Ocena> findAllByAvio(AvioKompanija a) {
		return ocenaRepository.findAllByAvio(a);
	}
	public List<Ocena> findAllByHotel(Hotel h) {
		return ocenaRepository.findAllByHotel(h);
	}
	public List<Ocena> findAllBySoba(Soba s) {
		return ocenaRepository.findAllBySoba(s);
	}

	public Page<Ocena> findAll(Pageable page) {
		return ocenaRepository.findAll(page);
	}

	@Transactional(readOnly = false)
	public void remove(Long id) {
		ocenaRepository.deleteById(id);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public ArrayList<Ocena> ocenjivanje(Principal user,List<OcenaDTO> ocene){
		RegistrovaniKorisnik me = (RegistrovaniKorisnik) korisnikService.findByKorisnickoIme(user.getName());
		ArrayList <Ocena> svakaOcena = (ArrayList<Ocena>) findAll();
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
											//ako jeste, onda izmenimo ocenu
				if(entity.equals("avio")){
					if(o1.getAvio()!= null && o1.getKorisnik_id()!= null)
					{
						if(o1.getAvio().getId() == id && o1.getKorisnik_id().equals(me.getId()))
						{
							o1.setOcena(ocena);
							o1.setDatumOcenjivanja(new Date());
							save(o1);
							kreiraneOcene.add(o1);
							continue mainLoop;
						}
					}
				}else if(entity.equals("let")){
					if(o1.getLet()!= null && o1.getKorisnik_id()!= null)
					{
						if(o1.getLet().getId() == id && o1.getKorisnik_id().equals(me.getId()))
						{
							o1.setOcena(ocena);
							o1.setDatumOcenjivanja(new Date());
							save(o1);
							kreiraneOcene.add(o1);
							continue mainLoop;
						}
					}	
				}else if(entity.equals("hotel")){
					if(o1.getHotel()!= null && o1.getKorisnik_id()!= null)
					{
						if(o1.getHotel().getId() == id && o1.getKorisnik_id().equals(me.getId()))
						{
							o1.setOcena(ocena);
							o1.setDatumOcenjivanja(new Date());
							save(o1);
							kreiraneOcene.add(o1);
							continue mainLoop;
						}
					}	
				}else if(entity.equals("soba")){
					if(o1.getSoba()!= null && o1.getKorisnik_id()!= null)
					{
						if(o1.getSoba().getId() == id && o1.getKorisnik_id().equals(me.getId()))
						{
							o1.setOcena(ocena);
							o1.setDatumOcenjivanja(new Date());
							save(o1);
							kreiraneOcene.add(o1);
							continue mainLoop;
						}
					}
				}else if(entity.equals("rent")){
					if(o1.getRent()!= null && o1.getKorisnik_id()!= null)
					{
						if(o1.getRent().getId() == id && o1.getKorisnik_id().equals(me.getId()))
						{
							o1.setOcena(ocena);
							o1.setDatumOcenjivanja(new Date());
							save(o1);
							kreiraneOcene.add(o1);
							continue mainLoop;
						}
					}
				}else if(entity.equals("vozilo")){
					if(o1.getVozilo()!= null && o1.getKorisnik_id()!= null)
					{
						if(o1.getVozilo().getId() == id && o1.getKorisnik_id().equals(me.getId()))
						{
							o1.setOcena(ocena);
							o1.setDatumOcenjivanja(new Date());
							save(o1);
							kreiraneOcene.add(o1);
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
			save(rating);
			
		}
		if(kreiraneOcene.size()== 0)
		{
			return null;
		}
		
		return kreiraneOcene;
	}
}
