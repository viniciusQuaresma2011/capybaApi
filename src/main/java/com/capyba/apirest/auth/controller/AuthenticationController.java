package com.capyba.apirest.auth.controller;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capyba.apirest.Usuario.model.Usuario;
import com.capyba.apirest.Usuario.model.UsuarioOAuth;
import com.capyba.apirest.Usuario.service.UsuarioService;
import com.capyba.apirest.auth.model.TokenDto;
import com.capyba.apirest.auth.service.TokenService;

@RestController
/* @CrossOrigin(maxAge = 3600, allowedHeaders = "*") */
@RequestMapping(value = "/auth")
public class AuthenticationController {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private AuthenticationManager auth;

	@Autowired
	private UsuarioService usuarioService;

	@PostMapping("/login")
	public ResponseEntity<TokenDto> retornaTokenLogin(@RequestBody Usuario usuarioDto) {
		UsernamePasswordAuthenticationToken dadosLogin = usuarioDto.convertTo();
		var usuario = usuarioService.encontrarUsuarioEmail(usuarioDto.getEmail());
		System.out.println(usuario);
		try {
			var autenticacao = auth.authenticate(dadosLogin);
			var token = tokenService.gerarToken(autenticacao);
			return ResponseEntity.ok(new TokenDto(token, "Bearer", usuario.getIdUsuario()));
		} catch (AuthenticationException e) {
			System.out.println("o erro é: " + e);
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping("/oauth")
	public ResponseEntity<TokenDto> retornaTokenAuth(@RequestBody UsuarioOAuth usuarioOAuth) {
		var usuario = usuarioService.encontraUsuarioToken(usuarioOAuth);

		if (Objects.isNull(usuario)) {
			usuario = usuarioService.salvarOA(usuarioOAuth);
		}

		try {
			var token = tokenService.gerarTokenOauth(usuario);
			return ResponseEntity.ok(new TokenDto(token, "Bearer", usuario.getIdUsuario()));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping(value = "/logout")
	public String logoutUser(HttpSession session) {
		session.invalidate();
		return "Usuário deslogado com sucesso!";
	}

}
