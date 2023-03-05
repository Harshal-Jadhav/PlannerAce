package com.plannerAce;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PlannerAceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlannerAceApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	//	@Bean
	//	public JavaMailSender javaMailSender() {
	//		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	//		mailSender.setHost("smtp.gmail.com");
	//		mailSender.setPort(587);
	//		mailSender.setProtocol("smtp");
	//		mailSender.setUsername("harshaljadhav6444@gmail.com");
	//		mailSender.setPassword("Harshal@6342");
	//		Properties props = mailSender.getJavaMailProperties();
	//		props.put("mail.transport.protocol", "smtp");
	//		props.put("mail.smtp.auth", "true");
	//		props.put("mail.smtp.starttls.enable", "true");
	//		props.put("mail.debug", "true");
	//		return mailSender;
	//	}


}
