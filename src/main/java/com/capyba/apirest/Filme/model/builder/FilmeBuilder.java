package com.capyba.apirest.Filme.model.builder;

import org.springframework.stereotype.Component;

import com.capyba.apirest.Filme.model.Filme;

@Component
public class FilmeBuilder {

    public Filme builderModel(Filme filmeRecebido) {
        return Filme
                .builder()
                .id(filmeRecebido.getId())
                .nome(filmeRecebido.getNome())
                .duracao(filmeRecebido.getDuracao())
                .genero(filmeRecebido.getGenero())
                .dublagem(filmeRecebido.getDublagem())
                .lancamento(filmeRecebido.getLancamento())
                .direcao(filmeRecebido.getDirecao())
                .elenco(filmeRecebido.getElenco())
                .sinopse(filmeRecebido.getSinopse())
                .build();
    }

}
