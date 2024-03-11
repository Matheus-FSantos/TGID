package io.github.matheusfsantos.tgid.model.utils.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.test.context.SpringBootTest;

import io.github.matheusfsantos.tgid.model.dto.NewAccountDTO;
import io.github.matheusfsantos.tgid.model.exception.TgidException;

@SpringBootTest
public class AccountValidationTests {

	public static Logger logger = LoggerFactory.getLogger(AccountValidationTests.class);
	
	@Test
	void newAccountValidationWithValidFieldsToClientTest() {
		String identifierDocument = "52727750072";
		String accountEmail = "matheus@gmail.com";
		String accountName = "Matheus Ferreira Santos";
		
		NewAccountDTO newAccountDto = new NewAccountDTO(accountName, accountEmail, identifierDocument);
		
		try {			
			AccountValidation.validation(newAccountDto);
			Assertions.assertTrue(newAccountDto.name().equals(accountName));
			Assertions.assertTrue(newAccountDto.email().equals(accountEmail));
			Assertions.assertTrue(newAccountDto.identifierDocument().equals(identifierDocument));
		} catch (TgidException e) { }
	}
	
	@Test
	void newAccountValidationWithValidFieldsToCompanyTest() {
		String accountName = "CodeFactory";
		String identifierDocument = "709093440001011";
		String accountEmail = "codefactory@codefactory.com";
		
		NewAccountDTO newAccountDto = new NewAccountDTO(accountName, accountEmail, identifierDocument);
		
		try {			
			AccountValidation.validation(newAccountDto);
			Assertions.assertTrue(newAccountDto.name().equals(accountName));
			Assertions.assertTrue(newAccountDto.email().equals(accountEmail));
			Assertions.assertTrue(newAccountDto.identifierDocument().equals(identifierDocument));
		} catch (TgidException e) { }
	}
	
	@Test
	void newAccountValidationWith7CharsOnIdentifierDocumentFieldsTest() {
		String identifierDocument = "5272775007";
		String accountEmail = "matheus@gmail.com";
		String accountName = "Matheus Ferreira Santos";
		
		NewAccountDTO newAccountDto = new NewAccountDTO(accountName, accountEmail, identifierDocument);
		
		try {			
			AccountValidation.validation(newAccountDto);
		} catch (TgidException e) {
			Assertions.assertTrue(!e.getMessages().isEmpty());
		}
	}
	
	@Test
	void newAccountValidationWith9CharsOnIdentifierDocumentFieldsTest() {
		String identifierDocument = "527277500722";
		String accountEmail = "matheus@gmail.com";
		String accountName = "Matheus Ferreira Santos";
		
		NewAccountDTO newAccountDto = new NewAccountDTO(accountName, accountEmail, identifierDocument);
		
		try {			
			AccountValidation.validation(newAccountDto);
		} catch (TgidException e) {
			Assertions.assertTrue(!e.getMessages().isEmpty());
		}
	}
	
	@Test
	void newAccountValidationWith13CharsOnIdentifierDocumentFieldsTest() {
		String accountName = "CodeFactory";
		String identifierDocument = "7090934400010";
		String accountEmail = "codefactory@codefactory.com";
		
		NewAccountDTO newAccountDto = new NewAccountDTO(accountName, accountEmail, identifierDocument);
		
		try {			
			AccountValidation.validation(newAccountDto);
		} catch (TgidException e) {
			Assertions.assertTrue(!e.getMessages().isEmpty());
		}
	}
	
	@Test
	void newAccountValidationWith15CharsOnIdentifierDocumentFieldsTest() {
		String accountName = "CodeFactory";
		String identifierDocument = "709093440001011";
		String accountEmail = "codefactory@codefactory.com";
		
		NewAccountDTO newAccountDto = new NewAccountDTO(accountName, accountEmail, identifierDocument);
		
		try {			
			AccountValidation.validation(newAccountDto);
		} catch (TgidException e) {
			Assertions.assertTrue(!e.getMessages().isEmpty());
		}
	}
	
	@Test
	void nameValidationWithNameNull() {
		String identifierDocument = "52727750072";
		String accountEmail = "matheus@gmail.com";
		String accountName = null;
		
		NewAccountDTO newAccountDto = new NewAccountDTO(accountName, accountEmail, identifierDocument);
		
		try {			
			AccountValidation.validation(newAccountDto);
		} catch (TgidException e) {
			Assertions.assertTrue(!e.getMessages().isEmpty());
		}
	}
	
	@Test
	void nameValidationWithNameLessThan2() {
		String identifierDocument = "52727750072";
		String accountEmail = "matheus@gmail.com";
		String accountName = "ma";
		
		NewAccountDTO newAccountDto = new NewAccountDTO(accountName, accountEmail, identifierDocument);
		
		try {			
			AccountValidation.validation(newAccountDto);
		} catch (TgidException e) {
			Assertions.assertTrue(!e.getMessages().isEmpty());
		}
	}
	
	@Test
	void nameValidationWithNameBiggerThan60() {
		String identifierDocument = "52727750072";
		String accountEmail = "matheus@gmail.com";
		String accountName = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		
		NewAccountDTO newAccountDto = new NewAccountDTO(accountName, accountEmail, identifierDocument);
		
		try {			
			AccountValidation.validation(newAccountDto);
		} catch (TgidException e) {
			Assertions.assertTrue(!e.getMessages().isEmpty());
		}
	}
	
	@Test
	void emailValidationWithEmailNull() {
		String identifierDocument = "52727750072";
		String accountEmail = null;
		String accountName = "Matheus Ferreira Santos";
		
		NewAccountDTO newAccountDto = new NewAccountDTO(accountName, accountEmail, identifierDocument);
		
		try {			
			AccountValidation.validation(newAccountDto);
		} catch (TgidException e) {
			Assertions.assertTrue(!e.getMessages().isEmpty());
		}
	}
	
	@Test
	void emailValidationWithEmailLessThan5() {
		String identifierDocument = "52727750072";
		String accountEmail = "mat@";
		String accountName = "Matheus Ferreira Santos";
		
		NewAccountDTO newAccountDto = new NewAccountDTO(accountName, accountEmail, identifierDocument);
		
		try {			
			AccountValidation.validation(newAccountDto);
		} catch (TgidException e) {
			Assertions.assertTrue(!e.getMessages().isEmpty());
		}
	}
	
	@Test
	void emailValidationWithEmailBiggerThan120() {
		String identifierDocument = "52727750072";
		String accountEmail = "maaaaaaaaaaaaaaaaaaaamaaaaaaaaaaaaaaaaaaaamaaaaaaaaaaaaaaaaaaaamaaaaaaaaaaaaaaaaaaaamaaaaaaaaaaaaaaaaaaaamaaaaaaaaaaaaaaaaaaaamaaaaaaaaaaa@gmail.com";
		String accountName = "Matheus Ferreira Santos";
		
		NewAccountDTO newAccountDto = new NewAccountDTO(accountName, accountEmail, identifierDocument);
		
		try {			
			AccountValidation.validation(newAccountDto);
		} catch (TgidException e) {
			Assertions.assertTrue(!e.getMessages().isEmpty());
		}
	}
	
}
