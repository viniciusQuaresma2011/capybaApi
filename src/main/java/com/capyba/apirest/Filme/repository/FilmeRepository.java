package com.capyba.apirest.Filme.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.capyba.apirest.Filme.model.Filme;

public interface FilmeRepository extends PagingAndSortingRepository<Filme, Long> {

    Filme findByNome(String email);

    @Query("FROM Filme f " +
            "WHERE LOWER(f.nome) like %:searchTerm% " +
            "OR LOWER(f.genero) like %:searchTerm%")
    Page<Filme> searchFilmeByNameOrGenero(
            @Param("searchTerm") String searchTerm,
            Pageable pageable);
}
