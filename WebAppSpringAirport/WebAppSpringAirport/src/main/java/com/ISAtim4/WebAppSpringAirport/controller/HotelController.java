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
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ISAtim4.WebAppSpringAirport.domain.Hotel;
import com.ISAtim4.WebAppSpringAirport.domain.Ocena;
import com.ISAtim4.WebAppSpringAirport.domain.Rezervacija;
import com.ISAtim4.WebAppSpringAirport.domain.Soba;
import com.ISAtim4.WebAppSpringAirport.dto.Chart1DTO;
import com.ISAtim4.WebAppSpringAirport.dto.Chart2DTO;
import com.ISAtim4.WebAppSpringAirport.dto.Chart3DTO;
import com.ISAtim4.WebAppSpringAirport.dto.HotelDTO;
import com.ISAtim4.WebAppSpringAirport.service.HotelService;
import com.ISAtim4.WebAppSpringAirport.service.OcenaService;
import com.ISAtim4.WebAppSpringAirport.service.RezervacijaService;

@RestController
public class HotelController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	HotelService hotelService;
	
	@Autowired
	private OcenaService ocenaService;
	
	@Autowired
	private RezervacijaService rezervacijaService;

	/* da dodamo hotel */
	@RequestMapping(method = RequestMethod.POST,value = "/api/hotels", produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	/*
	 * Milan: trenutno sam dodao proveru da li trenutno
	 * ulogovan korisnik ima rolu ADMIN. Kada sredite model svih korisnika i dodate adekvatne role
	 * onda ce pretpostavljam ovde biti provera "hasRole('HOTEL_ADMIN')"
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public Hotel createHotel(@Valid @RequestBody Hotel hotel) {
		hotel.setCoord1(31.214535);
		hotel.setCoord2(29.945663);
		return hotelService.save(hotel);
	}
	
	//za PRETRAGU HOTELA
	@RequestMapping(method = RequestMethod.POST,value = "/api/hotels/pretraga", produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public List<Hotel> pretragaHotel(@Valid @RequestBody HotelDTO hotel) {
		List<Rezervacija> rezervacije=rezervacijaService.findAll();
		//ArrayList<Hotel> ne_moze=new ArrayList<>();
		ArrayList<Soba> ne_moze=new ArrayList<>();
		ArrayList<Hotel> pronadjeni=new ArrayList<>();
		ArrayList<Hotel> pronadjeni2=new ArrayList<>();
		Date pocetak=hotel.getDatumPolaska();
		Date kraj=hotel.getDatumDolaska();
		for(Rezervacija r: rezervacije) {
			//moze samo ako su oba pre pocetka ili oba posle kraja
			if( (r.getSobaZauzetaOd().compareTo(pocetak)<=0 && r.getSobaZauzetaDo().compareTo(pocetak)>=0) 
					|| (r.getSobaZauzetaOd().compareTo(kraj)<=0 && r.getSobaZauzetaDo().compareTo(kraj) >= 0) 
					|| (r.getSobaZauzetaOd().compareTo(pocetak)>=0 && r.getSobaZauzetaDo().compareTo(kraj)<=0) ) {
				//dodaju se sve sobe koje su zauzete
				for(Soba s: r.getOdabraneSobe()) {
					if(!ne_moze.contains(s)) {
						ne_moze.add(s);
					}
				}
			}
			/*if( (hotel.getDatumPolaska().before(r.getSobaZauzetaOd()) && hotel.getDatumDolaska().before(r.getSobaZauzetaOd())) 
					|| (hotel.getDatumPolaska().after(r.getSobaZauzetaDo()) && hotel.getDatumDolaska().after(r.getSobaZauzetaDo())) ) {
				for(Soba s: r.getOdabraneSobe()) {
					if(!pronadjeni.contains(s.getHotel())) {
						pronadjeni.add(s.getHotel());
					}
				}
			}*/
		}
		ArrayList<Hotel> hoteli=(ArrayList<Hotel>) hotelService.findAll();
		for(Hotel h:hoteli) {
			for(Soba s: h.getSobe()) {
				//ako se soba ne nalazi u listi zauzetih, dodaj hotel kome pripada i iteriraj sledeci hotel
				if(!ne_moze.contains(s)) {
					pronadjeni.add(h);
					break;
				}
			}
		}
		logger.info("pretraga po datumu: "+pronadjeni.size());
		if (hotel.getTipPretrage().equals("location")){
			//pretraga po lokaciji
			for(Hotel h: pronadjeni) {
				List<Ocena> ocene = ocenaService.findAllByHotel(h);
				h.setOcena(Ocena.getProsek(ocene));
				if(h.getAdresa().contains(hotel.getLokNaziv())) {
					pronadjeni2.add(h);
				}
			}
			/*List<Hotel> hoteli = hotelService.searchHotelsLocation(hotel.getLokNaziv(),hotel.getDatumPolaska(),hotel.getDatumDolaska());
			for (Hotel hotel2 : hoteli) {
				List<Ocena> ocene = ocenaService.findAllByHotel(hotel2);
				hotel2.setOcena(Ocena.getProsek(ocene));
			}*/
			return pronadjeni2;
		} else{
			//pretraga po nazivu hotela
			for(Hotel h: pronadjeni) {
				List<Ocena> ocene = ocenaService.findAllByHotel(h);
				h.setOcena(Ocena.getProsek(ocene));
				if(h.getNaziv().contains(hotel.getLokNaziv())) {
					pronadjeni2.add(h);
				}
			}
			/*List<Hotel> hoteli = hotelService.searchHotelsName(hotel.getLokNaziv(),hotel.getDatumPolaska(),hotel.getDatumDolaska());
			for (Hotel hotel2 : hoteli) {
				List<Ocena> ocene = ocenaService.findAllByHotel(hotel2);
				hotel2.setOcena(Ocena.getProsek(ocene));
			}*/
			return pronadjeni2;
		}
	}

	/* da uzmemo sve hotele, svima je dozvoljeno */
	@RequestMapping(method = RequestMethod.GET,value = "/api/hotels", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Hotel> getAllHotels() {
		List<Hotel> hoteli = hotelService.findAll();
		for (Hotel hotel2 : hoteli) {
			List<Ocena> ocene = ocenaService.findAllByHotel(hotel2);
			hotel2.setOcena(Ocena.getProsek(ocene));
		}
		return hoteli;
	}

	/* da uzmemo hotel po id-u, svima dozvoljeno */
	@RequestMapping(method = RequestMethod.GET,value = "/api/hotels/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hotel> getHotel(
			@PathVariable(value = "id") Long hotelId) {
		Hotel hotel = hotelService.findOne(hotelId);

		if (hotel == null) {
			return ResponseEntity.notFound().build();
		}
		List<Ocena> ocene = ocenaService.findAllByHotel(hotel);
		hotel.setOcena(Ocena.getProsek(ocene));
		
		return ResponseEntity.ok().body(hotel);
	}
	
	/* da uzmemo RentAcar po nazivu, svima dozvoljeno */
	
	@RequestMapping(method = RequestMethod.GET,value = "/api/hotels/search/{name}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Hotel>> getHotelsByName(
			@PathVariable(value = "name") String hotelName) {
		List<Hotel> hotels = hotelService.containsName(hotelName);

		if (hotels == null) {
			return ResponseEntity.notFound().build();
		}
		
		for (Hotel hotel2 : hotels) {
			List<Ocena> ocene = ocenaService.findAllByHotel(hotel2);
			hotel2.setOcena(Ocena.getProsek(ocene));
		}
		return ResponseEntity.ok().body(hotels);
	}
	
	@PreAuthorize("hasRole('ROLE_HOTEL')")
	@RequestMapping(method = RequestMethod.GET,value = "/api/hotels/chart1/{id}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Chart1DTO> getChart1(
			@PathVariable(value = "id") Long hotelId) {
		Hotel hotel = hotelService.findOne(hotelId);
		
		if (hotel == null) {
			return ResponseEntity.notFound().build();
		}
		
		List<Ocena> ocene = ocenaService.findAllByHotel(hotel);
		hotel.setOcena(Ocena.getProsek(ocene));
		
		
		double sum = 0.0;
		int i = 0;
		
		List<Hotel> hotels = hotelService.findAll();
		for (Hotel r : hotels) {
			List<Ocena> oceneR = ocenaService.findAllByHotel(r);
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
		retval.setService(hotel.getNaziv());
		retval.setServiceRating(hotel.getOcena());
		retval.setOthers("Services average");
		retval.setOthersRating(avg);
		
		return ResponseEntity.ok().body(retval);
	}
	

	@PreAuthorize("hasRole('ROLE_HOTEL')")
	@RequestMapping(method = RequestMethod.GET,value = "/api/hotels/chart2/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Chart2DTO>> getChart2(
			@PathVariable(value = "id") Long hotelId) {
		Hotel hotel = hotelService.findOne(hotelId);

		List<Chart2DTO> retVal = new ArrayList<>();
		
		double sum = 0.0;
		int i = 0;
		for (Soba soba : hotel.getSobe()) {
			List<Ocena> ocene = ocenaService.findAllBySoba(soba);
			soba.setOcena(Ocena.getProsek(ocene));
			if(soba.getOcena()!= 0.0)
			{
				++i;
				sum += soba.getOcena();
			}
			Chart2DTO r2 = new Chart2DTO();
			r2.setCar(soba.getHotel().getNaziv() + "_" + soba.getId());
			r2.setCarRating(soba.getOcena());
			retVal.add(r2);
		
		}

		double avg = 0.0;
		if(i != 0){
			avg =  sum/i;
		}
		
		retVal.add(new Chart2DTO("Average", avg));
		
		return ResponseEntity.ok().body(retVal);
	}
	
	
	@PreAuthorize("hasRole('ROLE_HOTEL')")
	@RequestMapping(method = RequestMethod.POST,value = "/api/hotels/chart3/daily/{id}",consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Integer>> getChart3Daily(
			@PathVariable(value = "id") Long hotelId,
			@Valid @RequestBody Chart3DTO chartData) {
		Hotel hotel = hotelService.findOne(hotelId);

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
				for (Soba s : r.getOdabraneSobe()) {
					if(s.getHotel().equals(hotel))
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
	
	@PreAuthorize("hasRole('ROLE_HOTEL')")
	@RequestMapping(method = RequestMethod.POST,value = "/api/hotels/chart3/weekly/{id}", consumes= MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Integer>> getChart3Weekly(
			@PathVariable(value = "id") Long hotelId,
			@Valid @RequestBody Chart3DTO chartData) {
		Hotel hotel = hotelService.findOne(hotelId);

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
				for (Soba s : r.getOdabraneSobe()) {
					if(s.getHotel().equals(hotel))
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
	
	@PreAuthorize("hasRole('ROLE_HOTEL')")
	@RequestMapping(method = RequestMethod.POST,value = "/api/hotels/chart3/monthly/{id}", consumes= MediaType.APPLICATION_JSON_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Integer>> getChart3Monthly(
			@PathVariable(value = "id") Long hotelId,
			@Valid @RequestBody Chart3DTO chartData) {
		Hotel hotel = hotelService.findOne(hotelId);
		
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
				for (Soba s : r.getOdabraneSobe()) {
					if(s.getHotel().equals(hotel))
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
	
	@PreAuthorize("hasRole('ROLE_HOTEL')")
	@RequestMapping(method = RequestMethod.POST,value = "/api/hotels/chart4/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Double>> getChart4(
			@PathVariable(value = "id") Long hotelId) {
		Hotel hotel = hotelService.findOne(hotelId);
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
				if(r.getOdabraneSobe().size()!= 0){	//ako je korisnik rezervisao sobu
					for (Soba s : r.getOdabraneSobe()) {
						if(s.getHotel().equals(hotel))
						{
							/*
							 * ili
						    float days = (diff / (1000*60*60*24));
							Math.round(days); //Output is 9 
							itt tesztelsz h melyik a helyes    
							 * */
						    long diff = r.getSobaZauzetaDo().getTime() - r.getSobaZauzetaOd().getTime();
						    int brojDana =  (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
						    logger.info(r.getSobaZauzetaDo().toString());
						    logger.info(r.getSobaZauzetaOd().toString());
						    
						    switch (reserveDate.get(Calendar.MONTH)) {
							case 0:
								retVal.set(0, retVal.get(0) + (brojDana * s.getCena() ));
								break;
							case 1:
								retVal.set(1, retVal.get(1) + (brojDana * s.getCena() ));
								break;
							case 2:
								retVal.set(2, retVal.get(2) + (brojDana * s.getCena() ));
								break;
							case 3:
								retVal.set(3, retVal.get(3) + (brojDana * s.getCena() ));
								break;
							case 4:
								retVal.set(4, retVal.get(4) + (brojDana * s.getCena() ));
								break;
							case 5:
								retVal.set(5, retVal.get(5) + (brojDana * s.getCena() ));
								break;
							case 6:
								retVal.set(6, retVal.get(6) + (brojDana * s.getCena() ));
								break;
							case 7:
								retVal.set(7, retVal.get(7) + (brojDana * s.getCena() ));
								break;
							case 8:
								retVal.set(8, retVal.get(8) + (brojDana * s.getCena() ));
								break;
							case 9:
								retVal.set(9, retVal.get(9) + (brojDana * s.getCena() ));
								break;
							case 10:
								retVal.set(10, retVal.get(10) + (brojDana * s.getCena() ));
								break;
							case 11:
								retVal.set(11, retVal.get(11) + (brojDana * s.getCena() ));
								break;
							default:
								break;
							}
							
						}
					}
				}
			}
		}
		
		return ResponseEntity.ok().body(retVal);
	}


	/* update hotela po id-u */
	@PreAuthorize("hasAnyRole('ROLE_HOTEL', 'ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.PUT,value = "/api/hotels/{id}", produces = MediaType.APPLICATION_JSON_VALUE,consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Hotel> updateHotela(
			@PathVariable(value = "id") Long hotelId,
			@Valid @RequestBody Hotel hotelDetalji) {

		Hotel hotel = hotelService.findOne(hotelId);
		if (hotel == null) {
			return ResponseEntity.notFound().build();
		}

		hotel.setNaziv(hotelDetalji.getNaziv());
		hotel.setAdresa(hotelDetalji.getAdresa());
		hotel.setOpis(hotelDetalji.getOpis());
		hotel.setSlika(hotelDetalji.getSlika());
		hotel.setCoord1(hotelDetalji.getCoord1());
		hotel.setCoord2(hotelDetalji.getCoord2());
		List<Ocena> ocene = ocenaService.findAllByHotel(hotel);
		hotel.setOcena(Ocena.getProsek(ocene));
		Hotel updateHotel = hotelService.save(hotel);
		return ResponseEntity.ok().body(updateHotel);
	}

	/* brisanje hotela */
	@RequestMapping(method = RequestMethod.DELETE,value = "/api/hotels/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Hotel> deleteHotel(
			@PathVariable(value = "id") Long hotelId) {
		System.out.println("\n\n\t"+hotelId+"\n\n");
		Hotel hotel = hotelService.findOne(hotelId);
		System.out.println("\n\n\t"+hotel+"\n\n");
		if (hotel != null) {
			System.out.println("\n\n\t"+"nije null hotel"+"\n\n");
			hotelService.remove(hotelId);
			logger.info("Hotel " + hotelId + " deleted.");
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			logger.error("Hotel not found.");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
