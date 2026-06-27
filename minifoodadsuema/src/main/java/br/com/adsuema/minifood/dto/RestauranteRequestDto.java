package br.com.adsuema.minifood.dto;

public record RestauranteRequestDto(
        String nome,
        String tipoComidas,
        String email

) {
}
