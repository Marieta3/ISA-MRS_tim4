package com.ISAtim4.WebAppSpringAirport.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ISAtim4.WebAppSpringAirport.domain.Bodovanje;
import com.ISAtim4.WebAppSpringAirport.dto.BodovanjeDTO;
import com.ISAtim4.WebAppSpringAirport.service.BodovanjeService;

@RestController
public class BodovanjeController {
	
	@Autowired
	private BodovanjeService bodovanjeService;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping(value = "/api/points/", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Bodovanje> getBodovanje() {
		List<Bodovanje> asd  = bodovanjeService.findAll();

		Bodovanje retVal = new Bodovanje();
		if(asd.size()== 1)
		{
			retVal = asd.get(0);
		}else if(asd.size()>= 1)
		{
			for (int i = 0; i < asd.size(); i++) {
				if (i==0)
				{
					retVal = asd.get(0);
					continue;
				}
				Bodovanje x = asd.remove(i);
				bodovanjeService.remove(x.getId());
			}
		}


		return ResponseEntity.ok().body(retVal);
	}
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping(value = "/api/points", produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Bodovanje> changeBodovanje(@Valid @RequestBody BodovanjeDTO b) {
		List<Bodovanje> asd  = bodovanjeService.findAll();

		Bodovanje retVal = new Bodovanje();
		if(asd.size()== 1)
		{
			retVal = asd.get(0);
		}else if(asd.size()>= 1)
		{
			for (int i = 0; i < asd.size(); i++) {
				if (i==0)
				{
					retVal = asd.get(0);
					continue;
				}
				Bodovanje x = asd.remove(i);
				bodovanjeService.remove(x.getId());
			}
		}
		
		retVal.setKmZaBroj(b.getKmZaBroj());
		retVal.setMaxBroj(b.getMaxBroj());

		bodovanjeService.save(retVal);
		
		return ResponseEntity.ok().body(retVal);
	}
}
