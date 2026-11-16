package br.com.adsuema.minifood.service;

import br.com.adsuema.minifood.dto.securitydto.CadastroClienteDto;
import br.com.adsuema.minifood.model.Cliente;
import br.com.adsuema.minifood.model.Usuarios;
import br.com.adsuema.minifood.repository.ClienteRepository;
import br.com.adsuema.minifood.repository.UsuariosRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UsuarioService implements UserDetailsService {
    private final ClienteRepository clienteRepository;
    private final UsuariosRepository usuariosRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(ClienteRepository clienteRepository, UsuariosRepository usuariosRepository, PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.usuariosRepository = usuariosRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails usuario = usuariosRepository.findByLogin(username);

        // O contrato do UserDetailsService manda lançar esta exceção quando o usuário não existe.
        // O Spring Security a converte em BadCredentialsException, que o TratarErros devolve como 401.
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return usuario;
    }

    // Cadastro público: cria o Cliente e o login na mesma transação.
    // Se qualquer passo falhar, nada fica salvo.
    @Transactional
    public Usuarios registrarUsuario(CadastroClienteDto cadastro) {
        if (usuariosRepository.existsByLogin(cadastro.email())
                || clienteRepository.existsByEmail(cadastro.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este e-mail já está cadastrado");
        }
        if (clienteRepository.existsByTelefone(cadastro.telefone())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este telefone já está cadastrado");
        }

        Cliente cliente = new Cliente();
        cliente.setNome(cadastro.nome());
        cliente.setEmail(cadastro.email());
        cliente.setTelefone(cadastro.telefone());

        Usuarios usuario = new Usuarios();
        usuario.setLogin(cadastro.email());
        usuario.setSenha(passwordEncoder.encode(cadastro.senha()));
        usuario.setRole("ROLE_CLIENTE");
        usuario.setCliente(cliente); // o cascade salva o Cliente junto

        return usuariosRepository.save(usuario);
    }

}
