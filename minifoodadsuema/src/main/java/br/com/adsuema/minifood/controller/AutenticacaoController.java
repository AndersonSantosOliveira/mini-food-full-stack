package br.com.adsuema.minifood.controller;

import br.com.adsuema.minifood.config.TokenService;
import br.com.adsuema.minifood.dto.clientedto.ClienteResponseDto;
import br.com.adsuema.minifood.dto.securitydto.CadastroClienteDto;
import br.com.adsuema.minifood.dto.securitydto.DadosAutenticacao;
import br.com.adsuema.minifood.dto.securitydto.DadosTokenJWT;
import br.com.adsuema.minifood.model.Usuarios;
import br.com.adsuema.minifood.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@RestController
@RequestMapping("/login")
public class AutenticacaoController {
    private final AuthenticationManager manager;
    private final TokenService tokenService;

    public AutenticacaoController(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;

    }

    @PostMapping
    public ResponseEntity<DadosTokenJWT> fazerLogin(@Valid @RequestBody DadosAutenticacao dadosAutenticacao) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dadosAutenticacao.login(), dadosAutenticacao.senha());
        var authentication = manager.authenticate(authenticationToken);
        var TokenJWT = tokenService.gerarToken((Usuarios) Objects.requireNonNull(authentication.getPrincipal()));
        return ResponseEntity.ok(new DadosTokenJWT(TokenJWT));
    }

}
