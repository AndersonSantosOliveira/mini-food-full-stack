package br.com.adsuema.minifood.repository;

import br.com.adsuema.minifood.model.Usuarios;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuariosRepository extends JpaRepository<Usuarios,Long> {

    UserDetails findByLogin(String username);

    boolean existsByLogin(String login);
}
