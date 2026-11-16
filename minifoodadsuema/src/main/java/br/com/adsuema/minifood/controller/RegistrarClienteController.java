package br.com.adsuema.minifood.controller;

import br.com.adsuema.minifood.config.TokenService;
import br.com.adsuema.minifood.dto.clientedto.ClienteComTokenDto;
import br.com.adsuema.minifood.dto.securitydto.CadastroClienteDto;
import br.com.adsuema.minifood.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/register")
public class RegistrarClienteController {
    private final UsuarioService usuarioService;
    private final TokenService tokenService;

    public RegistrarClienteController(UsuarioService usuarioService, TokenService tokenService) {
        this.usuarioService = usuarioService;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<ClienteComTokenDto> cadastrar(@Valid @RequestBody CadastroClienteDto cadastro,
                                                        UriComponentsBuilder uriBuilder) {
        var usuario = usuarioService.registrarUsuario(cadastro);
        var cliente = usuario.getCliente();
        var tokenJWT = tokenService.gerarToken(usuario);
        var uri = uriBuilder.path("/cliente/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).body(new ClienteComTokenDto(cliente, tokenJWT));
    }
}
