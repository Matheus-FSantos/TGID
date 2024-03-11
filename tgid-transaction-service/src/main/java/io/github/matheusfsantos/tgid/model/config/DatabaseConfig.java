package io.github.matheusfsantos.tgid.model.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.matheusfsantos.tgid.model.dto.NewAccountDTO;
import io.github.matheusfsantos.tgid.model.service.AccountService;

@Configuration
public class DatabaseConfig {

	@Bean
	CommandLineRunner databaseInit(@Autowired AccountService accountService) {
		return args -> {
			/* Client accounts */
			accountService.save(new NewAccountDTO("Matheus Ferreira", "matheusdev12@gmail.com", "49796561026"));
			accountService.save(new NewAccountDTO("Lucas Montano", "lucas.montano@gmail.com", "74129305085"));
			accountService.save(new NewAccountDTO("Gabriel Barbosa", "gabigol@gmail.com", "94094149031"));
			
			/* Company accounts */
			accountService.save(new NewAccountDTO("Code Factory", "codefactory@codefactory.com", "32058390000147"));
			accountService.save(new NewAccountDTO("Code House", "codehouse@codehouse.com", "20972577000172"));
			accountService.save(new NewAccountDTO("Code Lab", "codelab@codelab.com", "66358293000191"));
		};
	}
	
}
