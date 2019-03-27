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
		Collection<Vozilo> korisnici = VoziloRepository.findAll();
        logger.info("< findAll");
        return korisnici;
	}

	@Override
	public Vozilo findOne(Long id) {
		logger.info("> findOne id:{}", id);
        Vozilo Vozilo = VoziloRepository.findOne(id);
        logger.info("< findOne id:{}", id);
        return Vozilo;
	}

	@Override
	public Vozilo create(Vozilo Vozilo) throws Exception {
		logger.info("> create");
        if (Vozilo.getId() != null) {
            logger.error(
                    "Pokusaj kreiranja novog entiteta, ali Id nije null.");
            throw new Exception(
                    "Id mora biti null prilikom perzistencije novog entiteta.");
        }
        Vozilo snimljenVozilo = VoziloRepository.create(Vozilo);
        logger.info("< create");
        return snimljenVozilo;
	}

	@Override
	public Vozilo update(Vozilo Vozilo) throws Exception {
        logger.info("> update id:{}", Vozilo.getId());
        Vozilo VoziloToUpgrade = findOne(Vozilo.getId());
        if (VoziloToUpgrade == null) {
            logger.error(
                    "Pokusaj azuriranja entiteta, ali je on nepostojeci.");
            throw new Exception("Trazeni entitet nije pronadjen.");
        }
        VoziloToUpgrade.setProizvodjac(Vozilo.getProizvodjac());
        VoziloToUpgrade.setModel(Vozilo.getModel());;
        VoziloToUpgrade.setGodina(Vozilo.getGodina());
        VoziloToUpgrade.setTablica(Vozilo.getTablica());
        VoziloToUpgrade.setCena(Vozilo.getCena());
        
        Vozilo updatedVozilo = VoziloRepository.create(VoziloToUpgrade);
        logger.info("< update id:{}", Vozilo.getId());
        return updatedVozilo;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}
