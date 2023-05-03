
package com.capyba.apirest.Termo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.capyba.apirest.Termo.model.Termo;
import com.capyba.apirest.Termo.service.TermoService;

@RestController
/* @CrossOrigin(origins = "*", allowedHeaders = "*") */
@RequestMapping(value = "/termo")
public class TermoController {

    @Autowired
    private TermoService service;

    @GetMapping("/listar")
    public ResponseEntity<List<Termo>> listarTermos() {
        var termos = service.listarTermos();
        return ResponseEntity.ok(termos);
    }

    @PostMapping(value = "/salvar", consumes = { "multipart/form-data" })
    public Termo salvarTermo(@RequestPart("termo_arquivo") MultipartFile file) throws Exception {

        try {
            return service.salvarTermo(file);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @GetMapping(value ="/buscarTermo/{fileName}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> downloadTermo(@PathVariable String fileName) {
        byte[] termoData = service.downloadTermo(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(termoData);

    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<String> removeTermoId(@PathVariable("id") Long id) throws Exception {
        service.removerTermo(id);
        return ResponseEntity.noContent().build();
    }
}