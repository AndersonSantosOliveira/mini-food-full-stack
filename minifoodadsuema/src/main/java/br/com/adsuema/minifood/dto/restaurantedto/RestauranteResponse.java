package br.com.adsuema.minifood.dto.restaurantedto;

import br.com.adsuema.minifood.model.Restaurante;

public record RestauranteResponse(
        Long id,
        String nome,
        String tipoComidas,
        String email
) {
    public RestauranteResponse(Restaurante restaurante) {
        this(
                restaurante.getId(),
                restaurante.getNome(),
                restaurante.getTipoComidas(),
                restaurante.getEmail()
                );
    }
}
