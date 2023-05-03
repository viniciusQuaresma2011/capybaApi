package com.capyba.apirest.Usuario.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import com.capyba.apirest.Usuario.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	Usuario findByEmail(String email);

}
