package br.com.adsuema.minifood.repository;

import br.com.adsuema.minifood.model.Restaurante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

}
