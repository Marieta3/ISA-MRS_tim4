package com.ISAtim4.WebAppSpringAirport.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ISAtim4.WebAppSpringAirport.domain.Vozilo;
import com.ISAtim4.WebAppSpringAirport.repository.InMemoryVoziloRepository;

@Service
public class VoziloServiceImpl implements VoziloService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private InMemoryVoziloRepository VoziloRepository;
	
	
	@Override
	public Collection<Vozilo> findAll() {
		logger.info("> findAll");
		Collection<Vozilo> vozila = VoziloRepository.findAll();
        logger.info("< findAll");
        return vozila;
	}

	@Override
	public Vozilo findOne(Long id) {
		logger.info("> findOne id:{}", id);
        Vozilo vozilo = VoziloRepository.findOne(id);
        logger.info("< findOne id:{}", id);
        return vozilo;
	}

	@Override
	public Vozilo create(Vozilo vozilo) throws Exception {
		logger.info("> create");
        if (vozilo.getId() != null) {
            logger.error(
                    "Pokusaj kreiranja novog entiteta, ali Id nije null.");
            throw new Exception(
                    "Id mora biti null prilikom perzistencije novog entiteta.");
        }
        Vozilo snimljenVozilo = VoziloRepository.create(vozilo);
        logger.info("< create");
        return snimljenVozilo;
	}

	@Override
	public Vozilo update(Vozilo vozilo) throws Exception {
        logger.info("> update id:{}", vozilo.getId());
        Vozilo voziloToUpgrade = findOne(vozilo.getId());
        if (voziloToUpgrade == null) {
            logger.error(
                    "Pokusaj azuriranja entiteta, ali je on nepostojeci.");
            throw new Exception("Trazeni entitet nije pronadjen.");
        }
        voziloToUpgrade.setProizvodjac(vozilo.getProizvodjac());
        voziloToUpgrade.setModel(vozilo.getModel());;
        voziloToUpgrade.setGodina(vozilo.getGodina());
        voziloToUpgrade.setTablica(vozilo.getTablica());
        voziloToUpgrade.setCena(vozilo.getCena());
        
        Vozilo updatedVozilo = VoziloRepository.create(voziloToUpgrade);
        logger.info("< update id:{}", vozilo.getId());
        return updatedVozilo;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}
