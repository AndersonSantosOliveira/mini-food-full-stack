package br.com.adsuema.minifood.dto;

import br.com.adsuema.minifood.model.Restaurante;

public record RestauranteResponse(
        Long id,
        String nome,
        String tipoComidas,
        String email,
        String address,
        Double latitude,
        Double longitude,
        Integer deliveryRadiusKm,
        String openTime,
        String closeTime
) {
    public RestauranteResponse(Restaurante restaurante) {
        this(
                restaurante.getId(),
                restaurante.getNome(),
                restaurante.getTipoComidas(),
                restaurante.getEmail(),
                restaurante.getAddress(),
                restaurante.getLatitude(),
                restaurante.getLongitude(),
                restaurante.getDeliveryRadiusKm(),
                restaurante.getOpenTime(),
                restaurante.getCloseTime()
        );
    }
}
