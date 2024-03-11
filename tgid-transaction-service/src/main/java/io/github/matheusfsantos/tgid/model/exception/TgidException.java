package io.github.matheusfsantos.tgid.model.exception;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpStatus;

public class TgidException extends Exception {
	
	@Serial
	public static final long serialVersionUID = 1L;
	
	private String description;
	private List<String> messages;
	private HttpStatus statusCode;
	private LocalDateTime exceptionAt;
	
	public TgidException() { }
	
	public TgidException(String message, String description, HttpStatus statusCode, LocalDateTime exceptionAt) {
		this.statusCode = statusCode;
		this.description = description;
		this.exceptionAt = exceptionAt;
		this.messages = Collections.singletonList(message);
	}
	
	public TgidException(List<String> messages, String description, HttpStatus statusCode, LocalDateTime exceptionAt) {
		this.messages = messages;
		this.statusCode = statusCode;
		this.description = description;
		this.exceptionAt = exceptionAt;
	}
	
	public List<String> getMessages() {
		return messages;
	}
	
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public HttpStatus getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}
	
	public LocalDateTime getExceptionAt() {
		return exceptionAt;
	}
	
	public void setExceptionAt(LocalDateTime exceptionAt) {
		this.exceptionAt = exceptionAt;
	}
	
}
