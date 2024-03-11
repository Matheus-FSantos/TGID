package io.github.matheusfsantos.tgid.model.dto.exception;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public record TgidExceptionDTO(
	List<String> messages,
	String description,
	Integer statusCode,
	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss") LocalDateTime exceptionAt
) {

}
