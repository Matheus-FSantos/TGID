package io.github.matheusfsantos.tgid.model.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.matheusfsantos.tgid.model.domain.Client;
import io.github.matheusfsantos.tgid.model.dto.ClientDTO;
import io.github.matheusfsantos.tgid.model.dto.NewAccountDTO;
import io.github.matheusfsantos.tgid.model.dto.UpdatedAccountDTO;
import io.github.matheusfsantos.tgid.model.exception.specialization.ClientAlreadyExistsException;
import io.github.matheusfsantos.tgid.model.exception.specialization.ClientNotFoundException;
import io.github.matheusfsantos.tgid.model.repository.ClientRepository;

@Service
public class ClientService {

	private static final Logger logger = LoggerFactory.getLogger(ClientService.class);
	
	@Autowired
	private ClientRepository clientRepository;
	
	public List<ClientDTO> findAllClients() {
		return this.clientRepository.findAll()
	            .stream()
		            .map(client -> new ClientDTO(
		                    client.getCpf(),
		                    client.getName(),
		                    client.getEmail(),
		                    client.getFunds(),
		                    client.getCreatedAt(),
		                    client.getUpdatedAt()
		            )).collect(Collectors.toList());
	
	}

	public ClientDTO findClientById(String identifierDocument) throws ClientNotFoundException {
		Optional<Client> client = this.clientRepository.findById(identifierDocument);
		
		if(client.isEmpty())
			throw new ClientNotFoundException("Client not found.", "Client not found.");
		
		return new ClientDTO(identifierDocument, client.get().getName(), client.get().getEmail(), client.get().getFunds(), client.get().getCreatedAt(), client.get().getUpdatedAt());
	}
	
	public void save(NewAccountDTO newAccountDTO) throws ClientAlreadyExistsException {
		if(this.clientRepository.existsById(newAccountDTO.identifierDocument())) {
			throw new ClientAlreadyExistsException("Unable to complete this action. Update the field and try again.", "Invalid Fields");
		}
		
		Client client = new Client(newAccountDTO.name(), newAccountDTO.email(), new BigDecimal(100d), newAccountDTO.identifierDocument(), LocalDateTime.now(), LocalDateTime.now());
		
		ClientService.logger.info("A new account was created with the CPF: " + client.getCpf());
		
		this.clientRepository.save(client);
	}	

	public void update(String identifierDocument, UpdatedAccountDTO updatedAccountDTO) throws ClientNotFoundException {
		ClientDTO oldClient = this.findClientById(identifierDocument);
		Client updatedClient = new Client(updatedAccountDTO.name(), updatedAccountDTO.email(), oldClient.funds(), identifierDocument, oldClient.createdAt(), LocalDateTime.now());
		
		ClientService.logger.info("A new account was updated with the CPF: " + identifierDocument);
		
		this.clientRepository.save(updatedClient);
	}

	public void delete(String identifierDocument) throws ClientNotFoundException {
		if(this.clientRepository.existsById(identifierDocument))
			this.clientRepository.deleteById(identifierDocument);
		else
			throw new ClientNotFoundException("Client not found.", "Client not found.");
	}
	
	public void updateClientBeforeTransaction(Client client) {
		this.clientRepository.save(client);
	}
}
