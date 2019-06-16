package com.ISAtim4.WebAppSpringAirport.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ISAtim4.WebAppSpringAirport.domain.Hotel;
import com.ISAtim4.WebAppSpringAirport.repository.HotelRepository;

@Service
@Transactional(readOnly = true)
public class HotelService {
	@Autowired
	private HotelRepository hotelRepository;

	@Transactional(readOnly = false)
	public Hotel save(Hotel hotel) {
		return hotelRepository.save(hotel);
	}

	public Hotel findOne(Long id) {
		return hotelRepository.getOne(id);
	}

	public List<Hotel> findAll() {
		return hotelRepository.findAll();
	}

	public Page<Hotel> findAll(Pageable page) {
		return hotelRepository.findAll(page);
	}

	public List<Hotel> findByName(String name) {
		return hotelRepository.findAllByNaziv(name);
	}

	public List<Hotel> containsName(String name) {
		return hotelRepository.pronadjiHotelSadrziNaziv(name);
	}

	@Transactional(readOnly = false)
	public void remove(Long id) {
		hotelRepository.deleteById(id);
	}
	
	public List<Hotel> searchHotelsLocation(String lokacija, Date datumPolaska, Date datumDolaska){
		return hotelRepository.searchHotelsLocation(lokacija,datumPolaska,datumDolaska);
	}
	
	public List<Hotel> searchHotelsName(String nazivHotela, Date datumPolaska, Date datumDolaska){
		return hotelRepository.searchHotelsName(nazivHotela,datumPolaska,datumDolaska);
	}
	
	/*public List<Hotel> searchHotelsNameDate(String nazivHotela, Date datumPolaska, Date datumDolaska){
		return hotelRepository.searchHotelsNameDate(nazivHotela,datumPolaska,datumDolaska);
	}
	
	public List<Hotel> searchHotelsLocationDate(String lokacija, Date datumPolaska, Date datumDolaska){
		return hotelRepository.searchHotelsLocationDate(lokacija,datumPolaska,datumDolaska);
	}*/
	
}
