package br.com.adsuema.minifood.dto.dtopedido;

import java.util.List;

public record PedidoRequestDto(
        String customer,
        RestauranteIdDto restaurante,
        List<ProdutoIdDto> produtos
) {
    public record RestauranteIdDto(Long id) {}
    public record ProdutoIdDto(Long id) {}
}


