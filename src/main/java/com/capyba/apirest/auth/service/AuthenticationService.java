package com.capyba.apirest.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.capyba.apirest.Usuario.model.Usuario;
import com.capyba.apirest.Usuario.repository.UsuarioRepository;



@Service
public class AuthenticationService implements UserDetailsService {
	  @Autowired
	    UsuarioRepository usuarioRepository;
	  
	  
	  @Bean
	    public BCryptPasswordEncoder  getPasswordEncoder() {
	       return new BCryptPasswordEncoder();

	 }

	    @Override
	    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	       Usuario usuarioRetornado = usuarioRepository.findByEmail(email);
	        if(usuarioRetornado == null){
	        	throw new UsernameNotFoundException("Dados inválidos para o usuário");    
	        }
	        return usuarioRetornado;
	        
	        
	    }
	    
}
