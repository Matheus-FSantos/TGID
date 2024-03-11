package io.github.matheusfsantos.tgid.model.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import io.github.matheusfsantos.tgid.model.domain.Debt;

public interface DebitRepository extends JpaRepository<Debt, UUID> {

	Optional<Debt> findByDebtorId(String debtorId);

}
