package com.bank.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankingApplication.class, args);
	}
	/*@Bean
	public CommandLineRunner commandLineRunner(
			AuthenticationServiceImpl service
	) {
		return args -> {
			var admin = Userdto.builder()
					.firstname( "mahdi" )
					.lastname( "bouazizi" )
					.email( "admin@gmail.com" )
					.password( "password" )
					.build();
			System.out.println( "Admin token: " + service.register( admin ).getToken() );

		};
	}*/
}
