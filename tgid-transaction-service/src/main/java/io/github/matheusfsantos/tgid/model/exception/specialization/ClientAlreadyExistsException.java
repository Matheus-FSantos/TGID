package io.github.matheusfsantos.tgid.model.exception.specialization;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import io.github.matheusfsantos.tgid.model.exception.TgidException;

public class ClientAlreadyExistsException extends TgidException {

	@Serial
	public static final long serialVersionUID = 1L;
	
	public ClientAlreadyExistsException () { }
	
	public ClientAlreadyExistsException (String message, String description) {
		super(message, description, HttpStatus.CONFLICT, LocalDateTime.now());
	}
	
	public ClientAlreadyExistsException (List<String> messages, String description) {
		super(messages, description, HttpStatus.CONFLICT, LocalDateTime.now());
	}
	
}
