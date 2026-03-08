package br.com.adsuema.minifood.repository;

import br.com.adsuema.minifood.model.Pedidos;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidosRepository extends JpaRepository<Pedidos,Long> {
}
