package br.com.adsuema.minifood.dto.produtodto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProdutoRequestDto(
        @NotBlank(message = "O nome não pode ser nulo")
        String nome,
        @NotBlank(message = "A descrição e obrigatoria")
        String descricao,
        @NotNull(message = "O produto deve possuir preço")
        Double preco,
        @NotBlank(message = "Imagem descritiva obrigatorio")
        String urlImagem,
        @NotNull(message = "O restaurante é obrigatório")
        Long restauranteId
) {
}
