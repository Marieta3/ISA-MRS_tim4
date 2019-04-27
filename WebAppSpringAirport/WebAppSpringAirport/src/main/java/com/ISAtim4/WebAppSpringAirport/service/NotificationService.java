package com.ISAtim4.WebAppSpringAirport.service;

import java.util.ArrayList;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ISAtim4.WebAppSpringAirport.domain.AdminAvio;
import com.ISAtim4.WebAppSpringAirport.domain.Authority;
import com.ISAtim4.WebAppSpringAirport.domain.Korisnik;

@Service
public class NotificationService {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendNotification(Korisnik korisnik) throws MailException,InterruptedException{
		//send email
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(korisnik.getMail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject("Verification mail for user ");//+korisnik.getUsername());
		String originalInput = "test input";
		String uname = Base64.getEncoder().encodeToString(korisnik.getKorisnickoIme().getBytes());
		mail.setText("Verification url is: http://localhost:8080/api/users/enabled/"+uname+". Click link to verify account!");
		javaMailSender.send(mail);
	}
	
	public void sendPswToAdmin(Korisnik k, String lozinka)throws MailException,InterruptedException {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(k.getMail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		ArrayList<Authority> a=(ArrayList<Authority>) k.getAuthorities();
		String uloga=a.get(0).getAuthority();
		mail.setSubject("Verification mail for "+uloga);//+korisnik.getUsername());
		
		
		mail.setText("Your temporary password is "+lozinka+". Please change this password on your first login.");
		javaMailSender.send(mail);
	}
}
