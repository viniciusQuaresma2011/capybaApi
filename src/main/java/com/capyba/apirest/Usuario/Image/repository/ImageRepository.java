package com.capyba.apirest.Usuario.Image.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capyba.apirest.Usuario.Image.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByName(String fileName);
}
