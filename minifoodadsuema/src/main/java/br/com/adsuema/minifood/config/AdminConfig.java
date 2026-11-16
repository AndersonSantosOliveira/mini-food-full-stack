package br.com.adsuema.minifood.config;

import br.com.adsuema.minifood.model.Usuarios;
import br.com.adsuema.minifood.repository.UsuariosRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminConfig {

    @Bean
    CommandLineRunner criarAdminSeNaoExistir(UsuariosRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String emailAdmin = "admin@fogoebrasa.com.br";

            // Verifica se o admin já está cadastrado no banco
            if (!usuarioRepository.existsByLogin(emailAdmin)) {
                Usuarios admin = new Usuarios();
                admin.setLogin(emailAdmin);
                // Codifica a senha utilizando o mesmo encoder do sistema
                admin.setSenha(passwordEncoder.encode("Admin@1234")); // Atende a qualquer regra estrita de senha com maiúscula e caractere especial!
                admin.setRole("ROLE_ADMIN");

                usuarioRepository.save(admin);
                System.out.println(">> Administrador criado automaticamente com sucesso!");
            }
        };
    }

}
