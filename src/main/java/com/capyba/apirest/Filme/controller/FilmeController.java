package com.capyba.apirest.Filme.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capyba.apirest.Filme.model.Filme;
import com.capyba.apirest.Filme.service.FilmeService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/filme")
public class FilmeController {

    @Autowired
    private FilmeService service;

    @GetMapping
    public List<Filme> listarFilmesPadrao() {
        return service.listarFilmesPadrao();
    }

    @GetMapping("/buscar")
    public Page<Filme> search(
            @RequestParam("search") String searchTerm,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        return service.search(searchTerm, page, size);

    }

    @GetMapping("/listar")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Página", defaultValue = "0"),
            @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Quantidade", defaultValue = "5"),
            @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Ordenação")
    })
    public List<Filme> listarFilmes(
            @ApiIgnore Pageable pageable) {
        return service.listarFilmes(pageable).getContent();
    }

    @PostMapping(value = "/salvar")
    public ResponseEntity<Filme> salvarFilme(@RequestBody @Valid Filme filme) throws Exception {

        var resultado = service.salvarFilme(filme);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/encontrar/{id}")
    public ResponseEntity<Optional<Filme>> encontrarFilme(@PathVariable("id") Long id) {
        var filmeEncontrado = service.encontrarFilme(id);
        return ResponseEntity.ok(filmeEncontrado);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<Filme> editarFilme(@PathVariable("id") Long id, @RequestBody @Valid Filme filme)
            throws Exception {
        var resultado = service.alterar(id, filme);
        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<String> removeFilmeId(@PathVariable("id") Long id) throws Exception {
        service.removerFilme(id);
        return ResponseEntity.noContent().build();
    }

}
