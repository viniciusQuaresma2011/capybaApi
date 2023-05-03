package com.capyba.apirest.Usuario.model.builder;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.capyba.apirest.Usuario.model.Usuario;
import com.capyba.apirest.Usuario.model.UsuarioOAuth;



@Component
public class UsuarioBuilder {
	
	
	  public Usuario builderDto(Usuario usuarioRecebido) {
	        return Usuario
	                .builder()
	                .idUsuario(usuarioRecebido.getIdUsuario())
	                .email(usuarioRecebido.getEmail())
	                .senha(new BCryptPasswordEncoder().encode(usuarioRecebido.getSenha()))
	                .build();
	    }
	
	public Usuario builderModel(Usuario usuarioDto) {
        return Usuario
                .builder()
                .email(usuarioDto.getEmail())
                .senha(new BCryptPasswordEncoder().encode(usuarioDto.getSenha()))
                .build();
    }

    public Usuario builderModelFromOauth(UsuarioOAuth usuarioOAuth) {
        return Usuario
                .builder()
                .email(usuarioOAuth.getEmail())
                .build();
    }
}
