package br.com.adsuema.minifood.dto.restaurantedto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RestauranteRequestDto(
        @NotBlank(message = "O nome não pode ser nulo")
        String nome,
        @NotBlank(message = "Especificação de nicho alimenticio")
        String tipoComidas,
        @NotBlank(message = "Este campo e obrigatorio")
        @Email(message = "O simbolo de '@' e obrigatorio")
        String email
) {
}
