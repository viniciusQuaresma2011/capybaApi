package com.capyba.apirest.Filme.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.capyba.apirest.Filme.model.Filme;
import com.capyba.apirest.Filme.model.builder.FilmeBuilder;
import com.capyba.apirest.Filme.repository.FilmeRepository;

@Service
public class FilmeService {
    @Autowired
    private FilmeBuilder builder;

    @Autowired
    private FilmeRepository repository;

    public List<Filme> listarFilmesPadrao() {
        return (List<Filme>) repository.findAll();
    }


    public Page<Filme> listarFilmes(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Filme> search(
            String searchTerm,
            int page,
            int size) {
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "nome");

        return repository.searchFilmeByNameOrGenero(
                searchTerm.toLowerCase(),
                pageRequest);
    }



    @Transactional
    public Filme salvarFilme(Filme filme) throws IOException {
        var filmeRecebido = builder.builderModel(filme);
        var filmeSalvo = repository.save(filmeRecebido);
        return filmeSalvo;
    }

    @Transactional
    public Filme alterar(Long idFilme, Filme filme) {
        filme.setId(idFilme);
        Filme filmeAlterado = repository.save(filme);
        return filmeAlterado;
    }

    public Filme encontrarFilmePorNome(String nome) {
        return repository.findByNome(nome);
    }

    public Optional<Filme> encontrarFilme(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public void removerFilme(Long id) {
        repository.deleteById(id);
    }

}
