package io.github.matheusfsantos.tgid.model.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.matheusfsantos.tgid.model.domain.Account;
import io.github.matheusfsantos.tgid.model.domain.Client;
import io.github.matheusfsantos.tgid.model.domain.Company;
import io.github.matheusfsantos.tgid.model.domain.Transfer;
import io.github.matheusfsantos.tgid.model.domain.enums.TransferType;
import io.github.matheusfsantos.tgid.model.dto.ClientDTO;
import io.github.matheusfsantos.tgid.model.dto.CompanyDTO;
import io.github.matheusfsantos.tgid.model.dto.NewTransferDTO;
import io.github.matheusfsantos.tgid.model.dto.TransferDTO;
import io.github.matheusfsantos.tgid.model.exception.specialization.ClientNotFoundException;
import io.github.matheusfsantos.tgid.model.exception.specialization.CompanyNotFoundException;
import io.github.matheusfsantos.tgid.model.exception.specialization.InvalidFieldsException;
import io.github.matheusfsantos.tgid.model.exception.specialization.InvalidTransferException;
import io.github.matheusfsantos.tgid.model.exception.specialization.TransferNotFoundException;
import io.github.matheusfsantos.tgid.model.repository.TransferRepository;
import io.github.matheusfsantos.tgid.model.service.email.EmailSenderService;
import io.github.matheusfsantos.tgid.model.service.webhook.WebHookSenderService;
import io.github.matheusfsantos.tgid.model.utils.format.PriceFormat;
import io.github.matheusfsantos.tgid.model.utils.validation.AccountValidation;

@Service
public class TransferService {

	private static final Logger logger = LoggerFactory.getLogger(TransferService.class);
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private DebtService debitService;
	
	@Autowired
	private EmailSenderService emailSenderService;
	
	@Autowired
	private WebHookSenderService webHookSenderService;

	@Autowired
	private TransferRepository transferRepository;
	
	public List<TransferDTO> findAllTransfers() {
		return this.transferRepository.findAll()
					.stream()
						.map(transfer -> new TransferDTO(
								transfer.getId(),
								transfer.getOriginAccount(),
								transfer.getTargetAccount(),
								transfer.getTransferValue(),
								transfer.getTransferType(),
								transfer.getCreatedAt()
						)).collect(Collectors.toList());
	}

	public TransferDTO findTransferById(UUID transferId) throws TransferNotFoundException {
		Optional<Transfer> transfer = this.transferRepository.findById(transferId);
		
		if(transfer.isEmpty())
			throw new TransferNotFoundException("Transfer not found.", "Transfer not found.");
		
		return new TransferDTO(transferId, transfer.get().getOriginAccount(), transfer.get().getTargetAccount(), transfer.get().getTransferValue(), transfer.get().getTransferType(), transfer.get().getCreatedAt());
	}
	
	public void createDeposit(String originAccount, String targetAccount, NewTransferDTO newTransferDTO) throws InvalidTransferException, CompanyNotFoundException, ClientNotFoundException, InvalidFieldsException {
		/* Fields Validation */
		AccountValidation.identifierDocumentValidation(originAccount);
		AccountValidation.identifierDocumentValidation(targetAccount);

		Account originAccountEntity = this.findAccountById(originAccount);
		Account targetAccountEntity = this.findAccountById(targetAccount);
		
		
		this.validateSufficientFunds(originAccountEntity, newTransferDTO.value());
		
		String message = "New transfer of " + PriceFormat.getFormattedPrice(newTransferDTO.value()) + " between accounts: " + originAccount + " -> " + targetAccount + " (DEPOSIT)";

		this.updateAccountFundsSubtract(originAccount, originAccountEntity, newTransferDTO.value(), message);
		this.updateAccountFundsAdd(targetAccount, targetAccountEntity, newTransferDTO.value(), message);
		
		
		this.transferRepository.save(new Transfer(originAccount, targetAccount, newTransferDTO.value(), TransferType.Deposit, LocalDateTime.now()));
		TransferService.logger.info(message);
	}
	
	public void createWithdraw(String originAccount, String targetAccount, NewTransferDTO newTransferDTO) throws InvalidTransferException, CompanyNotFoundException, ClientNotFoundException, InvalidFieldsException {
		/* Fields Validation */
		AccountValidation.identifierDocumentValidation(originAccount);
		AccountValidation.identifierDocumentValidation(targetAccount);
		
		this.validateHavePendingDebts(originAccount);
		this.validateIsCompany(targetAccount);
		
		Account originAccountEntity = this.findAccountById(originAccount);
		Account targetAccountEntity = this.findAccountById(targetAccount);
		
		
		this.validateSufficientFunds(targetAccountEntity, newTransferDTO.value());
		
		String message = "New transfer of " + PriceFormat.getFormattedPrice(newTransferDTO.value()) + " between accounts: " + originAccount + " -> " + targetAccount + " (WITHDRAW).";
		
		this.updateAccountFundsSubtract(targetAccount, targetAccountEntity, newTransferDTO.value(), message);
		this.updateAccountFundsAdd(originAccount, originAccountEntity, newTransferDTO.value(), message);
	
		TransferService.logger.info(message);
		this.debitService.createNewDebit(originAccount, targetAccount, newTransferDTO.value());
		this.transferRepository.save(new Transfer(originAccount, targetAccount, newTransferDTO.value(), TransferType.Deposit, LocalDateTime.now()));
	}
	
	public void fakeDeposit(String targetAccount, NewTransferDTO newTransferDTO) throws ClientNotFoundException, CompanyNotFoundException {
		Account account = this.findAccountById(targetAccount);
		
		if(targetAccount.length() == 11) {
			Client client = new Client(account.getName(), account.getEmail(), account.getFunds().add(newTransferDTO.value()), targetAccount, account.getCreatedAt(), LocalDateTime.now());
			this.emailSenderService.sender(client.getEmail(), "The company with CNPJ: " + client.getCpf() + " just won: " + PriceFormat.getFormattedPrice(newTransferDTO.value()));
			this.clientService.updateClientBeforeTransaction(client);
		} else {
			Company company = new Company(account.getName(), account.getEmail(), account.getFunds().add(newTransferDTO.value()), targetAccount, account.getCreatedAt(), LocalDateTime.now());
			this.webHookSenderService.sendMessage("The company with CNPJ: " + company.getCnpj() + " just won: " + PriceFormat.getFormattedPrice(newTransferDTO.value()));
			this.companyService.updateCompanyBeforeTransaction(company);
		}
	}
	
	private Account findAccountById(String accountId) throws CompanyNotFoundException, ClientNotFoundException {
	    if (accountId.length() == 11) {
	    	ClientDTO clientDTO = clientService.findClientById(accountId);
	    	Client client = new Client(clientDTO.name(), clientDTO.email(), clientDTO.funds(), clientDTO.cpf(), clientDTO.createdAt(), clientDTO.updatedAt());
	    	return client;
	    } else {
	    	CompanyDTO companyDTO = this.companyService.findCompanyById(accountId);
	    	Company company = new Company(companyDTO.name(), companyDTO.email(), companyDTO.funds(), companyDTO.cnpj(), companyDTO.createdAt(), companyDTO.updatedAt());
			return company;
	    }
	}
	
	private void validateSufficientFunds(Account account, BigDecimal amount) throws InvalidTransferException {
		if(account.getFunds().compareTo(amount) < 0) {
			throw new InvalidTransferException("You do not have enough money to carry out this transaction", "Invalid transfer.");
		}
	}
	
	private void validateHavePendingDebts(String accountId) throws InvalidTransferException {
		if(this.debitService.haveAnOutstandingDebt(accountId))
			throw new InvalidTransferException("It is impossible to carry out this action, you have an outstanding debt, pay the debt and try again.", "Invalid transfer.");
		
	}
	
	private void validateIsCompany(String accountId) throws InvalidTransferException {
		if(accountId.length() != 14)
			throw new InvalidTransferException("It is only possible to acquire “loans” with companies.", "Invalid transfer.");
	}
	
	private void updateAccountFundsSubtract(String accountId, Account account, BigDecimal amount, String message) {
	    account.updateFunds(account.getFunds().subtract(amount));
	    account.updateUpdatedAt(LocalDateTime.now());
	    
	    if(accountId.length() == 11) {
	    	Client client = new Client(account.getName(), account.getEmail(), account.getFunds(), accountId, account.getCreatedAt(), account.getUpdatedAt());
	    	this.emailSenderService.sender(client.getEmail(), message);
	    	this.clientService.updateClientBeforeTransaction(client);
	    } else {
	    	Company company = new Company(account.getName(), account.getEmail(), account.getFunds(), accountId, account.getCreatedAt(), account.getUpdatedAt());
	    	this.webHookSenderService.sendMessage(message);
	    	this.companyService.updateCompanyBeforeTransaction(company);
		}
	}
	
	private void updateAccountFundsAdd(String accountId, Account account, BigDecimal amount, String message) {
	    account.updateFunds(account.getFunds().add(amount));
	    account.updateUpdatedAt(LocalDateTime.now());
	    
	    if(accountId.length() == 11) {
	    	Client client = new Client(account.getName(), account.getEmail(), account.getFunds(), accountId, account.getCreatedAt(), account.getUpdatedAt());
	    	this.emailSenderService.sender(client.getEmail(), message);
	    	this.clientService.updateClientBeforeTransaction(client);
	    } else {
	    	Company company = new Company(account.getName(), account.getEmail(), account.getFunds(), accountId, account.getCreatedAt(), account.getUpdatedAt());
	    	this.webHookSenderService.sendMessage(message);
	    	this.companyService.updateCompanyBeforeTransaction(company);
		}
	}
}
