package br.com.adsuema.minifood.dto.securitydto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record DadosAutenticacao(
        @NotEmpty(message = "O usuario e obrigatorio")
        @Email
        String login,
        @NotEmpty(message = "A senha e obrigatoria")
        String senha
) {
}
