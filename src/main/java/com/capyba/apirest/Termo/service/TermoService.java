package com.capyba.apirest.Termo.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.capyba.apirest.Termo.model.Termo;
import com.capyba.apirest.Termo.model.builder.TermoBuilder;
import com.capyba.apirest.Termo.repository.TermoRepository;
import com.capyba.apirest.Termo.util.TermoUtils;

@Service
public class TermoService {

    @Autowired
    private TermoBuilder builder;

    @Autowired
    private TermoRepository repository;

    public List<Termo> listarTermos() {
        return repository.findAll();
    }

    @Transactional
    public Termo salvarTermo(MultipartFile file) throws IOException {

        Termo termoNovo = uploadTermo(file);

        var termoRecebido = builder.builderTermo(termoNovo);
        var termoSalvo = repository.save(termoRecebido);
        return termoSalvo;
    }

    public Termo uploadTermo(MultipartFile file) throws IOException {

        Termo termoData = Termo.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .termoData(TermoUtils.compressTermo(file.getBytes())).build();

        return termoData;
    }

    public byte[] downloadTermo(String fileName) {
        Optional<Termo> dbTermoData = repository.findByName(fileName);
        byte[] termos = TermoUtils.decompressTermo(dbTermoData.get().getTermoData());
        return termos;
    }

    @Transactional
    public void removerTermo(Long id) {
        repository.deleteById(id);
    }

}
