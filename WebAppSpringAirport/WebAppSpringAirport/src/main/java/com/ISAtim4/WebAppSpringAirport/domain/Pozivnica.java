package com.ISAtim4.WebAppSpringAirport.domain;

import java.util.Date;

public class Pozivnica {
	private Long id;
	private RegistrovaniKorisnik koSalje;
	private RegistrovaniKorisnik komeSalje;
	private Rezervacija rezervacija;
	private Boolean prihvacen; //da li je prihvacen ili nije
	private Boolean reagovanoNaPoziv; //da li je isteklo vreme
	private Date datumSlanja;
}
