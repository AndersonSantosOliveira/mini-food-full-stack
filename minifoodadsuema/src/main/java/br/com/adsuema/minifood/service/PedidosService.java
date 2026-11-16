package br.com.adsuema.minifood.service;

import br.com.adsuema.minifood.dto.dtopedido.PedidoRequestDto;
import br.com.adsuema.minifood.model.Cliente;
import br.com.adsuema.minifood.model.Pedidos;
import br.com.adsuema.minifood.model.Produtos;
import br.com.adsuema.minifood.model.Restaurante;
import br.com.adsuema.minifood.model.Usuarios;

import br.com.adsuema.minifood.repository.PedidosRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidosService {
    private final PedidosRepository pedidosRepository;
    private final ClienteService clienteService;
    private final RestauranteService restauranteService;
    private final ProdutosService produtosService;

    public PedidosService(PedidosRepository pedidosRepository,
                          ClienteService clienteService,
                          RestauranteService restauranteService,
                          ProdutosService produtosService) {
        this.pedidosRepository = pedidosRepository;
        this.clienteService = clienteService;
        this.restauranteService = restauranteService;
        this.produtosService = produtosService;
    }

    // Descobre o cliente a partir do usuário logado (token JWT)
    private Cliente obterClienteDoUsuario(Usuarios usuarioLogado) {
        if (usuarioLogado == null || usuarioLogado.getCliente() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Esta conta não está vinculada a um cliente e não pode fazer pedidos");
        }
        // Busca de novo para trabalhar com a entidade gerenciada pelo JPA
        return clienteService.buscarClienteId(usuarioLogado.getCliente().getId());
    }

    // Centraliza validações e associações para criação e atualização
    private Pedidos montarPedido(Cliente cliente, PedidoRequestDto dto) {
        Restaurante restaurante = restauranteService.buscarRestaurantePorId(dto.restaurante().id());

        List<Produtos> produtos = dto.produtos().stream()
                .map(item -> produtosService.listarProdutosPorId(item.id()))
                .collect(Collectors.toList());

        // Todo produto do pedido precisa pertencer ao restaurante escolhido
        for (Produtos produto : produtos) {
            if (produto.getRestaurante() == null
                    || !produto.getRestaurante().getId().equals(restaurante.getId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Produto ID " + produto.getId() + " não pertence ao restaurante do pedido");
            }
        }

        Pedidos pedido = new Pedidos();
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setProdutos(produtos);
        pedido.calcularValorTotal();

        return pedido;
    }

    // Criar Pedido: o cliente é o usuário logado
    @Transactional
    public Pedidos criarPedido(PedidoRequestDto dto, Usuarios usuarioLogado) {
        Cliente cliente = obterClienteDoUsuario(usuarioLogado);
        Pedidos pedido = montarPedido(cliente, dto);
        return pedidosRepository.save(pedido);
    }

    // Atualizar Pedido: mantém o cliente original do pedido
    @Transactional
    public Pedidos atualizarPedido(Long id, PedidoRequestDto dto) {
        Pedidos pedidoExistente = buscarPedidoPorId(id);
        Pedidos pedidoPreparado = montarPedido(pedidoExistente.getCliente(), dto);

        pedidoExistente.setRestaurante(pedidoPreparado.getRestaurante());
        pedidoExistente.setProdutos(pedidoPreparado.getProdutos());
        pedidoExistente.calcularValorTotal();

        return pedidosRepository.save(pedidoExistente);
    }

    // Listar todos pedidos
    @Transactional(readOnly = true)
    public Page<Pedidos> listarPedidos(Pageable pageable) {
        return pedidosRepository.findAll(pageable);
    }

    // Buscar pedido por id
    @Transactional(readOnly = true)
    public Pedidos buscarPedidoPorId(Long id) {
        return pedidosRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado"));
    }

    // Deletar pedido pelo id
    @Transactional
    public void deletarPedido(Long id) {
        Pedidos pedido = buscarPedidoPorId(id);
        pedidosRepository.delete(pedido);
    }
}