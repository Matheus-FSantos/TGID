package io.github.matheusfsantos.tgid.model.exception.specialization;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import io.github.matheusfsantos.tgid.model.exception.TgidException;

public class ClientNotFoundException extends TgidException {

	@Serial
	public static final long serialVersionUID = 1L;
	
	public ClientNotFoundException () { }
	
	public ClientNotFoundException (String message, String description) {
		super(message, description, HttpStatus.NOT_FOUND, LocalDateTime.now());
	}
	
	public ClientNotFoundException (List<String> messages, String description) {
		super(messages, description, HttpStatus.NOT_FOUND, LocalDateTime.now());
	}
	
}
