package com.capyba.apirest.Filme.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "FILME")
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    @Column(name = "nome", unique = true)
    @JsonProperty(value = "nome")
    private String nome;

    @Column(name = "duracao")
    @JsonProperty(value = "duracao")
    private String duracao;

    @Column(name = "genero")
    @JsonProperty(value = "genero")
    private String genero;

    @Column(name = "dublagem")
    @JsonProperty(value = "dublagem")
    private String dublagem;

    @Column(name = "lancamento")
    @JsonProperty(value = "lancamento")
    private String lancamento;

    @Column(name = "direcao")
    @JsonProperty(value = "direcao")
    private String direcao;

    @Column(name = "elenco")
    @JsonProperty(value = "elenco")
    private String elenco;

    @Column(name = "sinopse", length = 10000)
    @JsonProperty(value = "sinopse")
    private String sinopse;

}
