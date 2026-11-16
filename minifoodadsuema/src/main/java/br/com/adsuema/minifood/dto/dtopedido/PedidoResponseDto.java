package br.com.adsuema.minifood.dto.dtopedido;

import br.com.adsuema.minifood.dto.clientedto.ClienteResponseDto;
import br.com.adsuema.minifood.dto.produtodto.ProdutoResponseDto;
import br.com.adsuema.minifood.model.Cliente;
import br.com.adsuema.minifood.model.Pedidos;
import java.util.List;

public record PedidoResponseDto(
        Long id,
        ClienteResponseDto cliente,
        Double valorTotal,
        Long restauranteId,
        List<ProdutoResponseDto> produtos
) {
    // Construtor secundário que transforma a Entity Pedidos no DTO de resposta
    public PedidoResponseDto(Pedidos pedidos) {
        this(
                pedidos.getId(),
                new ClienteResponseDto(pedidos.getCliente()),
                pedidos.getValorTotal(),
                pedidos.getRestaurante().getId(),
                pedidos.getProdutos().stream()
                        .map(ProdutoResponseDto::new)
                        .toList()
        );
    }
    }