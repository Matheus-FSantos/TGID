package io.github.matheusfsantos.tgid.model.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.matheusfsantos.tgid.model.domain.Transfer;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, UUID> { }
