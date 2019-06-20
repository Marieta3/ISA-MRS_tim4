package com.ISAtim4.WebAppSpringAirport.service;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.ISAtim4.WebAppSpringAirport.domain.Hotel;
import com.ISAtim4.WebAppSpringAirport.domain.Ocena;
import com.ISAtim4.WebAppSpringAirport.repository.HotelRepository;

@Service
@Transactional(readOnly = true)
public class HotelService {
	@Autowired
	private HotelRepository hotelRepository;
	
	@Autowired
	private OcenaService ocenaService;

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
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Hotel create(Hotel hotel) {
		hotel.setCoord1(31.214535);
		hotel.setCoord2(29.945663);
		return save(hotel);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Hotel update(Long hotelId,Hotel hotelDetalji) {
		Hotel hotel = findOne(hotelId);
		if (hotel == null) {
			return null;
		}

		hotel.setNaziv(hotelDetalji.getNaziv());
		hotel.setAdresa(hotelDetalji.getAdresa());
		hotel.setOpis(hotelDetalji.getOpis());
		hotel.setSlika(hotelDetalji.getSlika());
		hotel.setCoord1(hotelDetalji.getCoord1());
		hotel.setCoord2(hotelDetalji.getCoord2());
		List<Ocena> ocene = ocenaService.findAllByHotel(hotel);
		hotel.setOcena(Ocena.getProsek(ocene));
		Hotel updateHotel = save(hotel);
		return updateHotel;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean delete(Long hotelId) {
		System.out.println("\n\n\t"+hotelId+"\n\n");
		Hotel hotel = findOne(hotelId);
		System.out.println("\n\n\t"+hotel+"\n\n");
		if (hotel != null) {
			remove(hotelId);
			return true;
		} else {
			return false;
		}
	}
}
