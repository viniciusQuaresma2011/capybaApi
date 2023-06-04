package com.capyba.apirest.Usuario.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.capyba.apirest.Usuario.Image.service.ImageService;
import com.capyba.apirest.Usuario.model.Usuario;
import com.capyba.apirest.Usuario.service.UsuarioService;

import io.swagger.annotations.ApiOperation;

@RestController
/* @CrossOrigin(origins = "*", allowedHeaders = "*") */
@RequestMapping(value = "/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@Autowired
	private ImageService imgService;

	@GetMapping("/listar")
	public ResponseEntity<List<Usuario>> listarUsuarios() {
		var usuarioDtos = service.listarUsuarios();
		return ResponseEntity.ok(usuarioDtos);
	}

	@PostMapping(value = "/salvar", consumes = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.MULTIPART_FORM_DATA_VALUE })
	public Usuario salvarUsuario(@RequestParam @Valid String nome, String email, String senha,
			@RequestPart("image") MultipartFile file) throws Exception {
	
		
		Usuario u = new Usuario();
		u.setNome(nome);
		u.setEmail(email);
		u.setSenha(senha);
		
		
		try {
			/* service.uploadImage(file); */
			return service.salvar(u, file);
		} catch (Exception e) {
			System.out.print(email);
			System.out.print(file.getOriginalFilename());
//			System.out.println(e.getMessage());
			return null;
		}

	}

	@GetMapping("/buscarImagem/{fileName}")
	public ResponseEntity<?> downloadImage(@PathVariable String fileName) {
		byte[] imageData = imgService.downloadImage(fileName);
		return ResponseEntity.status(HttpStatus.OK)
				.contentType(MediaType.valueOf("image/png"))
				.body(imageData);

	}

	@GetMapping("/encontrar/{id}")
	public ResponseEntity<Optional<Usuario>> encontrarUsuario(@PathVariable("id") Long idUsuario) {
		var usuarioEncontrado = service.encontrarUsuario(idUsuario);
		return ResponseEntity.ok(usuarioEncontrado);
	}

	@PutMapping("/editar/{id}")
	public ResponseEntity<Usuario> editarUsuario(@PathVariable("id") Long id, @RequestBody @Valid Usuario usuario)
			throws Exception {
		var resultado = service.alterar(id, usuario);
		return ResponseEntity.ok(resultado);
	}

	@DeleteMapping("/remover/{id}")
	public ResponseEntity<String> removeUsuarioId(@PathVariable("id") Long id) throws Exception {
		service.removerUsuario(id);
		return ResponseEntity.noContent().build();
	}

}
