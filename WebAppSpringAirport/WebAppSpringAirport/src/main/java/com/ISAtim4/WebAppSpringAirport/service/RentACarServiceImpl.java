package com.ISAtim4.WebAppSpringAirport.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ISAtim4.WebAppSpringAirport.domain.RentACar;
import com.ISAtim4.WebAppSpringAirport.repository.InMemoryRentacarRepository;


@Service
public class RentACarServiceImpl implements RentACarService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private InMemoryRentacarRepository rentACarRepository;
	
	
	@Override
	public Collection<RentACar> findAll() {
		logger.info("> findAll");
        Collection<RentACar> rentACars = rentACarRepository.findAll();
        logger.info("< findAll");
        return rentACars;
	}

	@Override
	public RentACar findOne(Long id) {
		logger.info("> findOne id:{}", id);
		RentACar rentACar = rentACarRepository.findOne(id);
        logger.info("< findOne id:{}", id);
        return rentACar;
	}

	@Override
	public RentACar create(RentACar rentACar) throws Exception {
		logger.info("> create");
        if (rentACar.getId() != null) {
            logger.error(
                    "Pokusaj kreiranja novog entiteta, ali Id nije null.");
            throw new Exception(
                    "Id mora biti null prilikom perzistencije novog entiteta.");
        }
        RentACar savedrentACar = rentACarRepository.create(rentACar);
        logger.info("< create");
        return savedrentACar;
	}

	@Override
	public RentACar update(RentACar rentACar) throws Exception {
        logger.info("> update id:{}", rentACar.getId());
        RentACar rentACarToUpgrade = findOne(rentACar.getId());
        if (rentACarToUpgrade == null) {
            logger.error(
                    "Pokusaj azuriranja entiteta, ali je on nepostojeci.");
            throw new Exception("Trazeni entitet nije pronadjen.");
        }
        rentACarToUpgrade.setNaziv(rentACar.getNaziv());
        rentACarToUpgrade.setAdresa(rentACar.getAdresa());
        rentACarToUpgrade.setOpis(rentACar.getOpis());
        
        RentACar updatedrentACar = rentACarRepository.create(rentACarToUpgrade);
        logger.info("< update id:{}", rentACar.getId());
        return updatedrentACar;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}
