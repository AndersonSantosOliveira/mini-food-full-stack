package br.com.adsuema.minifood.repository;

import br.com.adsuema.minifood.model.Produtos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutosRepository extends JpaRepository<Produtos, Long> {
    List<Produtos> findByRestauranteId(Long restauranteId);
}
