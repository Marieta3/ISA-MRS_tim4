package com.ISAtim4.WebAppSpringAirport.service;

import java.util.ArrayList;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ISAtim4.WebAppSpringAirport.domain.Authority;
import com.ISAtim4.WebAppSpringAirport.domain.Korisnik;
import com.ISAtim4.WebAppSpringAirport.domain.NeregistrovaniPutnik;
import com.ISAtim4.WebAppSpringAirport.domain.Pozivnica;
import com.ISAtim4.WebAppSpringAirport.domain.Sediste;
import com.ISAtim4.WebAppSpringAirport.domain.Soba;
import com.ISAtim4.WebAppSpringAirport.domain.Vozilo;

@Service
public class NotificationService {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendNotification(Korisnik korisnik) throws MailException{
		//send email
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(korisnik.getMail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Verification mail for user ");//+korisnik.getUsername());
		//String originalInput = "test input";
		String uname = Base64.getEncoder().encodeToString(korisnik.getKorisnickoIme().getBytes());
		mail.setText("Verification url is: http://localhost:8080/api/users/enabled/"+uname+". Click link to verify account!");
		javaMailSender.send(mail);
	}
	
	public void sendInvitation(Korisnik korisnik, Pozivnica pozivnica) throws MailException{
		//send email
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(pozivnica.getKomeSalje().getMail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Flight invitation for you ");//+korisnik.getUsername());
		//String originalInput = "test input";
		//String uname = Base64.getEncoder().encodeToString(korisnik.getKorisnickoIme().getBytes());
		String str= "";
		str = "Dear, you have invitation for flight with your friend. You can accept or reject the call and also, you can see more travel information at your profile on WebAppSpringAirport app."
				+ "Link to app: http://localhost:8080/." ;
		str += "\nReservation info: ";
		str += "\n\t- Date of reservation: " + pozivnica.getRezervacija().getDatumRezervacije().toString();
		str += "\n\t- Price: " + pozivnica.getRezervacija().getCena().toString();
		str += "\n\t- Seats: \n";
		for(Sediste s: pozivnica.getRezervacija().getOdabranaSedista()){
			str += "\t\t== "+s.getRow_col() + ", klasa: " + s.getKlasa() + "\n";
		}
		str += "\n\t- Vehicle: ";
		if (!pozivnica.getRezervacija().getOdabranaVozila().isEmpty()){
			for (Vozilo v: pozivnica.getRezervacija().getOdabranaVozila()){
				str += "\n\t\t== "+v.getProizvodjac() + " " + v.getModel();
			}
		} else {
			str += " No vehicle reserved.";
		}
		
		str += "\n\t- Rooms in hotel: ";
		if (!pozivnica.getRezervacija().getOdabranaVozila().isEmpty()){
			for (Soba s: pozivnica.getRezervacija().getOdabraneSobe()){
				str += "\n\t\t== Inside hotel: "+s.getHotel() + ", room with " + s.getBrojKreveta() + " bed/s";
			}
		} else {
			str += " No rooms reserved.";
		}
		mail.setText(str);
		javaMailSender.send(mail);
	}
	
	public void sendPswToAdmin(Korisnik k, String lozinka)throws MailException {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(k.getMail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		@SuppressWarnings("unchecked")
		ArrayList<Authority> a=(ArrayList<Authority>) k.getAuthorities();
		String uloga=a.get(0).getAuthority();
		mail.setSubject("Verification mail for "+uloga);//+korisnik.getUsername());
		mail.setText("Your username is " + k.getKorisnickoIme()+ " .Your temporary password is "+lozinka+". Please change this password on your first login.");
		javaMailSender.send(mail);
	}

	public void sendInvitationNereg(NeregistrovaniPutnik nereg) throws MailException {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(nereg.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Flight invitation for you ");//+korisnik.getUsername());
		//String originalInput = "test input";
		//String uname = Base64.getEncoder().encodeToString(korisnik.getKorisnickoIme().getBytes());
		mail.setText("Dear, you have invitation for flight with your friend. You can accept or reject the call and also, you can see more travel information at your profile on WebAppSpringAirport app.");
		javaMailSender.send(mail);
	}
}
