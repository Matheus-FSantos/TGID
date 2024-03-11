package io.github.matheusfsantos.tgid.model.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AccountTests {

	@Test
	void testAccountInstance() {
		String email = "matheus@gmail.com";
		BigDecimal funds = new BigDecimal(10d);
		String name = "Matheus Ferreira Santos";
		
		Account account = new Account(name, email, funds, LocalDateTime.now(), LocalDateTime.now());

		Assertions.assertTrue(account.getName().equals(name));
		Assertions.assertTrue(account.getEmail().equals(email));
		Assertions.assertTrue(account.getFunds().equals(funds));
	}
	
}
