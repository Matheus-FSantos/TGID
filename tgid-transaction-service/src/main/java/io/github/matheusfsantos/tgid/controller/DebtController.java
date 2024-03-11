package io.github.matheusfsantos.tgid.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.matheusfsantos.tgid.model.dto.DebtDTO;
import io.github.matheusfsantos.tgid.model.exception.TgidException;
import io.github.matheusfsantos.tgid.model.service.DebtService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/debts")
@Tag(name="Debt", description="Controller class that will handle all requests that arrive at the URL: \"/api/debt\" - that is, it controls everything related to system debt.")
public class DebtController {

	@Autowired
	private DebtService debitService;
	
	@Operation (
		summary="Find all debts",
		description="With this method you will be able to search for all existing debts in our system. In return for the request, all formatted information will come from the respective debts - if it exists." 
	)
	@GetMapping
	public ResponseEntity<List<DebtDTO>> findAllTransfers() {
		return ResponseEntity.ok().body(this.debitService.findAllDebits());
	}
	
	@Operation (
		summary="Find debt by id",
		description="This method will fetch all information from a debt - if it exists." 
	)
	@GetMapping("/{id}")
	public ResponseEntity<DebtDTO> findDebitById(@PathVariable(name="id") UUID debitId) throws TgidException {
		return ResponseEntity.ok().body(this.debitService.findDebitById(debitId));
	}
	
	@Operation (
		summary="Close debt by id",
		description="This method will close, in other words: it will close, an open debit in the system - if it exists." 
	)
	@PostMapping("/close-debt/{identifierDocument}")
	public ResponseEntity<DebtDTO> closeDebitByOriginAccountId(@PathVariable String identifierDocument) throws TgidException {
		this.debitService.closeExistentDebit(identifierDocument);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
}
