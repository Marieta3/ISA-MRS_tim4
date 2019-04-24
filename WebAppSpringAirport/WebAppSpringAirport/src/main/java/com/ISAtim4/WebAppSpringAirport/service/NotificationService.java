package com.ISAtim4.WebAppSpringAirport.service;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ISAtim4.WebAppSpringAirport.domain.Korisnik;

@Service
public class NotificationService {
	
	@Bean
	public JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(587);
	     
	    mailSender.setUsername("dzonisrb13@gmail.com");
	    mailSender.setPassword("moj_password"); 
	     
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
	     
	    return mailSender;
	}
	public void sendNotification(Korisnik korisnik) throws MailException{
		//send email
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(korisnik.getMail());
		mail.setFrom("dzonisrb13@gmail.com");
		mail.setSubject("Verification mail for user ");//+korisnik.getUsername());
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String uname = bCryptPasswordEncoder.encode(korisnik.getUsername());
		mail.setText("Verification url is: http://localhost:8080/enabled/");
		//+uname+			". Click link to verify account!");
		getJavaMailSender().send(mail);
	}
}
