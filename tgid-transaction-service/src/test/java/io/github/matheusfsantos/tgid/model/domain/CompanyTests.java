package io.github.matheusfsantos.tgid.model.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CompanyTests {

	private static Logger logger = LoggerFactory.getLogger(CompanyTests.class);
	
	@Test
	void testAccountInstance() {
		String cnpj = "70909344000101";
		String companyName = "CodeFactory";
		BigDecimal funds = new BigDecimal(10d);
		String companyEmail = "codefactory@codefactory.com";
		
		Company company  = new Company(companyName, companyEmail, funds, cnpj, LocalDateTime.now(), LocalDateTime.now());
	

		Assertions.assertTrue(company.getCnpj().equals(cnpj));
		Assertions.assertTrue(company.getFunds().equals(funds));
		Assertions.assertTrue(company.getName().equals(companyName));
		Assertions.assertTrue(company.getEmail().equals(companyEmail));
	}
	
	@Test
	void testAccountToString() {
		String cnpj = "70909344000101";
		String companyName = "CodeFactory";
		String companyEmail = "codefactory@codefactory.com";
		
		Company company  = new Company(companyName, companyEmail, new BigDecimal(10d), cnpj, LocalDateTime.now(), LocalDateTime.now());

		Assertions.assertTrue(company.toString().contains(cnpj));
		Assertions.assertTrue(company.toString().contains(companyName)); /* validate toString at superclass */
	
		CompanyTests.logger.info(company.toString());
	}
	
}
