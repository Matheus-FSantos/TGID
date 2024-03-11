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
import io.github.matheusfsantos.tgid.model.domain.Debt;
import io.github.matheusfsantos.tgid.model.dto.ClientDTO;
import io.github.matheusfsantos.tgid.model.dto.CompanyDTO;
import io.github.matheusfsantos.tgid.model.dto.DebtDTO;
import io.github.matheusfsantos.tgid.model.exception.specialization.ClientNotFoundException;
import io.github.matheusfsantos.tgid.model.exception.specialization.CompanyNotFoundException;
import io.github.matheusfsantos.tgid.model.exception.specialization.DebitNotFoundException;
import io.github.matheusfsantos.tgid.model.exception.specialization.InvalidFieldsException;
import io.github.matheusfsantos.tgid.model.exception.specialization.InvalidTransferException;
import io.github.matheusfsantos.tgid.model.repository.DebitRepository;
import io.github.matheusfsantos.tgid.model.service.email.EmailSenderService;
import io.github.matheusfsantos.tgid.model.service.webhook.WebHookSenderService;
import io.github.matheusfsantos.tgid.model.utils.format.PriceFormat;
import io.github.matheusfsantos.tgid.model.utils.math.PriceAdjustmentWithInterest;
import io.github.matheusfsantos.tgid.model.utils.validation.AccountValidation;

@Service
public class DebtService {

	private static final Logger logger = LoggerFactory.getLogger(TransferService.class);

	@Autowired
	private DebitRepository debitRepository;

	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private EmailSenderService emailSenderService;
	
	@Autowired
	private WebHookSenderService webHookSenderService;
	
	@Autowired
	private ClientService clientService;
	
	public List<DebtDTO> findAllDebits() {
		return this.debitRepository.findAll()
					.stream()
						.map(debit -> new DebtDTO(
							debit.getId(),
							debit.getDebtorId(),
							debit.getCompanyToReceiveId(),
							debit.getDebtValue(),
							debit.getCreatedAt(),
							debit.getUpdatedAt()
						)).collect(Collectors.toList());
	}
	
	public DebtDTO findDebitById(UUID id) throws DebitNotFoundException, InvalidTransferException {
		Optional<Debt> debit = this.debitRepository.findById(id);
		
		this.validateDebitIsEmpty(debit, null);
		
		return new DebtDTO(id, debit.get().getDebtorId(), debit.get().getCompanyToReceiveId(), debit.get().getDebtValue(), debit.get().getCreatedAt(), debit.get().getUpdatedAt());
	}
	
	public Boolean haveAnOutstandingDebt(String debtorId) {
		return this.debitRepository.findByDebtorId(debtorId).isPresent();
	}
	
	public void createNewDebit(String originAccount, String targetAccount, BigDecimal transferValue) throws InvalidFieldsException, CompanyNotFoundException, ClientNotFoundException {
		/* Fields Validation */
		AccountValidation.identifierDocumentValidation(originAccount);
		AccountValidation.identifierDocumentValidation(targetAccount);
		
		String message = "A debit in " + PriceFormat.getFormattedPrice(PriceAdjustmentWithInterest.getPriceAdjustmentWithInterest(transferValue)) + " has just been OPENED, " + originAccount + " -> " + targetAccount;
		
		Debt debit = new Debt(originAccount, targetAccount, PriceAdjustmentWithInterest.getPriceAdjustmentWithInterest(transferValue), LocalDateTime.now(), LocalDateTime.now());
		this.debitRepository.save(debit);
    	this.webHookSenderService.sendMessage(message);
    	this.sendEmailByAccountId(originAccount, message);
		DebtService.logger.info(message);
	}
	
	public void closeExistentDebit(String originAccount) throws InvalidFieldsException, CompanyNotFoundException, InvalidTransferException, ClientNotFoundException, DebitNotFoundException {
		/* Fields Validation */
		AccountValidation.identifierDocumentValidation(originAccount);
		
		Optional<Debt> debt = this.debitRepository.findByDebtorId(originAccount);
		
		this.validateDebitIsEmpty(debt, originAccount);
		
		Account originAccountEntity = this.findAccountById(originAccount);
		Account targetAccountEntity = this.findAccountById(debt.get().getCompanyToReceiveId());
		
		
		this.validateSufficientFunds(originAccount, originAccountEntity, debt.get().getDebtValue());
		
		String message = "A debit in " + PriceFormat.getFormattedPrice(PriceAdjustmentWithInterest.getPriceAdjustmentWithInterest(debt.get().getDebtValue())) + " has just been CLOSED, " + originAccount + " -> " + debt.get().getCompanyToReceiveId();
		
		this.updateAccountFundsSubtract(originAccount, originAccountEntity, PriceAdjustmentWithInterest.getPriceAdjustmentWithInterest(debt.get().getDebtValue()), message);
		this.updateAccountFundsAdd(debt.get().getCompanyToReceiveId(), targetAccountEntity, PriceAdjustmentWithInterest.getPriceAdjustmentWithInterest(debt.get().getDebtValue()), message);
		
		this.delete(debt.get().getId());
		this.webHookSenderService.sendMessage(message);
    	this.sendEmailByAccountId(originAccount, message);
		DebtService.logger.info(message);
	}
	
	public void delete(UUID id) {
		this.debitRepository.deleteById(id);
	}
	
	private void validateDebitIsEmpty(Optional<Debt> debt, String originAccount) throws InvalidTransferException, DebitNotFoundException {
		if(debt.isEmpty()) {
			if(originAccount != null)
				throw new InvalidTransferException("The account with the identifying document " + originAccount + " has no outstanding debts.", "Invalid transfer.");			
			else
				throw new DebitNotFoundException("Debit not found.", "Debit not found.");
		}
	}

	private void validateSufficientFunds(String originAccount, Account account, BigDecimal amount) throws InvalidTransferException {
		if(account.getFunds().compareTo(PriceAdjustmentWithInterest.getPriceAdjustmentWithInterest(amount)) < 0)
			throw new InvalidTransferException("The account with the identifying document " + originAccount + " don't have money to close the debt.", "Invalid transfer.");
		
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
	
	private void updateAccountFundsSubtract(String accountId, Account account, BigDecimal amount, String message) {
	    account.updateFunds(account.getFunds().subtract(amount));
	    account.updateUpdatedAt(LocalDateTime.now());
	    
	    if(accountId.length() == 11) {
	    	Client client = new Client(account.getName(), account.getEmail(), account.getFunds(), accountId, account.getCreatedAt(), account.getUpdatedAt());
	    	this.clientService.updateClientBeforeTransaction(client);
	    } else {
	    	Company company = new Company(account.getName(), account.getEmail(), account.getFunds(), accountId, account.getCreatedAt(), account.getUpdatedAt());
			this.companyService.updateCompanyBeforeTransaction(company);
		}
	}
	
	private void updateAccountFundsAdd(String accountId, Account account, BigDecimal amount, String message) {
	    account.updateFunds(account.getFunds().add(amount));
	    account.updateUpdatedAt(LocalDateTime.now());
	    
	    if(accountId.length() == 11) {
	    	Client client = new Client(account.getName(), account.getEmail(), account.getFunds(), accountId, account.getCreatedAt(), account.getUpdatedAt());
	    	this.clientService.updateClientBeforeTransaction(client);
	    } else {
	    	Company company = new Company(account.getName(), account.getEmail(), account.getFunds(), accountId, account.getCreatedAt(), account.getUpdatedAt());
			this.companyService.updateCompanyBeforeTransaction(company);
		}
	}

	private void sendEmailByAccountId(String accountId, String message) throws CompanyNotFoundException, ClientNotFoundException {
		if(accountId.length() == 11) {
			Account account = this.findAccountById(accountId);
			this.emailSenderService.sender(account.getEmail(), message);
		}
	}
	
}
