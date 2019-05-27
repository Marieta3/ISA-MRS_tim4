package com.ISAtim4.WebAppSpringAirport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ISAtim4.WebAppSpringAirport.domain.AvioKompanija;
import com.ISAtim4.WebAppSpringAirport.domain.Hotel;
import com.ISAtim4.WebAppSpringAirport.domain.Let;
import com.ISAtim4.WebAppSpringAirport.domain.Ocena;
import com.ISAtim4.WebAppSpringAirport.domain.RentACar;
import com.ISAtim4.WebAppSpringAirport.domain.Rezervacija;
import com.ISAtim4.WebAppSpringAirport.domain.Soba;
import com.ISAtim4.WebAppSpringAirport.domain.Vozilo;

public interface OcenaRepository extends JpaRepository<Ocena, Long> {

	List<Ocena> findAllByRent(RentACar r);

	List<Ocena> findAllByVozilo(Vozilo v);

	List<Ocena> findAllByLet(Let l);

	List<Ocena> findAllByAvio(AvioKompanija a);

	List<Ocena> findAllByHotel(Hotel h);

	List<Ocena> findAllBySoba(Soba s);

	List<Ocena> findAllByRezervacija(Rezervacija r);

}
