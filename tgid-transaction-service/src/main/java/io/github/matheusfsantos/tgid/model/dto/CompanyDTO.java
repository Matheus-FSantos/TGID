package io.github.matheusfsantos.tgid.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.github.matheusfsantos.tgid.model.utils.format.IdentifierDocumentFormat;
import io.github.matheusfsantos.tgid.model.utils.format.PriceFormat;

public record CompanyDTO(
	@JsonIgnore String cnpj,
	String name,
	String email,
	@JsonIgnore BigDecimal funds,
	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss") LocalDateTime createdAt,
	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss") LocalDateTime updatedAt
) {
	public String getCnpj() {
		return IdentifierDocumentFormat.formatarCNPJ(cnpj);
	}
	
	public String getFunds() {
		return PriceFormat.getFormattedPrice(funds);
	}
}
