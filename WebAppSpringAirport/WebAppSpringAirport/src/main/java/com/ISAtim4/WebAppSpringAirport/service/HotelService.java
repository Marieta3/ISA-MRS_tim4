package com.ISAtim4.WebAppSpringAirport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ISAtim4.WebAppSpringAirport.domain.Hotel;
import com.ISAtim4.WebAppSpringAirport.repository.HotelRepository;

@Service
public class HotelService {
	@Autowired
	private HotelRepository hotelRepository;

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

	public void remove(Long id) {
		hotelRepository.deleteById(id);
	}
}
