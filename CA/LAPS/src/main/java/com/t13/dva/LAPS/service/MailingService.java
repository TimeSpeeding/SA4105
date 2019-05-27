package com.t13.dva.LAPS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailingService {
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	public void sendNotification(String Email) throws MailException
	{		
        System.out.println("Sending email...");
        
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(Email);
		mail.setFrom("lapsystemteam@gmail.com");
		mail.setSubject("New Leave Form is Revieved");
		mail.setText("Login to see the leave request: http://localhost:8080/login");
		javaMailSender.send(mail);
	}
	

}
