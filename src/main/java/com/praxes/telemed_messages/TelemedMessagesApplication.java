package com.praxes.telemed_messages;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.praxes.telemed_messages.domain.Consultation;
import com.praxes.telemed_messages.repo.ConsultationRepository;

import java.time.OffsetDateTime;

@SpringBootApplication
public class TelemedMessagesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TelemedMessagesApplication.class, args);
	}
}
