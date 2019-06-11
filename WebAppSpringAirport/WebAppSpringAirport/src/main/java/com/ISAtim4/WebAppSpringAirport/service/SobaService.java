package com.ISAtim4.WebAppSpringAirport.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ISAtim4.WebAppSpringAirport.domain.Hotel;
import com.ISAtim4.WebAppSpringAirport.domain.Soba;
import com.ISAtim4.WebAppSpringAirport.domain.Vozilo;
import com.ISAtim4.WebAppSpringAirport.repository.SobaRepository;

@Service
public class SobaService {
	@Autowired
	private SobaRepository sobaRepository;

	public Soba save(Soba soba) {
		return sobaRepository.save(soba);
	}

	public Soba findOne(Long id) {
		return sobaRepository.getOne(id);
	}

	public List<Soba> findAll() {
		return sobaRepository.findAll();
	}

	public Page<Soba> findAll(Pageable page) {
		return sobaRepository.findAll(page);
	}

	public void remove(Long id) {
		sobaRepository.deleteById(id);
	}

	public List<Soba> findAllByHotel(Hotel hotel) {
		return sobaRepository.findAllByHotel(hotel);
	}
	
	/*public void updateReservedRooms(List<Long> ids) {
		sobaRepository.updateReservedRooms(ids);
	}*/
	public Set<Soba> findSobeIds(List<Long> ids) {
		return sobaRepository.findSobeIds(ids);
	}
}
