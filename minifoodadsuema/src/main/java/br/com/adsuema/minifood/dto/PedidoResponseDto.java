package br.com.adsuema.minifood.dto;

import br.com.adsuema.minifood.model.Pedidos;
import java.util.List;

public record PedidoResponseDto(
        Long id,
        String customer,
        Double valorTotal,
        Long restauranteId,
        List<ProdutoResponseDto> produtos
) {
    // Construtor secundário que transforma a Entity Pedidos no DTO de resposta
    public PedidoResponseDto(Pedidos pedidos) {
        this(
                pedidos.getId(),
                pedidos.getCustomer(),
                pedidos.getValorTotal(),
                pedidos.getRestaurante().getId(),
                pedidos.getProdutos().stream()
                        .map(ProdutoResponseDto::new)
                        .toList()
        );
    }
    }