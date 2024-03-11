package io.github.matheusfsantos.tgid.model.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import io.github.matheusfsantos.tgid.model.domain.enums.TransferType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_transfer_tgid")
public class Transfer {

	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID id;
	
	@Column(length=14, nullable=false)
	private String originAccount;

	@Column(length=14, nullable=false)
	private String targetAccount;
	
	@Column(nullable=false, columnDefinition="decimal(38,2)")
	private BigDecimal transferValue;
	
	@Column(nullable=false)
	private Integer transferType;
	
	@Column(nullable=false)
	private LocalDateTime createdAt;
	
	public Transfer() { }

	public Transfer(UUID id, String originAccount, String targetAccount, BigDecimal transferValue, TransferType transferType, LocalDateTime createdAt) {
		this.id = id;
		this.originAccount = originAccount;
		this.targetAccount = targetAccount;
		this.transferValue = transferValue;
		this.createdAt = createdAt;
		
		this.updateTransferType(transferType);
	}
	
	public Transfer(String originAccount, String targetAccount, BigDecimal transferValue, TransferType transferType, LocalDateTime createdAt) {
		this.originAccount = originAccount;
		this.targetAccount = targetAccount;
		this.transferValue = transferValue;
		this.createdAt = createdAt;
		
		this.updateTransferType(transferType);
	}

	@Override
	public int hashCode() {
		return Objects.hash(createdAt, id, originAccount, targetAccount, transferType, transferValue);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transfer other = (Transfer) obj;
		return Objects.equals(createdAt, other.createdAt) && Objects.equals(id, other.id)
				&& Objects.equals(originAccount, other.originAccount)
				&& Objects.equals(targetAccount, other.targetAccount)
				&& Objects.equals(transferType, other.transferType) && Objects.equals(transferValue, other.transferValue);
	}
	
	@Override
	public String toString() {
		return "Transfer [id=" + id + ", originAccount=" + originAccount + ", targetAccount=" + targetAccount
				+ ", transferValue=" + transferValue + ", transferType=" + transferType + ", createdAt=" + createdAt + "]";
	}

	public UUID getId() {
		return id;
	}

	public void updateId(UUID id) {
		this.setId(id);
	}

	private void setId(UUID id) {
		this.id = id;
	}

	public String getOriginAccount() {
		return originAccount;
	}

	public void updateOriginAccount(String originAccount) {
		this.setOriginAccount(originAccount);
	}

	private void setOriginAccount(String originAccount) {
		this.originAccount = originAccount;
	}

	public String getTargetAccount() {
		return targetAccount;
	}

	public void updateTargetAccount(String targetAccount) {
		this.setTargetAccount(targetAccount);
	}

	private void setTargetAccount(String targetAccount) {
		this.targetAccount = targetAccount;
	}

	public BigDecimal getTransferValue() {
		return transferValue;
	}

	public void updateTransferValue(BigDecimal transferValue) {
		this.setTransferValue(transferValue);
	}

	private void setTransferValue(BigDecimal transferValue) {
		this.transferValue = transferValue;
	}
	
	public Integer getTransferType() {
		return transferType;
	}

	public void updateTransferType(TransferType transferType) {
		if(transferType != null) {
			this.setTransferType(transferType);
		}
	}

	private void setTransferType(TransferType transferType) {
		this.transferType = transferType.getCode();
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void updateCreatedAt(LocalDateTime createdAt) {
		this.setCreatedAt(createdAt);
	}

	private void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
}
