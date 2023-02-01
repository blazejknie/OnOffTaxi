package com.onofftaxi.backend.service;

import com.google.common.base.Preconditions;
import com.onofftaxi.backend.model.dto.DriverDto;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class EmailSenderService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender emailSender;
    private SimpleMailMessage message = new SimpleMailMessage();
    private String token;

    public void sendForgotPasswordMessage(String email, DriverService driverService) {
        DriverDto driver = driverService.getByEmail(email);
        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        token = getToken(driver);
        message.setSubject("Serwis onofftaxi.pl - klucz zmiany hasła w serwisie.");
        message.setText("Przesłany klucz zmiany hasła należy skopiować i użyć w serwisie onofftaxi.pl w ciągu pięciu minut. " +
                        "Po wprowadzeniu klucza prosimy o podanie i potwierdzenie nowego hasła. \n" +
                        " Klucz: \n" + token + "\n"
                /*"\n----------------------------- \n" +
                "Serwis onofftaxi.pl\n" +
                *//*"Ireneusz Senger\n" +
                "tel.: +48 504 377 288\n" +*//**//*
                "email: kontakt@onofftaxi.pl\n" *//*+
                "\n" +
                "Administratorem Państwa danych osobowych jest serwis onofftaxi.pl\n" +
                "Szczegóły dotyczące zasad przetwarzania Państwa danych osobowych znajdują się w https://onofftaxi.pl/polityka-prywatnosci.html\n"*/);

        emailSender.send(message);
    }

    public void sendNewUserRegisterMessage(String mail) {
        message.setTo(mail);
        message.setSubject("Serwis onofftaxi.pl - mail powitalny.");
        message.setText("Witaj w serwisie onofftaxi.pl\n" +
                        "Dziękujemy za podjęcie współpracy.\n" +
                        "Zachęcamy do aktywnego korzystania z funkcji ustaw status oraz z ustaw opis - " +
                        "funkcje te pozwolą zdobyć nowych klientów.\n" +
                        "W razie jakichkolwiek pytań - prosimy o kontakt."
                /*"\n----------------------------- \n" +
                "Serwis onofftaxi.pl\n" +
                *//*"Ireneusz Senger\n" +
                "tel.: +48 504 377 288\n" +*//**//*
                "email: kontakt@onofftaxi.pl\n" *//*+
                "\n" +
                "Administratorem Państwa danych osobowych jest serwis onofftaxi.pl\n" +
                "Szczegóły dotyczące zasad przetwarzania Państwa danych osobowych znajdują się w https://onofftaxi.pl/polityka-prywatnosci.html\n"*/);

        emailSender.send(message);
    }

    public String getToken(DriverDto driver) {

        long currentTimeMillis = System.currentTimeMillis();
        String compact = Jwts.builder()
                .setSubject(driver.getLogin())
                .setSubject(UUID.randomUUID().toString())
                .setIssuedAt(new Date(currentTimeMillis))
                .setExpiration(new Date(currentTimeMillis + 300000))
                .compact();
        return passwordEncoder.encode(compact);
    }

    public boolean compareToken(String value){
        Preconditions.checkNotNull(value);
        return value.equals(token);
    }
}
