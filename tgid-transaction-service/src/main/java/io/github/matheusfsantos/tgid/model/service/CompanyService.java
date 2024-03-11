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

import io.github.matheusfsantos.tgid.model.domain.Company;
import io.github.matheusfsantos.tgid.model.dto.CompanyDTO;
import io.github.matheusfsantos.tgid.model.dto.NewAccountDTO;
import io.github.matheusfsantos.tgid.model.dto.UpdatedAccountDTO;
import io.github.matheusfsantos.tgid.model.exception.specialization.CompanyAlreadyExistsException;
import io.github.matheusfsantos.tgid.model.exception.specialization.CompanyNotFoundException;
import io.github.matheusfsantos.tgid.model.repository.CompanyRepository;

@Service
public class CompanyService {

	private static final Logger logger = LoggerFactory.getLogger(CompanyService.class);
	
	@Autowired
	private CompanyRepository companyRepository;
	
	public List<CompanyDTO> findAllCompanies() {
		return this.companyRepository.findAll()
	            .stream()
		            .map(company -> new CompanyDTO(
		                    company.getCnpj(),
		                    company.getName(),
		                    company.getEmail(),
		                    company.getFunds(),
		                    company.getCreatedAt(),
		                    company.getUpdatedAt()
		            )).collect(Collectors.toList());
	}
	
	public CompanyDTO findCompanyById(String identifierDocument) throws CompanyNotFoundException {
		Optional<Company> company = this.companyRepository.findById(identifierDocument);
		
		if(company.isEmpty())
			throw new CompanyNotFoundException("Company not found.", "Company not found.");
		
		return new CompanyDTO(identifierDocument, company.get().getName(), company.get().getEmail(), company.get().getFunds(), company.get().getCreatedAt(), company.get().getUpdatedAt());
	}
	
	public void save(NewAccountDTO newAccountDTO) throws CompanyAlreadyExistsException {
		if(this.companyRepository.existsById(newAccountDTO.identifierDocument())) {
			throw new CompanyAlreadyExistsException("Unable to complete this action. Update the field and try again.", "Invalid Fields");
		}
		
		Company company = new Company(newAccountDTO.name(), newAccountDTO.email(), new BigDecimal(100d), newAccountDTO.identifierDocument(), LocalDateTime.now(), LocalDateTime.now());
		
		CompanyService.logger.info("A new account was created with the CNPJ: " + company.getCnpj());
		
		this.companyRepository.save(company);
	}
	
	public void update(String identifierDocument, UpdatedAccountDTO updatedAccountDTO) throws CompanyNotFoundException {
		CompanyDTO oldCompany = this.findCompanyById(identifierDocument);
		Company updatedCompany = new Company(updatedAccountDTO.name(), updatedAccountDTO.email(), oldCompany.funds(), identifierDocument, oldCompany.createdAt(), LocalDateTime.now());
		
		CompanyService.logger.info("A new account was updated with the CNPJ: " + identifierDocument);
		
		this.companyRepository.save(updatedCompany);
	}
	
	public void delete(String identifierDocument) throws CompanyNotFoundException {
		if(this.companyRepository.existsById(identifierDocument))
			this.companyRepository.deleteById(identifierDocument);
		else
			throw new CompanyNotFoundException("Company not found.", "Company not found.");
	}

	public void updateCompanyBeforeTransaction(Company company) {
		this.companyRepository.save(company);
	}
}
