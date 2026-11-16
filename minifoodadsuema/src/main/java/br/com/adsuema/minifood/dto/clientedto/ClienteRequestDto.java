package br.com.adsuema.minifood.dto.clientedto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteRequestDto(
        @NotBlank(message = "o nome e obrigatorio")
        @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
        String nome,
        @NotBlank(message = "Email é obrigatorio")
        @Email(message = "Formato invalalido")
        String email,
        @NotBlank(message = "O telefone e obrigatorio")
        @Pattern(
                regexp = "^\\(?\\d{2}\\)?\\s?9?\\d{4}-?\\d{4}$",
                message = "Formato de telefone inválido (ex: 11999998888 ou (11) 99999-8888)"
        )
        String telefone
) {

}
