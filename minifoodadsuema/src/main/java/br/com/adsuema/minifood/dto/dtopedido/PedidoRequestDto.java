package br.com.adsuema.minifood.dto.dtopedido;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PedidoRequestDto(
        @NotNull(message = "O restaurante é obrigatório")
        @Valid
        RestauranteIdDto restaurante,

        @NotEmpty(message = "O pedido precisa ter ao menos um produto")
        List<@NotNull(message = "Produto inválido") @Valid ProdutoIdDto> produtos
) {
    public record RestauranteIdDto(
            @NotNull(message = "O id do restaurante é obrigatório")
            Long id) {
    }

    public record ProdutoIdDto(
            @NotNull(message = "O id do produto é obrigatório")
            Long id) {
    }
}