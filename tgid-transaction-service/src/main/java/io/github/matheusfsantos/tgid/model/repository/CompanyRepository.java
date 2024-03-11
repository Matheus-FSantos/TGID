package io.github.matheusfsantos.tgid.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.matheusfsantos.tgid.model.domain.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> { }
