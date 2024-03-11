package io.github.matheusfsantos.tgid.model.exception.specialization;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import io.github.matheusfsantos.tgid.model.exception.TgidException;

public class InvalidTransferTypeCodeException extends TgidException {

	@Serial
	public static final long serialVersionUID = 1L;

	public InvalidTransferTypeCodeException() { }
	
	public InvalidTransferTypeCodeException(String message, String description) {
		super(message, description, HttpStatus.UNPROCESSABLE_ENTITY, LocalDateTime.now());
	}
	
	public InvalidTransferTypeCodeException(List<String> messages, String description) {
		super(messages, description, HttpStatus.UNPROCESSABLE_ENTITY, LocalDateTime.now());
	}
	
}
