package io.github.matheusfsantos.tgid.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.github.matheusfsantos.tgid.model.utils.format.IdentifierDocumentFormat;
import io.github.matheusfsantos.tgid.model.utils.format.PriceFormat;

public record DebtDTO(
	UUID id,
	@JsonIgnore String debtorId,
	@JsonIgnore String companyToReceiveId,
	@JsonIgnore BigDecimal debitValue,
	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss") LocalDateTime createdAt,
	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss") LocalDateTime updatedAt
) {
	public String getOriginAccount() {
		if(debtorId.length() == 11)
			return IdentifierDocumentFormat.formatarCPF(debtorId);
		
		return IdentifierDocumentFormat.formatarCNPJ(debtorId);
	}

	public String getTargetAccount() {
		if(companyToReceiveId.length() == 11)
			return IdentifierDocumentFormat.formatarCPF(companyToReceiveId);
		
		return IdentifierDocumentFormat.formatarCNPJ(companyToReceiveId);
	}
	
	public String getValue() {
		return PriceFormat.getFormattedPrice(debitValue);
	}
}
