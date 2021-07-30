package com.emilie.SpringBatch;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients("com.emilie.SpringBatch")
public class SpringBatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchApplication.class, args);
	}
}


	/**
	 * Mail template
	 *//*
	@Bean
	public SimpleMailMessage templateSimpleMessage() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setText("Hello," +
				"\n\nThe return date for the book you loaned was : %s" +
				"\nIf it is not already done, you can extend your loan for 4 weeks." +
				"\nOr else, bring the book back to your library soon in order to avoid a penality" +
				"\n\n\t\t\t\tDunkirk Library - This is an automatic send, please do not answer back.");
		return message;
	}*/

