package br.com.adsuema.minifood.dto.dtopedido;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoRequestDto(
        @NotBlank(message = "O nome do cliente é obrigatório")
        String customer,
        @NotNull
        @Valid
        RestauranteIdDto restaurante,
        @NotEmpty
        List<ProdutoIdDto> produtos
) {
    public record RestauranteIdDto(@NotNull(message = "O id do restaurante é obrigatório")
                                   Long id) {
    }

    public record ProdutoIdDto(@NotNull(message = "O id do produto é obrigatório")
                               Long id) {
    }
}


