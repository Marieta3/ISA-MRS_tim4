package com.ISAtim4.WebAppSpringAirport.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ISAtim4.WebAppSpringAirport.domain.Prijateljstvo;
import com.ISAtim4.WebAppSpringAirport.domain.RegistrovaniKorisnik;

public interface PrijateljstvoRepository extends
		JpaRepository<Prijateljstvo, Long> {

	@Query("select p from Prijateljstvo p where ( p.receiver = ?1 or p.sender = ?1) and p.accepted = true and p.reacted = true")
	List<Prijateljstvo> FindMyFriends(RegistrovaniKorisnik k);

	@Query("select p from Prijateljstvo p where ( (p.receiver = ?1 and p.sender = ?2) or (p.receiver = ?2 and p.sender = ?1)) and p.accepted = true and p.reacted = true")
	Prijateljstvo FindOneFriend(RegistrovaniKorisnik me,
			RegistrovaniKorisnik friend);
	
	@Query("select p from Prijateljstvo p where p.receiver = ?1 and p.accepted = false and p.reacted=false")
	List<Prijateljstvo> FindMyRequests(RegistrovaniKorisnik me);
	
	@Query("select p from Prijateljstvo p where p.receiver = ?1 and p.sender = ?2 and p.accepted = false and p.reacted=false")
	Prijateljstvo findOneRequest(RegistrovaniKorisnik me, RegistrovaniKorisnik friend);
}
