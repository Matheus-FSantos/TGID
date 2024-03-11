package io.github.matheusfsantos.tgid.model.utils.validation;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import io.github.matheusfsantos.tgid.model.dto.NewAccountDTO;
import io.github.matheusfsantos.tgid.model.dto.UpdatedAccountDTO;
import io.github.matheusfsantos.tgid.model.exception.specialization.InvalidFieldsException;

public class AccountValidation {

	private static String __NAME_REGEX__ = "\\b[A-Za-zÀ-ú][A-Za-zÀ-ú]+,?\\s[A-Za-zÀ-ú][A-Za-zÀ-ú]{2,60}\\b";
	
	public static void validation(NewAccountDTO newAccountDTO) throws InvalidFieldsException {
		List<String> messages = new ArrayList<String>();
		
		AccountValidation.nameValidation(messages, newAccountDTO.name());
		AccountValidation.emailValidation(messages, newAccountDTO.email());
		AccountValidation.identifierDocumentValidation(messages, newAccountDTO.identifierDocument());
		
		if (!messages.isEmpty())
			throw new InvalidFieldsException(messages, "Invalid Fields.");
	}
	
	public static void validation(UpdatedAccountDTO updatedAccountDTO) throws InvalidFieldsException {
		List<String> messages = new ArrayList<String>();
		
		AccountValidation.nameValidation(messages, updatedAccountDTO.name());
		AccountValidation.emailValidation(messages, updatedAccountDTO.email());
		
		if (!messages.isEmpty())
			throw new InvalidFieldsException(messages, "Invalid Fields.");
	}
	
	public static void identifierDocumentValidation(String identifierDocument) throws InvalidFieldsException {
		List<String> messages = new ArrayList<String>();
	
		AccountValidation.identifierDocumentValidation(messages, identifierDocument);
		
		if (!messages.isEmpty())
			throw new InvalidFieldsException(messages, "Invalid Fields.");
	}

	private static void nameValidation(List<String> messages, String name) {
		if(name == null)
			messages.add("The name field (\"name\") cannot be blank. Update the field and try again.");
		else {
			if(name.length() < 2 || name.length() > 60)
				messages.add("The name must be 2 to 60 characters long. Update the field and try again.");
			else if(!name.matches(AccountValidation.__NAME_REGEX__))
				messages.add("The name (\"name\") field must contain: your FIRST and SECOND name.");
		}
	}

	private static void emailValidation(List<String> messages, String email) {
		if(email == null)
			messages.add("The e-mail field (\"email\") cannot be blank. Update the field and try again.");
		else {
			if(email.length() < 5 || email.length() > 120)
				messages.add("The email must be 5 to 120 characters long. Update the field and try again.");
			else {
				try {
		            InternetAddress emailAddr = new InternetAddress(email);
		            emailAddr.validate();
		        } catch (AddressException exception) {
		            messages.add("The e-mail field is invalid!");
		        }
			}
		}
	}

	private static void identifierDocumentValidation(List<String> messages, String identifierDocument) {
		if(identifierDocument == null)
			messages.add("The identification document field (\"identifierDocument\") cannot be blank. Update the field and try again.");
		else if(identifierDocument.length() != 11 && identifierDocument.length() != 14) {
			messages.add("The identification document must be 11 (for basic account) or 14 (for company account). Update the field and try again.");
		}
	}
	
}
