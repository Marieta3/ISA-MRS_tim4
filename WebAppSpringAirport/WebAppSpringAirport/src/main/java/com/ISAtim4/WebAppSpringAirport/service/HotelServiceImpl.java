package com.ISAtim4.WebAppSpringAirport.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ISAtim4.WebAppSpringAirport.domain.Hotel;
import com.ISAtim4.WebAppSpringAirport.repository.InMemoryHotelRepository;





@Service
public class HotelServiceImpl implements HotelService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private InMemoryHotelRepository hotelRepository;
	
	
	@Override
	public Collection<Hotel> findAll() {
		logger.info("> findAll");
        Collection<Hotel> hotels = hotelRepository.findAll();
        logger.info("< findAll");
        return hotels;
	}

	@Override
	public Hotel findOne(Long id) {
		logger.info("> findOne id:{}", id);
        Hotel hotel = hotelRepository.findOne(id);
        logger.info("< findOne id:{}", id);
        return hotel;
	}

	@Override
	public Hotel create(Hotel hotel) throws Exception {
		logger.info("> create");
        if (hotel.getId() != null) {
            logger.error(
                    "Pokusaj kreiranja novog entiteta, ali Id nije null.");
            throw new Exception(
                    "Id mora biti null prilikom perzistencije novog entiteta.");
        }
        Hotel savedHotel = hotelRepository.create(hotel);
        logger.info("< create");
        return savedHotel;
	}

	@Override
	public Hotel update(Hotel hotel) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}
