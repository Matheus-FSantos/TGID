package io.github.matheusfsantos.tgid.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.matheusfsantos.tgid.model.dto.ClientDTO;
import io.github.matheusfsantos.tgid.model.dto.CompanyDTO;
import io.github.matheusfsantos.tgid.model.dto.NewAccountDTO;
import io.github.matheusfsantos.tgid.model.dto.UpdatedAccountDTO;
import io.github.matheusfsantos.tgid.model.exception.TgidException;
import io.github.matheusfsantos.tgid.model.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/accounts")
@Tag(name="Account", description="Controller class that will handle all requests that arrive at the URL: \"/api/accounts\" - that is, it controls everything related to system accounts.")
public class AccountController {

	@Autowired
	private AccountService accountService;
	
	@Operation (
		summary="Find all company accounts",
		description="With this method you will be able to search for all existing company accounts in our system. In return for the request, all formatted information will come from the respective company account - if it exists." 
	)
	@GetMapping("/companies")
	public ResponseEntity<List<CompanyDTO>> findAllCompanies() {
		return ResponseEntity.ok().body(this.accountService.findAllCompanies());
	}
	
	@Operation (
		summary="Find all client accounts",
		description="With this method you will be able to search for all existing client accounts in our system. In return for the request, all formatted information will come from the respective client account - if it exists." 
	)
	@GetMapping("/clients")
	public ResponseEntity<List<ClientDTO>> findAllClients() {
		return ResponseEntity.ok().body(this.accountService.findAllClients());
	}
	
	@Operation (
		summary="Find company by id",
		description="This method will fetch all information from a company account - if it exists." 
	)
	@GetMapping("/company/{identifierDocument}")
	public ResponseEntity<CompanyDTO> findCompanyById(@PathVariable String identifierDocument) throws TgidException {
		return ResponseEntity.ok().body(this.accountService.findCompanyById(identifierDocument));
	}
	
	@Operation (
		summary="Find client account by id",
		description="This method will fetch all information from a client account - if it exists." 
	)
	@GetMapping("/clients/{identifierDocument}")
	public ResponseEntity<ClientDTO> findClientById(@PathVariable String identifierDocument) throws TgidException {
		return ResponseEntity.ok().body(this.accountService.findClientById(identifierDocument));
	}
	
	@Operation (
		summary="Create a new account",
		description="This method will create an account in the system if you enter all the information correctly. Note: The length (.length()) of the \"identification document\" is taken into account, if you enter one with a length equal to 11, a standard account will be created - customer type - if you enter one with a length equal to 14, it will be created a business account - company type." 
	)
	@PostMapping
	public ResponseEntity<Void> create(@RequestBody NewAccountDTO newAccountDTO) throws TgidException {
		this.accountService.save(newAccountDTO);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	@Operation (
		summary="Update a existent account",
		description="This method will update an account in the system if you enter all the information correctly and it exists." 
	)
	@PutMapping("/{identifierDocument}")
	public ResponseEntity<Void> update(@PathVariable String identifierDocument, @RequestBody UpdatedAccountDTO updatedAccountDTO) throws TgidException {
		this.accountService.update(identifierDocument, updatedAccountDTO);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@Operation (
		summary="Delete a existent account",
		description="This method will delete an account in the system - if it exists." 
	)
	@DeleteMapping("/{identifierDocument}")
	public ResponseEntity<Void> delete(@PathVariable String identifierDocument) throws TgidException {
		this.accountService.delete(identifierDocument);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
}
