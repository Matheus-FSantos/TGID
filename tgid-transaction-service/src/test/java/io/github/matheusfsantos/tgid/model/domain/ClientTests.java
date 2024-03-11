package io.github.matheusfsantos.tgid.model.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ClientTests {

	private static Logger logger = LoggerFactory.getLogger(ClientTests.class);
	
	@Test
	void testAccountInstance() {
		String fakeCpf = "47066390008";
		BigDecimal funds = new BigDecimal(10d);
		String clientEmail = "matheus@gmail.com";
		String clientName = "Matheus Ferreira Santos";
		
		Client client = new Client(clientName, clientEmail, funds, fakeCpf, LocalDateTime.now(), LocalDateTime.now());

		Assertions.assertTrue(client.getCpf().equals(fakeCpf));
		Assertions.assertTrue(client.getFunds().equals(funds));
		Assertions.assertTrue(client.getName().equals(clientName));
		Assertions.assertTrue(client.getEmail().equals(clientEmail));
	}
	
	@Test
	void testAccountToString() {
		String fakeCpf = "47066390008";
		BigDecimal funds = new BigDecimal(10d);
		String clientEmail = "matheus@gmail.com";
		String clientName = "Matheus Ferreira Santos";
		
		Client client = new Client(clientName, clientEmail, funds, fakeCpf, LocalDateTime.now(), LocalDateTime.now());

		Assertions.assertTrue(client.toString().contains(fakeCpf));
		Assertions.assertTrue(client.toString().contains(clientName)); /* validate toString at superclass */
	
		ClientTests.logger.info(client.toString());
	}
	
}
