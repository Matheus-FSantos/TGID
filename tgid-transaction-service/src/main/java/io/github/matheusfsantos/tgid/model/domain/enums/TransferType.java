package io.github.matheusfsantos.tgid.model.domain.enums;

import io.github.matheusfsantos.tgid.model.exception.specialization.InvalidTransferTypeCodeException;

public enum TransferType {
	Deposit(1),
	Withdraw(2);
	
	private int code;
	
	private TransferType(int code) {
		this.code = code;
	}
	
	public int getCode() {
		return code;
	}
	
	public static TransferType valueOf(int code) throws InvalidTransferTypeCodeException {
		for(TransferType value : TransferType.values()) {
			if(code == value.getCode()) {
				return value;
			}
		}
		
		throw new InvalidTransferTypeCodeException("Invalid transfer type code. Valid transfer type code: 1 - Deposit, 2 - Withdrawn", "Invalid transfer type code.");
	}
}
