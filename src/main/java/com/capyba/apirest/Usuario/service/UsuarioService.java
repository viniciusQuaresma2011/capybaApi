package com.capyba.apirest.Usuario.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.capyba.apirest.Usuario.Image.model.Image;
import com.capyba.apirest.Usuario.Image.service.ImageService;
import com.capyba.apirest.Usuario.model.Usuario;
import com.capyba.apirest.Usuario.model.UsuarioOAuth;
import com.capyba.apirest.Usuario.model.builder.UsuarioBuilder;
import com.capyba.apirest.Usuario.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioBuilder builder;

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private ImageService imgService;


	


	public List<Usuario> listarUsuarios() {
		return repository.findAll();
	}

	@Transactional
	public Usuario salvar(Usuario usuario, MultipartFile file) throws IOException {

		Image imagemNova = imgService.salvarImage(file);

		var u = builder.builderModel(usuario);
		u.setImage(imagemNova);
		var usuarioSalvo = builder.builderDto(repository.save(u));

		return usuarioSalvo;
	}

	@Transactional
	public Usuario salvarOA(UsuarioOAuth usuarioOAuth) {
		var usuario = builder.builderModelFromOauth(usuarioOAuth);
		var usuarioSalvo = repository.save(usuario);
		return usuarioSalvo;
	}

	public Optional<Usuario> encontrarUsuario(Long idUsuario) {
		return repository.findById(idUsuario);
	}

	@Transactional
	public Usuario alterar(Long idUsuario, Usuario usuario) {
		usuario.setIdUsuario(idUsuario);
		Usuario usuarioAlterado = repository.save(usuario);
		return usuarioAlterado;
	}

	@Transactional
	public void removerUsuario(Long id) {
		repository.deleteById(id);
	}

	public Usuario encontrarUsuarioEmail(String email) {
		return repository.findByEmail(email);
	}

	public Usuario encontraUsuarioToken(UsuarioOAuth usuarioOAuth) {
		var usuario = repository.findByEmail(usuarioOAuth.getEmail());
		return usuario;
	}

	public void atualizarComUid(Usuario usuario) {
		repository.saveAndFlush(usuario);
	}

	public Usuario salvarUsuarioOauth(UsuarioOAuth usuarioOAuth) {
		var usuarioToSave = builder.builderModelFromOauth(usuarioOAuth);
		var usuario = repository.saveAndFlush(usuarioToSave);
		return (Usuario) usuario;
	}

}
