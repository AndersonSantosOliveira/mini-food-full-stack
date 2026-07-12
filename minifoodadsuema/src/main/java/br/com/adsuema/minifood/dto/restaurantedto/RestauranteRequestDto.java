package br.com.adsuema.minifood.dto.restaurantedto;

public record RestauranteRequestDto(
        String nome,
        String tipoComidas,
        String email

) {
}
