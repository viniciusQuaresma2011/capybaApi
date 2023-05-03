package com.capyba.apirest.Termo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capyba.apirest.Termo.model.Termo;

public interface TermoRepository extends JpaRepository<Termo, Long> {
    Optional<Termo> findByName(String fileName);
}
