package com.ISAtim4.WebAppSpringAirport.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ISAtim4.WebAppSpringAirport.domain.Korisnik;
import com.ISAtim4.WebAppSpringAirport.repository.InMemoryKorisnikRepository;

@Service
public class KorisnikServiceImpl implements KorisnikService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private InMemoryKorisnikRepository korisnikRepository;
	
	
	@Override
	public Collection<Korisnik> findAll() {
		logger.info("> findAll");
        Collection<Korisnik> korisnici = korisnikRepository.findAll();
        logger.info("< findAll");
        return korisnici;
	}

	@Override
	public Korisnik findOne(Long id) {
		logger.info("> findOne id:{}", id);
        Korisnik korisnik = korisnikRepository.findOne(id);
        logger.info("< findOne id:{}", id);
        return korisnik;
	}

	@Override
	public Korisnik create(Korisnik korisnik) throws Exception {
		logger.info("> create");
        if (korisnik.getId() != null) {
            logger.error(
                    "Pokusaj kreiranja novog entiteta, ali Id nije null.");
            throw new Exception(
                    "Id mora biti null prilikom perzistencije novog entiteta.");
        }
        Korisnik snimljenKorisnik = korisnikRepository.create(korisnik);
        logger.info("< create");
        return snimljenKorisnik;
	}

	@Override
	public Korisnik update(Korisnik korisnik) throws Exception {
        logger.info("> update id:{}", korisnik.getId());
        Korisnik korisnikToUpgrade = findOne(korisnik.getId());
        if (korisnikToUpgrade == null) {
            logger.error(
                    "Pokusaj azuriranja entiteta, ali je on nepostojeci.");
            throw new Exception("Trazeni entitet nije pronadjen.");
        }
        korisnikToUpgrade.setKorisnickoIme(korisnik.getKorisnickoIme());
        korisnikToUpgrade.setLozinka(korisnik.getLozinka());
        korisnikToUpgrade.setMail(korisnik.getMail());
        
        Korisnik updatedKorisnik = korisnikRepository.create(korisnikToUpgrade);
        logger.info("< update id:{}", korisnik.getId());
        return updatedKorisnik;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}
}
