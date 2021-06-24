package com.cashonline.examen;

import com.cashonline.examen.model.Loan;
import com.cashonline.examen.model.User;
import com.cashonline.examen.repository.LoanRepository;
import com.cashonline.examen.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
public class ExamenApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExamenApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(UserRepository userRepository, LoanRepository loanRepository, Environment env) {

		return args -> {
			ObjectMapper mapper = new ObjectMapper();

			TypeReference<List<User>> typeReference = new TypeReference<List<User>>(){};

			InputStream inputStream = TypeReference.class.getResourceAsStream("/json/users.json");

			List<Loan> loans = new ArrayList<>();

			int loansCount = Integer.parseInt(env.getProperty("start.inserts.loans") == null ? "100" : Objects.requireNonNull(env.getProperty("start.inserts.loans")));

			for (int i = 0; i < loansCount; i++) {
				Loan loan = new Loan((long) i, (float)Math.round((Math.random()*(150000-1000)) + 1000 * 100) / 100, (long) Math.floor((Math.random()*49)));
				loans.add(loan);
			}

			try {
				List<User> users = mapper.readValue(inputStream, typeReference);
				userRepository.saveAll(users);
				loanRepository.saveAll(loans);
				System.out.println("Users changed");
			} catch (IOException ex) {
				System.out.println("Unabled to save users");
				ex.printStackTrace();
			}

		};
	}

}
