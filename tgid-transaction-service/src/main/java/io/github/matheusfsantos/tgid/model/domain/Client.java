package io.github.matheusfsantos.tgid.model.domain;

import java.util.Objects;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
@Table(name="tb_client")
public class Client extends Account {
	
	@Id
	@Column(nullable=false, length=11, unique=true)
	private String cpf;

	public Client() { }

	public Client(String name, String email, BigDecimal funds, String cpf, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super(name, email, funds, createdAt, updatedAt);
		this.cpf = cpf;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(cpf);
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
		Client other = (Client) obj;
		return Objects.equals(cpf, other.cpf);
	}
	
	@Override
	public String toString() {
		return super.toString() + ", Client [cpf=" + cpf + "]";
	}

	public String getCpf() {
		return cpf;
	}

	public void updateCpf(String cpf) {
		this.setCpf(cpf);
	}

	private void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
}
