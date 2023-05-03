package com.capyba.apirest.auth.filter;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.capyba.apirest.Usuario.repository.UsuarioRepository;
import com.capyba.apirest.auth.service.TokenService;


@Component
public  class AuthWithTokenFilter  extends OncePerRequestFilter{
	
	 private TokenService tokenService;
	    private UsuarioRepository usuarioRepository;

	    public AuthWithTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
	        this.tokenService = tokenService;
	        this.usuarioRepository = usuarioRepository;
	    }

	    
	    @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	            throws ServletException, IOException {
	        var token = recuperarToken(request);
	        var valido = tokenService.isTokenValido(token);
	        
	        if(valido) {
	            autenticarCliente(token);
	        }

	        filterChain.doFilter(request, response);
	    }

	
	    private void autenticarCliente(String token) {
	        var idUsuario = tokenService.getIdUsuario(token);
	        var usuario = usuarioRepository.findById(idUsuario);
	        var authentication = new UsernamePasswordAuthenticationToken(usuario, null, null);
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	    }


	    private String recuperarToken(HttpServletRequest request) {
	        var token = request.getHeader("Authorization");
	        
	        if(Objects.nonNull(token) && token.startsWith("Bearer ")) {
	            return token.substring("Bearer ".length(), token.length());
	        }

	        return null;
	    }
	    
}
