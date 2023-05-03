package com.capyba.apirest.Usuario.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioOAuth {

    @JsonProperty("email")
    private String email;
    
    @JsonProperty("senha")
    private String senha;

    
}