package br.com.adsuema.minifood.dto;

public record ProdutoRequestDto(
        String nome,
        String descricao,
        Double preco,
        String urlImagem,
       Long restauranteId
) {
}
