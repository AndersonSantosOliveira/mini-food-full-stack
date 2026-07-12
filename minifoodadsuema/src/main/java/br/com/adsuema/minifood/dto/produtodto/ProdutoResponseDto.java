package br.com.adsuema.minifood.dto.produtodto;

import br.com.adsuema.minifood.model.Produtos;

public record ProdutoResponseDto(
        Long id,
        String nome,
        String descricao,
        Double preco,
        String urlImagem,
        String category,
        boolean available,
        RestauranteResumoDto restaurante
) {
    public ProdutoResponseDto(Produtos produto) {
        this(
                produto.getId(),
                produto.getNome(),
                produto.getDescricao(),
                produto.getPreco(),
                produto.getUrlImagem(),
                produto.getCategory(),
                produto.isAvailable(),
                produto.getRestaurante() != null
                        ? new RestauranteResumoDto(
                        produto.getRestaurante().getId(),
                        produto.getRestaurante().getNome())
                        : null
        );
    }

    public record RestauranteResumoDto(Long id, String nome) {}
}
