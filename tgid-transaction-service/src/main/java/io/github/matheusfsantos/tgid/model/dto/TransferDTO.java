package io.github.matheusfsantos.tgid.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.github.matheusfsantos.tgid.model.domain.enums.TransferType;
import io.github.matheusfsantos.tgid.model.exception.TgidException;
import io.github.matheusfsantos.tgid.model.utils.format.IdentifierDocumentFormat;
import io.github.matheusfsantos.tgid.model.utils.format.PriceFormat;

public record TransferDTO(
	UUID id,
	@JsonIgnore String originAccount,
	@JsonIgnore String targetAccount,
	@JsonIgnore BigDecimal value,
	@JsonIgnore Integer transferType,
	@JsonFormat(pattern="dd/MM/yyyy HH:mm:ss") LocalDateTime createdAt
) {
	public TransferType getTransferType() throws TgidException {
		return TransferType.valueOf(transferType);
	}
	
	public String getOriginAccount() {
		if(originAccount.length() == 11)
			return IdentifierDocumentFormat.formatarCPF(originAccount);
		
		return IdentifierDocumentFormat.formatarCNPJ(originAccount);
	}

	public String getTargetAccount() {
		if(originAccount.length() == 11)
			return IdentifierDocumentFormat.formatarCPF(targetAccount);
		
		return IdentifierDocumentFormat.formatarCNPJ(targetAccount);
	}
	
	public String getValue() {
		return PriceFormat.getFormattedPrice(value);
	}
}
