package io.github.matheusfsantos.tgid.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.matheusfsantos.tgid.model.domain.Client;


@Repository
public interface ClientRepository extends JpaRepository<Client, String> { }
