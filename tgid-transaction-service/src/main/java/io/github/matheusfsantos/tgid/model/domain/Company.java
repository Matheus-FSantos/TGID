package io.github.matheusfsantos.tgid.model.domain;

import java.util.Objects;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
@Table(name="tb_company")
public class Company extends Account {
	
	@Id
	@Column(nullable=false, length=14, unique=true)
	private String cnpj;
	
	public Company() { }
	
	public Company(String name, String email, BigDecimal funds, String cnpj, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super(name, email, funds, createdAt, updatedAt);
		this.cnpj = cnpj;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(cnpj);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		return Objects.equals(cnpj, other.cnpj);
	}
	
	@Override
	public String toString() {
		return super.toString() + ", Company [cnpj=" + cnpj + "]";
	}

	public String getCnpj() {
		return cnpj;
	}

	public void updateCnpj(String cnpj) {
		this.setCnpj(cnpj);
	}

	private void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

}
