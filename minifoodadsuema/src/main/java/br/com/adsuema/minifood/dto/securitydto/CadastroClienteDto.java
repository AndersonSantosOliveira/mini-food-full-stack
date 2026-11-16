package br.com.adsuema.minifood.dto.securitydto;

import jakarta.validation.constraints.*;

public record CadastroClienteDto(
        @NotBlank(message = "o nome e obrigatorio")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
        String nome,
        @NotBlank(message = "Email é obrigatorio")
        @Email(message = "Formato invalalido")
        String email,

        @NotEmpty(message = "A senha é obrigatória")
        @Size(min = 8, max = 20, message = "A senha deve ter entre 8 e 20 caracteres") // Ajustado o max para dar margem a senhas fortes
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!_]).*$",
                message = "A senha deve conter ao menos uma letra maiúscula, uma minúscula, um número e um caractere especial"
        )
        String senha,

        @NotBlank(message = "O telefone e obrigatorio")
        @Pattern(
                regexp = "^\\(?\\d{2}\\)?\\s?9?\\d{4}-?\\d{4}$",
                message = "Formato de telefone inválido (ex: 11999998888 ou (11) 99999-8888)"
        )
        String telefone

) {
}
