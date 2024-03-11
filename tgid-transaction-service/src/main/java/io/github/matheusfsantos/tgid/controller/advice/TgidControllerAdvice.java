package io.github.matheusfsantos.tgid.controller.advice;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.github.matheusfsantos.tgid.model.dto.exception.TgidExceptionDTO;
import io.github.matheusfsantos.tgid.model.exception.TgidException;

@RestControllerAdvice
public class TgidControllerAdvice {

	@ExceptionHandler(TgidException.class)
	public ResponseEntity<TgidExceptionDTO> handleFarmaCristoExceptions(TgidException tgidException) {
		return ResponseEntity.status(tgidException.getStatusCode()).body(new TgidExceptionDTO(tgidException.getMessages(), tgidException.getDescription(), tgidException.getStatusCode().value(), LocalDateTime.now()));
	}
	
}
