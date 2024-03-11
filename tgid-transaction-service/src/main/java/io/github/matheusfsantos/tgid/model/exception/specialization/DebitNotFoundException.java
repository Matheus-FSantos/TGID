package io.github.matheusfsantos.tgid.model.exception.specialization;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import io.github.matheusfsantos.tgid.model.exception.TgidException;

public class DebitNotFoundException extends TgidException {

	@Serial
	public static final long serialVersionUID = 1L;
	
	public DebitNotFoundException () { }
	
	public DebitNotFoundException (String message, String description) {
		super(message, description, HttpStatus.NOT_FOUND, LocalDateTime.now());
	}
	
	public DebitNotFoundException (List<String> messages, String description) {
		super(messages, description, HttpStatus.NOT_FOUND, LocalDateTime.now());
	}
	
}
