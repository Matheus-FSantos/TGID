package io.github.matheusfsantos.tgid.model.dto;

public record NewAccountDTO (
	String name,
	String email,
	String identifierDocument
) { }
