package com.ISAtim4.WebAppSpringAirport.controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ISAtim4.WebAppSpringAirport.domain.AvioKompanija;
import com.ISAtim4.WebAppSpringAirport.domain.Let;
import com.ISAtim4.WebAppSpringAirport.domain.Ocena;
import com.ISAtim4.WebAppSpringAirport.domain.Rezervacija;
import com.ISAtim4.WebAppSpringAirport.domain.Sediste;
import com.ISAtim4.WebAppSpringAirport.dto.Chart1DTO;
import com.ISAtim4.WebAppSpringAirport.dto.Chart2DTO;
import com.ISAtim4.WebAppSpringAirport.dto.Chart3DTO;
import com.ISAtim4.WebAppSpringAirport.dto.LetDTO;
import com.ISAtim4.WebAppSpringAirport.service.AvioKompanijaService;
import com.ISAtim4.WebAppSpringAirport.service.LetService;
import com.ISAtim4.WebAppSpringAirport.service.OcenaService;
import com.ISAtim4.WebAppSpringAirport.service.RezervacijaService;

@RestController
public class AvioKompanijaController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AvioKompanijaService aviokompanijaService;
	
	@Autowired
	private LetService letService;
	
	@Autowired
	private RezervacijaService rezervacijaService;
	
	@Autowired
	private OcenaService ocenaService;

	/* da snimimo avioKompaniju */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.POST,value = "/api/avioKompanije", produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public AvioKompanija createAvioKompanija(@Valid @RequestBody AvioKompanija avioKompanija) {
		return aviokompanijaService.create(avioKompanija);
	}

	/* da uzmemo sve avioKompanije, svima dozvoljeno */
	@RequestMapping(method = RequestMethod.GET,value = "/api/avioKompanije", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AvioKompanija> getAllAvioKompanije() {
		List<AvioKompanija> avios = aviokompanijaService.findAll();
		for (AvioKompanija a : avios) {
			List<Ocena> ocene = ocenaService.findAllByAvio(a);
			a.setOcena(Ocena.getProsek(ocene));
		}
		return avios;
	}

	/* da uzmemo avioKompaniju po id-u, svima dozvoljeno*/
	@RequestMapping(method = RequestMethod.GET,value = "/api/avioKompanije/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AvioKompanija> getAvioKompanija(
			@PathVariable(value = "id") Long idAviokompanije) {
		AvioKompanija aviokompanija=aviokompanijaService.getAvioKompanija(idAviokompanije);
		if (aviokompanija == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok().body(aviokompanija);
	}
	
	/* da uzmemo letove po id-u aviokompanije, svima dozvoljeno*/
	@RequestMapping(method = RequestMethod.GET,value = "/api/avioKompanije/flights/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Let>> getFlightsOfAvioKompanija(
			@PathVariable(value = "id") Long idAviokompanije) {
		AvioKompanija aviokompanija = aviokompanijaService.findOne(idAviokompanije);

		if (aviokompanija == null) {
			return ResponseEntity.notFound().build();
		}
		
		//ocene
		ArrayList<Let> listaLetova = new ArrayList<>();
		
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		for (Let let : aviokompanija.getListaLetova()) {
			List<Ocena> ocene = ocenaService.findAllByLet(let);
			let.setOcena(Ocena.getProsek(ocene));
			listaLetova.add(let);
		}
		return ResponseEntity.ok().body(listaLetova);
	}
	
	/* pretraga letova start, end, startdate, enddate*/
	@RequestMapping(method = RequestMethod.POST,value = "/api/avioKompanije/searchFlights/{id}", consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ArrayList<Let>> pretragaLetovaAvioKompanija(
			@PathVariable(value = "id") Long idAviokompanije,
			@Valid @RequestBody LetDTO sLet) {
		AvioKompanija aviokompanija = aviokompanijaService.findOne(idAviokompanije);
		
		if (aviokompanija == null) {
			return ResponseEntity.notFound().build();
		}
		
		ArrayList<Let> listaLetova = new ArrayList<>();
		String start;
		String end;
		Date startDate;
		Date endDate;
		start = sLet.getMestoPolaska();
		end = sLet.getMestoDolaska();
		startDate = sLet.getVreme1();
		endDate = sLet.getVreme2();
		logger.info(start + end + startDate.toString() + endDate.toString());
		if(!endDate.after(startDate)){
			return ResponseEntity.badRequest().build();
		}
		
		for (Let let : aviokompanija.getListaLetova()) {
			if(let.getPocetnaDestinacija().trim().toLowerCase().contains(start.trim().toLowerCase()) &&
					let.getKrajnjaDestinacija().trim().toLowerCase().contains(end.trim().toLowerCase())){
				if((let.getVremePolaska().after(startDate)&&let.getVremePolaska().before(endDate))||
					(let.getVremeDolaska().after(startDate)&&let.getVremePolaska().before(endDate))	)
				{
					listaLetova.add(let);
				}
			}
		}
		return ResponseEntity.ok().body(listaLetova);
	}
	
	/* da uzmemo avioKompaniju po nazivu, svima dozvoljeno */
	
	@RequestMapping(method = RequestMethod.GET,value = "/api/avioKompanije/search/{name}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AvioKompanija>> getAvioKompanijaByName(
			@PathVariable(value = "name") String avioKompanijaName) {
		List<AvioKompanija> avioKompanije = aviokompanijaService.containsName(avioKompanijaName);

		if (avioKompanije == null) {
			return ResponseEntity.notFound().build();
		}
		for (AvioKompanija a : avioKompanije) {
			List<Ocena> ocene = ocenaService.findAllByAvio(a);
			a.setOcena(Ocena.getProsek(ocene));
		}
		return ResponseEntity.ok().body(avioKompanije);
	}
	
	@PreAuthorize("hasRole('ROLE_AVIO')")
	@RequestMapping(method = RequestMethod.GET,value = "/api/avioKompanije/chart1/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Chart1DTO> getChart1(
			@PathVariable(value = "id") Long avioId) {
		AvioKompanija avio = aviokompanijaService.findOne(avioId);
		
		if (avio == null) {
			return ResponseEntity.notFound().build();
		}
		
		List<Ocena> ocene = ocenaService.findAllByAvio(avio);
		avio.setOcena(Ocena.getProsek(ocene));
		
		
		double sum = 0.0;
		int i = 0;
		
		List<AvioKompanija> avios = aviokompanijaService.findAll();
		for (AvioKompanija r : avios) {
			List<Ocena> oceneR = ocenaService.findAllByAvio(r);
			r.setOcena(Ocena.getProsek(oceneR));
			if(r.getOcena()!= 0.0)
			{
				++i;
				sum += r.getOcena();
			}
		}
		double avg = 0.0;
		if(i != 0){
			avg =  sum/i;
		}
		avg = BigDecimal.valueOf(avg)
			    .setScale(2, RoundingMode.HALF_UP)
			    .doubleValue();
		
		Chart1DTO retval = new Chart1DTO();
		retval.setService(avio.getNaziv());
		retval.setServiceRating(avio.getOcena());
		retval.setOthers("Services average");
		retval.setOthersRating(avg);
		
		return ResponseEntity.ok().body(retval);
	}
	
	@PreAuthorize("hasRole('ROLE_AVIO')")
	@RequestMapping(method = RequestMethod.GET,value = "/api/avioKompanije/chart2/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Chart2DTO>> getChart2(
			@PathVariable(value = "id") Long avioId) {
		AvioKompanija avio = aviokompanijaService.findOne(avioId);
		Set<Let> flights =  avio.getListaLetova();

		List<Chart2DTO> retVal = new ArrayList<>();
		
		double sum = 0.0;
		int i = 0;

		for (Let v : flights) {
			List<Ocena> ocene = ocenaService.findAllByLet(v);
			v.setOcena(Ocena.getProsek(ocene));
			if(v.getOcena()!= 0.0)
			{
				++i;
				sum += v.getOcena();
			}
			Chart2DTO r2 = new Chart2DTO();
			r2.setCar(v.getPocetnaDestinacija() + "-" + v.getKrajnjaDestinacija());
			r2.setCarRating(v.getOcena());
			retVal.add(r2);
		}
		

		double avg = 0.0;
		if(i != 0){
			avg =  sum/i;
		}
		avg = BigDecimal.valueOf(avg)
			    .setScale(2, RoundingMode.HALF_UP)
			    .doubleValue();
		
		retVal.add(new Chart2DTO("Average", avg));
		
		return ResponseEntity.ok().body(retVal);
	}
	
	@PreAuthorize("hasRole('ROLE_AVIO')")
	@RequestMapping(method = RequestMethod.POST,value = "/api/avioKompanije/chart3/daily/{id}",consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Integer>> getChart3Daily(
			@PathVariable(value = "id") Long avioId,
			@Valid @RequestBody Chart3DTO chartData) {
		AvioKompanija avio = aviokompanijaService.findOne(avioId);

		if(!chartData.getType().equals("daily")){
			return ResponseEntity.badRequest().build();
		}
		
		List<Integer> retVal = new ArrayList<>();
		for(int i = 0; i<= 23; i++)
		{
			retVal.add(0);
		}
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date searchDate;
		try {
			searchDate = sf.parse(chartData.getValue());
		} catch (ParseException e) {
			searchDate = new Date();
			e.printStackTrace();
		}
		Calendar searchCal = Calendar.getInstance();
		searchCal.setTime(searchDate);
		
		Calendar rezCal = Calendar.getInstance();
		
		for (Rezervacija r : rezervacijaService.findAll()) {
			rezCal.setTime(r.getDatumRezervacije());
			boolean sameDay = searchCal.get(Calendar.DAY_OF_YEAR) == rezCal.get(Calendar.DAY_OF_YEAR) &&
					searchCal.get(Calendar.YEAR) == rezCal.get(Calendar.YEAR);		
			if(sameDay) {  //svaka rezervacija na taj dan
				for (Sediste s : r.getOdabranaSedista()) {
					if(s.getLet().getAvioKompanija().equals(avio))
					{						    
						int hour = rezCal.get(Calendar.HOUR_OF_DAY);
						int value = retVal.get(hour); 
						value = value + 1; 
						retVal.set(hour, value );
					}
				}
			}
			
		}
		
		return ResponseEntity.ok().body(retVal);
	}
	
	@PreAuthorize("hasRole('ROLE_AVIO')")
	@RequestMapping(method = RequestMethod.POST,value = "/api/avioKompanije/chart3/weekly/{id}",consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Integer>> getChart3Weekly(
			@PathVariable(value = "id") Long avioId,
			@Valid @RequestBody Chart3DTO chartData) {
		AvioKompanija avio = aviokompanijaService.findOne(avioId);
		
		if(!chartData.getType().equals("weekly")){
			return ResponseEntity.badRequest().build();
		}
		String[] dm = chartData.getValue().split("-W");
		int iYear = Integer.parseInt(dm[0]);
		int iWeek = Integer.parseInt(dm[1]); // (months begin with 0) --> -1
		
		Calendar searchCal = Calendar.getInstance();
		searchCal.clear();
		searchCal.set(Calendar.WEEK_OF_YEAR, iWeek);
		searchCal.set(Calendar.YEAR, iYear);
		
		List<Integer> retVal = new ArrayList<>();
		for(int i = 0; i < 7; i++)			//7 dana
		{
			retVal.add(0);
		}

		Calendar rezCal = Calendar.getInstance();
		for (Rezervacija r : rezervacijaService.findAll()) {
			rezCal.setTime(r.getDatumRezervacije());
			boolean sameWeek = searchCal.get(Calendar.YEAR) == rezCal.get(Calendar.YEAR) &&
					searchCal.get(Calendar.WEEK_OF_YEAR) == rezCal.get(Calendar.WEEK_OF_YEAR);				//same month
			if(sameWeek) {  //svaka rezervacija na taj dan
				for (Sediste s : r.getOdabranaSedista()) {
					if(s.getLet().getAvioKompanija().equals(avio))
					{						    
						int day = rezCal.get(Calendar.DAY_OF_WEEK);
						int value = retVal.get(day-2); // DAY_OF_MONTH not zero based, retval is
						value = value + 1; 
						retVal.set(day-2, value );
					}
				}
			}
			
		}
		
		
		return ResponseEntity.ok().body(retVal);
	}
	
	@PreAuthorize("hasRole('ROLE_AVIO')")
	@RequestMapping(method = RequestMethod.POST,value = "/api/avioKompanije/chart3/monthly/{id}",consumes= MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Integer>> getChart3Monthly(
			@PathVariable(value = "id") Long avioId,
			@Valid @RequestBody Chart3DTO chartData) {
		AvioKompanija avio = aviokompanijaService.findOne(avioId);
		
		if(!chartData.getType().equals("monthly")){
			return ResponseEntity.badRequest().build();
		}
		String[] dm = chartData.getValue().split("-");
		int iYear = Integer.parseInt(dm[0]);
		int iMonth = Integer.parseInt(dm[1])-1; // (months begin with 0) --> -1
		int iDay = 1;
		// Create a calendar object and set year and month
		Calendar searchCal = new GregorianCalendar(iYear, iMonth, iDay);
		int daysInMonth = searchCal.getActualMaximum(Calendar.DAY_OF_MONTH);  //retval lenght
		
		List<Integer> retVal = new ArrayList<>();
		for(int i = 0; i < daysInMonth; i++)
		{
			retVal.add(0);
		}
		System.out.println(retVal.size());

		Calendar rezCal = Calendar.getInstance();
		for (Rezervacija r : rezervacijaService.findAll()) {
			rezCal.setTime(r.getDatumRezervacije());
			boolean sameMonth = searchCal.get(Calendar.YEAR) == rezCal.get(Calendar.YEAR) &&
					searchCal.get(Calendar.MONTH) == rezCal.get(Calendar.MONTH);				//same month
			if(sameMonth) {  //svaka rezervacija na taj dan
				for (Sediste s : r.getOdabranaSedista()) {
					if(s.getLet().getAvioKompanija().equals(avio))
					{						    
						int day = rezCal.get(Calendar.DAY_OF_MONTH);
						int value = retVal.get(day-1); // DAY_OF_MONTH not zero based, retval is
						value = value + 1; 
						retVal.set(day-1, value );
					}
				}
			}
			
		}
		
		
		return ResponseEntity.ok().body(retVal);
	}
	
	@PreAuthorize("hasRole('ROLE_AVIO')")
	@RequestMapping(method = RequestMethod.GET,value = "/api/avioKompanije/chart4/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Double>> getChart4(
			@PathVariable(value = "id") Long avioId) {
		AvioKompanija avio = aviokompanijaService.findOne(avioId);
		List<Rezervacija> rezervacije = new ArrayList<>( rezervacijaService.findAll());

		List<Double> retVal = new ArrayList<>();
		for(int i = 0; i<= 11; i++)
		{
			retVal.add(0.0);
		}
		
		Calendar currDate = Calendar.getInstance();
		Calendar reserveDate = Calendar.getInstance();

		currDate.setTime(new Date());
		
		for (Rezervacija r : rezervacije) {
			reserveDate.setTime(r.getDatumRezervacije());
			if(currDate.get(Calendar.YEAR) == reserveDate.get(Calendar.YEAR)) {  //gledamo samo ovogodišnje rezervacije 
					for (Sediste s : r.getOdabranaSedista()) {
						if(s.getLet().getAvioKompanija().equals(avio))
						{						    
						    switch (reserveDate.get(Calendar.MONTH)) {
							case 0:
								retVal.set(0, retVal.get(0) + s.getCena());
								break;
							case 1:
								retVal.set(1, retVal.get(1) + s.getCena());
								break;
							case 2:
								retVal.set(2, retVal.get(2) + s.getCena());
								break;
							case 3:
								retVal.set(3, retVal.get(3) + s.getCena());
								break;
							case 4:
								retVal.set(4, retVal.get(4) + s.getCena());
								break;
							case 5:
								retVal.set(5, retVal.get(5) + s.getCena());
								break;
							case 6:
								retVal.set(6, retVal.get(6) + s.getCena());
								break;
							case 7:
								retVal.set(7, retVal.get(7) + s.getCena());
								break;
							case 8:
								retVal.set(8, retVal.get(8) + s.getCena());
								break;
							case 9:
								retVal.set(9, retVal.get(9) + s.getCena());
								break;
							case 10:
								retVal.set(10, retVal.get(10) + s.getCena());
								break;
							case 11:
								retVal.set(11, retVal.get(11) + s.getCena());
								break;
							default:
								break;
							}
							
						
					}
				}
			}
		}
		
		return ResponseEntity.ok().body(retVal);
	}

	/* update avioKompanije po id-u */
	@PreAuthorize("hasAnyRole('ROLE_AVIO', 'ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.PUT,value = "/api/avioKompanije/{id}", produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AvioKompanija> updateAviokompanije(
			@PathVariable(value = "id") Long aviokompanijaId,
			@Valid @RequestBody AvioKompanija avioKompanijaDetalji) {

		AvioKompanija updateAviokompanija=aviokompanijaService.update(aviokompanijaId, avioKompanijaDetalji);
		if (updateAviokompanija == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(updateAviokompanija);
	}

	/* brisanje avioKompanije */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.DELETE,value = "/api/avioKompanije/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AvioKompanija> deleteAviokompanije(
			@PathVariable(value = "id") Long avioKompanijaId) {
		

		if (aviokompanijaService.delete(avioKompanijaId)) {
			logger.info("Brisanje uspesan!");
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
