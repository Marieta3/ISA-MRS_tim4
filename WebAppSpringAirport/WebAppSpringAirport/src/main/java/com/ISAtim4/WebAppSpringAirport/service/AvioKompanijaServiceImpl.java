package com.ISAtim4.WebAppSpringAirport.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ISAtim4.WebAppSpringAirport.domain.AvioKompanija;
import com.ISAtim4.WebAppSpringAirport.repository.InMemoryAvioKompanijaRepository;


@Service
public class AvioKompanijaServiceImpl {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private InMemoryAvioKompanijaRepository avioKompanijaRepository;
	
	
	
	public Collection<AvioKompanija> findAll() {
		logger.info("> findAll");
        Collection<AvioKompanija> avioKompanijas = avioKompanijaRepository.findAll();
        logger.info("< findAll");
        return avioKompanijas;
	}

	
	public AvioKompanija findOne(Long id) {
		logger.info("> findOne id:{}", id);
		AvioKompanija avioKompanija = avioKompanijaRepository.findOne(id);
        logger.info("< findOne id:{}", id);
        return avioKompanija;
	}

	
	public AvioKompanija create(AvioKompanija avioKompanija) throws Exception {
		logger.info("> create");
        if (avioKompanija.getId() != null) {
            logger.error(
                    "Pokusaj kreiranja novog entiteta, ali Id nije null.");
            throw new Exception(
                    "Id mora biti null prilikom perzistencije novog entiteta.");
        }
        AvioKompanija savedavioKompanija = avioKompanijaRepository.create(avioKompanija);
        logger.info("< create");
        return savedavioKompanija;
	}

	
	public AvioKompanija update(AvioKompanija avioKompanija) throws Exception {
        logger.info("> update id:{}", avioKompanija.getId());
        AvioKompanija avioKompanijaToUpgrade = findOne(avioKompanija.getId());
        if (avioKompanijaToUpgrade == null) {
            logger.error(
                    "Pokusaj azuriranja entiteta, ali je on nepostojeci.");
            throw new Exception("Trazeni entitet nije pronadjen.");
        }
        avioKompanijaToUpgrade.setNaziv(avioKompanija.getNaziv());
        //avioKompanijaToUpgrade.setAdresa(avioKompanija.getAdresa());
        //avioKompanijaToUpgrade.setOpis(avioKompanija.getOpis());
        
        AvioKompanija updatedavioKompanija = avioKompanijaRepository.create(avioKompanijaToUpgrade);
        logger.info("< update id:{}", avioKompanija.getId());
        return updatedavioKompanija;
	}

	
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}
