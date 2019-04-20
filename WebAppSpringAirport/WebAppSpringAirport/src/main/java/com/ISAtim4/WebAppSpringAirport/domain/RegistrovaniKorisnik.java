package com.ISAtim4.WebAppSpringAirport.domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.springframework.security.core.GrantedAuthority;

/*
 * Milan: vracena diskriminatorska vrednost da se u tabeli korisnik razlikuju tipovi
 */
@Entity
@DiscriminatorValue("rk")
public class RegistrovaniKorisnik extends Korisnik {
	/*
	private Set<RegistrovaniKorisnik> listaPrijatelja = new HashSet<>();
	private Set<Rezervacija> listaRezervacija = new HashSet<>();
	private Set<ZahtevPrijateljstvo> listaZahtevaPrijateljstvo = new HashSet<>();
	private Set<Pozivnica> listaPozivnicaLet = new HashSet<>();
	*/
	public RegistrovaniKorisnik() {
		super();
		/*
		
		this.listaPrijatelja = listaPrijatelja;
		this.listaRezervacija = listaRezervacija;
		this.listaZahtevaPrijateljstvo = listaZahtevaPrijateljstvo;
		this.listaPozivnicaLet = listaPozivnicaLet;*/
	}
/*
	public Set<RegistrovaniKorisnik> getListaPrijatelja() {
		return listaPrijatelja;
	}
	public void setListaPrijatelja(Set<RegistrovaniKorisnik> listaPrijatelja) {
		this.listaPrijatelja = listaPrijatelja;
	}
	public Set<Rezervacija> getListaRezervacija() {
		return listaRezervacija;
	}
	public void setListaRezervacija(Set<Rezervacija> listaRezervacija) {
		this.listaRezervacija = listaRezervacija;
	}
	public Set<ZahtevPrijateljstvo> getListaZahtevaPrijateljstvo() {
		return listaZahtevaPrijateljstvo;
	}
	public void setListaZahtevaPrijateljstvo(
			Set<ZahtevPrijateljstvo> listaZahtevaPrijateljstvo) {
		this.listaZahtevaPrijateljstvo = listaZahtevaPrijateljstvo;
	}
	public Set<Pozivnica> getListaPozivnicaLet() {
		return listaPozivnicaLet;
	}
	public void setListaPozivnicaLet(Set<Pozivnica> listaPozivnicaLet) {
		this.listaPozivnicaLet = listaPozivnicaLet;
	}
*/

	
}
