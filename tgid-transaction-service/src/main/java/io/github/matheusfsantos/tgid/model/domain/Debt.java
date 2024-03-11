package io.github.matheusfsantos.tgid.model.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tb_debt")
public class Debt {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id;
	
	@Column(length=14, nullable=false, unique=true)
	private String debtorId;

	@Column(length=14, nullable=false)
	private String companyToReceiveId;
	
	@Column(nullable=false)
	private BigDecimal debtValue;
	
	@Column(nullable=false)
	private LocalDateTime createdAt;

	@Column(nullable=false)
	private LocalDateTime updatedAt;
	
	public Debt() { }
	
	public Debt(UUID id, String debtorId, String companyToReceiveId, BigDecimal debtValue, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.id = id;
		this.debtorId = debtorId;
		this.companyToReceiveId = companyToReceiveId;
		this.debtValue = debtValue;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public Debt(String debtorId, String companyToReceiveId, BigDecimal debtValue, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.debtorId = debtorId;
		this.companyToReceiveId = companyToReceiveId;
		this.debtValue = debtValue;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	@Override
	public int hashCode() {
		return Objects.hash(companyToReceiveId, createdAt, debtValue, debtorId, id, updatedAt);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Debt other = (Debt) obj;
		return Objects.equals(companyToReceiveId, other.companyToReceiveId)
				&& Objects.equals(createdAt, other.createdAt) && Objects.equals(debtValue, other.debtValue)
				&& Objects.equals(debtorId, other.debtorId) && Objects.equals(id, other.id)
				&& Objects.equals(updatedAt, other.updatedAt);
	}
	
	@Override
	public String toString() {
		return "Debit [id=" + id + ", debtorId=" + debtorId + ", companyToReceiveId=" + companyToReceiveId
				+ ", debtValue=" + debtValue + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
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
	
	public String getDebtorId() {
		return debtorId;
	}

	public void updateDebtorId(String debtorId) {
		this.setDebtorId(debtorId);
	}

	private void setDebtorId(String debtorId) {
		this.debtorId = debtorId;
	}

	public String getCompanyToReceiveId() {
		return companyToReceiveId;
	}

	public void updateCompanyToReceiveId(String companyToReceiveId) {
		this.setCompanyToReceiveId(companyToReceiveId);
	}

	private void setCompanyToReceiveId(String companyToReceiveId) {
		this.companyToReceiveId = companyToReceiveId;
	}

	public BigDecimal getDebtValue() {
		return debtValue;
	}

	public void updateDebtValue(BigDecimal debtValue) {
		this.setDebtValue(debtValue);
	}

	private void setDebtValue(BigDecimal debtValue) {
		this.debtValue = debtValue;
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

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void updateUpdatedAt(LocalDateTime updatedAt) {
		this.setUpdatedAt(updatedAt);
	}

	private void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
}
