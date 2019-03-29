package com.ISAtim4.WebAppSpringAirport.domain;

import java.util.Date;

public class ZahtevPrijateljstvo {
	private Long id;
	private RegistrovaniKorisnik koSalje;
	private RegistrovaniKorisnik komeSalje;
	private Boolean prihvacen; //da li je prihvacen ili nije
	private Date datumSlanja;
}
