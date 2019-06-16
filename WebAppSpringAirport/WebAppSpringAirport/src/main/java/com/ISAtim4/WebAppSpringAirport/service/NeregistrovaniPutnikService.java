package com.ISAtim4.WebAppSpringAirport.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ISAtim4.WebAppSpringAirport.domain.Let;
import com.ISAtim4.WebAppSpringAirport.domain.NeregistrovaniPutnik;
import com.ISAtim4.WebAppSpringAirport.repository.NeregistrovaniPutnikRepository;

@Service
@Transactional(readOnly = true)
public class NeregistrovaniPutnikService {
	@Autowired
	private NeregistrovaniPutnikRepository neregistrovaniRepository;
	
	@Transactional(readOnly = false)
	public NeregistrovaniPutnik save(NeregistrovaniPutnik neregistrovaniPutnik) {
		return neregistrovaniRepository.save(neregistrovaniPutnik);
	}

	public NeregistrovaniPutnik findOne(Long id) {
		return neregistrovaniRepository.getOne(id);
	}

	public List<NeregistrovaniPutnik> findAll() {
		return neregistrovaniRepository.findAll();
	}

	public Page<NeregistrovaniPutnik> findAll(Pageable page) {
		return neregistrovaniRepository.findAll(page);
	}

	@Transactional(readOnly = false)
	public void remove(Long id) {
		neregistrovaniRepository.deleteById(id);
	}
}
