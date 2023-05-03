package com.capyba.apirest;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.multipart.MultipartFile;

import com.capyba.apirest.Filme.model.Filme;
import com.capyba.apirest.Filme.service.FilmeService;
import com.capyba.apirest.Usuario.model.Usuario;
import com.capyba.apirest.Usuario.service.UsuarioService;

@SpringBootTest
class ApirestApplicationTests {

	static final Filme FILME_0 = new Filme(1L,
			"Predador",
			"1 hora",
			"Suspense",
			"Brasileira",
			"26 de maio",
			"Fox Benetie",
			"Atores Americanos",
			"Dutch é contratado pelo governo dos Estados Unidos para resgatar políticos presos na Guatemala. Mas quando ele e sua equipe chegam na América Central");
	static final Filme FILME_1 = new Filme(2L,
			"Duro de matar",
			"1 hora",
			"Ação",
			"Brasileira",
			"25 de Maio de 2011",
			"Herbert Richards",
			"Atores Globais",
			"O personagem é duro de matar e ruim de morrer, mas ele insiste em seguir seus extintos");

	@Autowired
	FilmeService service;

	@Autowired
	private UsuarioService usuarioService;

	@Test
	@DisplayName("Teste 01- Cadastrar Usuário")
	void registerUser() throws IOException {

		Path path = Paths.get("/static/images/usuario_imagem.png");
		String name = "usuario_imagem.png";
		String originalFileName = "usuario_imagem.png";
		String contentType = "multipart/form-data";
		byte[] content = null;
		try {
			content = Files.readAllBytes(path);
		} catch (final IOException e) {
		}
		MultipartFile result = new MockMultipartFile(name,
				originalFileName, contentType, content);

		Usuario novoUsuario = new Usuario(1L, "usuarioteste@gmail.com", "123456", null, "usuarioteste");

		usuarioService.salvar(novoUsuario, result);
		System.out.println("salvou!");
		usuarioService.removerUsuario(novoUsuario.getIdUsuario());
		System.out.println("removeu!");

	}

	@Test
	@DisplayName("Teste 02- Listar filmes por paginação")
	@Sql("/Data.sql")
	void testListFilmesPagination() throws IOException, InterruptedException {

		Pageable pageable = PageRequest.of(0, 2);
		Page<Filme> pageFilme = service.listarFilmes(pageable);

		assertEquals(2, pageFilme.getContent().size());
		assertEquals(FILME_0, pageFilme.getContent().get(0));
		assertEquals(FILME_1.getNome(), pageFilme.getContent().get(1).getNome());

		service.removerFilme(FILME_0.getId());
		service.removerFilme(FILME_1.getId());

		Long FILME_3_id = Long.valueOf(3);
		service.removerFilme(FILME_3_id);
		System.out.println("removeu os filmes!");

	}

}
