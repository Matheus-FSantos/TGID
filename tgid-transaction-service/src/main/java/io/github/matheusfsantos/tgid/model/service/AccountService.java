package io.github.matheusfsantos.tgid.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.matheusfsantos.tgid.model.dto.ClientDTO;
import io.github.matheusfsantos.tgid.model.dto.CompanyDTO;
import io.github.matheusfsantos.tgid.model.dto.NewAccountDTO;
import io.github.matheusfsantos.tgid.model.dto.UpdatedAccountDTO;
import io.github.matheusfsantos.tgid.model.exception.specialization.ClientAlreadyExistsException;
import io.github.matheusfsantos.tgid.model.exception.specialization.ClientNotFoundException;
import io.github.matheusfsantos.tgid.model.exception.specialization.CompanyAlreadyExistsException;
import io.github.matheusfsantos.tgid.model.exception.specialization.CompanyNotFoundException;
import io.github.matheusfsantos.tgid.model.exception.specialization.InvalidFieldsException;
import io.github.matheusfsantos.tgid.model.utils.validation.AccountValidation;

@Service
public class AccountService {

	@Autowired
	private CompanyService companyService;	
	
	@Autowired
	private ClientService clientService;
	
	public List<CompanyDTO> findAllCompanies() {
		return this.companyService.findAllCompanies();
	}
	
	public List<ClientDTO> findAllClients() {
		return this.clientService.findAllClients();
	}
	
	public CompanyDTO findCompanyById(String identifierDocument) throws CompanyNotFoundException {
		return this.companyService.findCompanyById(identifierDocument);
	}
	
	public ClientDTO findClientById(String identifierDocument) throws ClientNotFoundException {
		return this.clientService.findClientById(identifierDocument);
	}
	
	public void save(NewAccountDTO newAccountDTO) throws InvalidFieldsException, ClientAlreadyExistsException, CompanyAlreadyExistsException {
		/* Fields Validation */
		AccountValidation.validation(newAccountDTO);
		
		
		if(newAccountDTO.identifierDocument().length() == 11) {
			this.clientService.save(newAccountDTO);
		}
		
		if(newAccountDTO.identifierDocument().length() == 14) {
			this.companyService.save(newAccountDTO);
		}
	}
	
	public void update(String identifierDocument, UpdatedAccountDTO updatedAccountDTO) throws InvalidFieldsException, ClientNotFoundException, CompanyNotFoundException, ClientAlreadyExistsException, CompanyAlreadyExistsException {
		/* Fields Validation */
		AccountValidation.validation(updatedAccountDTO);
		AccountValidation.identifierDocumentValidation(identifierDocument);
		
		if(identifierDocument.length() == 11) {
			this.clientService.update(identifierDocument, updatedAccountDTO);
		}
		
		if(identifierDocument.length() == 14) {
			this.companyService.update(identifierDocument, updatedAccountDTO);
		}
	}

	public void delete(String identifierDocument) throws InvalidFieldsException, ClientNotFoundException, CompanyNotFoundException {
		/* Fields Validation */
		AccountValidation.identifierDocumentValidation(identifierDocument);
		
		if(identifierDocument.length() == 11) {
			this.clientService.delete(identifierDocument);
		}
		
		if(identifierDocument.length() == 14) {
			this.companyService.delete(identifierDocument);
		}
	}

}
