package com.capyba.apirest.Termo.model.builder;

import org.springframework.stereotype.Component;

import com.capyba.apirest.Termo.model.Termo;

@Component
public class TermoBuilder {

    public Termo builderTermo(Termo termoRecebido) {
        return Termo
                .builder()
                .id(termoRecebido.getId())
                .name(termoRecebido.getName())
                .type(termoRecebido.getType())
                .termoData(termoRecebido.getTermoData())
                .build();
    }
}
