package io.github.matheusfsantos.tgid.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.matheusfsantos.tgid.model.dto.NewTransferDTO;
import io.github.matheusfsantos.tgid.model.dto.TransferDTO;
import io.github.matheusfsantos.tgid.model.exception.TgidException;
import io.github.matheusfsantos.tgid.model.service.TransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/transfers")
@Tag(name="Transfer", description="Controller class that will handle all requests that arrive at the URL: \"/api/transfer\" - that is, it controls everything related to system transfer.")
public class TransferController {

	@Autowired
	private TransferService transferService;
	
	
	@Operation (
		summary="Find all transfers",
		description="With this method you will be able to search for all existing transfers in our system. In return for the request, all formatted information will come from the respective transfer - if it exists." 
	)
	@GetMapping
	public ResponseEntity<List<TransferDTO>> findAllTransfers() {
		return ResponseEntity.ok().body(this.transferService.findAllTransfers());
	}
	
	@Operation (
		summary="Find a transfer by id",
		description="This method will fetch all information from a transfer - if it exists." 
	)
	@GetMapping("/{id}")
	public ResponseEntity<TransferDTO> findTransferById(@PathVariable(name="id") UUID transferId) throws TgidException {
		return ResponseEntity.ok().body(this.transferService.findTransferById(transferId));
	}
	
	@Operation (
		summary="Money inject",
		description="With this method you will inject money into an account, like \"lava jato\" - if account exists." 
	)
	@PostMapping("/deposit/{targetAccount}")
	public ResponseEntity<Void> fakeDeposit(@PathVariable(name="targetAccount") String targetAccount, @RequestBody NewTransferDTO newTransferDTO) throws TgidException {
		this.transferService.fakeDeposit(targetAccount, newTransferDTO);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@Operation (
		summary="Deposit",
		description="With this method you will be able to deposit money into another account - if it exists." 
	)
	@PostMapping("/{originAccount}/deposit/{targetAccount}")
	public ResponseEntity<Void> createDeposit(@PathVariable(name="originAccount") String originAccount, @PathVariable(name="targetAccount") String targetAccount, @RequestBody NewTransferDTO newTransferDTO) throws TgidException {
		this.transferService.createDeposit(originAccount, targetAccount, newTransferDTO);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@Operation (
		summary="Withdraw",
		description="With this method you can withdraw money from another account, such as a loan – if it exists. Note: Be careful not to get into debt, the rates are treacherous... 👀" 
	)
	@PostMapping("/{originAccount}/withdraw/{targetAccount}")
	public ResponseEntity<Void> createWithdraw(@PathVariable(name="originAccount") String originAccount, @PathVariable(name="targetAccount") String targetAccount, @RequestBody NewTransferDTO newTransferDTO) throws TgidException {
		this.transferService.createWithdraw(originAccount, targetAccount, newTransferDTO);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
}
