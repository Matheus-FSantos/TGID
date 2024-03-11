package io.github.matheusfsantos.tgid.model.domain;

import java.util.Objects;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class Account {

	@Column(nullable=false, length=60)
	private String name;

	@Column(nullable=false, length=120)
	private String email;

	@Column(nullable=false)
	private BigDecimal funds;
	
	@Column(nullable=false)
	private LocalDateTime createdAt;

	@Column(nullable=false)
	private LocalDateTime updatedAt;
	
	public Account() { }

	public Account(String name, String email, BigDecimal funds, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.name = name;
		this.email = email;
		this.funds = funds;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(createdAt, email, funds, name, updatedAt);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return Objects.equals(createdAt, other.createdAt) && Objects.equals(email, other.email)
				&& Objects.equals(funds, other.funds) && Objects.equals(name, other.name)
				&& Objects.equals(updatedAt, other.updatedAt);
	}

	@Override
	public String toString() {
		return "Account [name=" + name + ", email=" + email + ", funds=" + funds + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}

	public String getName() {
		return name;
	}

	public void updateName(String name) {
		this.setName(name);
	}

	private void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void updateEmail(String email) {
		this.setEmail(email);
	}

	private void setEmail(String email) {
		this.email = email;
	}
	
	public BigDecimal getFunds() {
		return funds;
	}

	public void updateFunds(BigDecimal funds) {
		this.setFunds(funds);
	}

	private void setFunds(BigDecimal funds) {
		this.funds = funds;
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
