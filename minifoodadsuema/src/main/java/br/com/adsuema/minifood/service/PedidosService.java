package br.com.adsuema.minifood.service;

import br.com.adsuema.minifood.model.Pedidos;
import br.com.adsuema.minifood.model.Produtos;
import br.com.adsuema.minifood.model.Restaurante;
import br.com.adsuema.minifood.repository.PedidosRepository;
import br.com.adsuema.minifood.repository.ProdutosRepository;
import br.com.adsuema.minifood.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidosService {
    @Autowired
    private PedidosRepository pedidosRepository;



    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ProdutosRepository produtosRepository;

    // Centraliza validações e associações para criação e atualização
    private Pedidos prepararPedido(Pedidos pedido) {
        // Validar e buscar restaurante
        Long idRestaurante = pedido.getRestaurante().getId();
        Restaurante restaurante = restauranteRepository.findById(idRestaurante)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurante do pedido não encontrado"));

        // Validar e buscar produtos
        List<Produtos> produtosValidados = pedido.getProdutos().stream()
                .map(prod -> produtosRepository.findById(prod.getId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Produto ID " + prod.getId() + " não encontrado")))
                .collect(Collectors.toList());

        pedido.setRestaurante(restaurante);
        pedido.setProdutos(produtosValidados);
        pedido.calcularValorTotal();

        return pedido;
    }

    // Criar Pedido
    public Pedidos criarPedido(Pedidos pedido) {
        pedido.setId(null); // Garante criação
        Pedidos pedidoPreparado = prepararPedido(pedido);
        return pedidosRepository.save(pedidoPreparado);
    }

    // Atualizar Pedido
    public Pedidos atualizarPedido(Long id, Pedidos novoPedido) {
        Pedidos pedidoExistente = pedidosRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));

        pedidoExistente.setCustomer(novoPedido.getCustomer());

        // Prepara associações e validações
        novoPedido.setId(id);
        Pedidos pedidoPreparado = prepararPedido(novoPedido);

        pedidoExistente.setProdutos(pedidoPreparado.getProdutos());
        pedidoExistente.setRestaurante(pedidoPreparado.getRestaurante());

        return pedidosRepository.save(pedidoExistente);
    }

    // Listar todos pedidos
    public Page<Pedidos> listarPedidos(Pageable pageable) {
        return pedidosRepository.findAll(pageable);
    }

    // Buscar pedido por id
    public Pedidos buscarPedidoPorId(Long id) {
        return pedidosRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
    }

    // Deletar pedido pelo id
    public void deletarPedido(Long id) {
        if (!pedidosRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado");

        }
        pedidosRepository.deleteById(id);
    }

}