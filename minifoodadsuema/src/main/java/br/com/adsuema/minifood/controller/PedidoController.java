package br.com.adsuema.minifood.controller;

import br.com.adsuema.minifood.dto.dtopedido.PedidoRequestDto;
import br.com.adsuema.minifood.dto.dtopedido.PedidoResponseDto;
import br.com.adsuema.minifood.model.Pedidos;
import br.com.adsuema.minifood.model.Usuarios;
import br.com.adsuema.minifood.service.PedidosService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/pedido")
public class PedidoController {

    private final PedidosService pedidosService;

    public PedidoController(PedidosService pedidosService) {
        this.pedidosService = pedidosService;
    }

    // Lista de pedidos
    @GetMapping
    public ResponseEntity<Page<PedidoResponseDto>> listarPedidos(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        var page = pedidosService.listarPedidos(pageable).map(PedidoResponseDto::new);
        return ResponseEntity.ok(page);
    }

    // Buscando um pedido específico via id
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> buscarPedidoId(@PathVariable Long id) {
        Pedidos pedido = pedidosService.buscarPedidoPorId(id);
        return ResponseEntity.ok(new PedidoResponseDto(pedido));
    }

    // Criando um pedido: o cliente é o usuário logado (vem do token JWT)
    @PostMapping
    public ResponseEntity<PedidoResponseDto> criarPedido(
            @RequestBody @Valid PedidoRequestDto pedidoRequestDto,
            @AuthenticationPrincipal Usuarios usuarioLogado,
            UriComponentsBuilder uriBuilder) {
        Pedidos pedido = pedidosService.criarPedido(pedidoRequestDto, usuarioLogado);
        var uri = uriBuilder.path("/pedido/{id}").buildAndExpand(pedido.getId()).toUri();
        return ResponseEntity.created(uri).body(new PedidoResponseDto(pedido));
    }

    // Atualizando pedido (mantém o cliente original do pedido)
    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDto> atualizarPedido(
            @PathVariable Long id,
            @RequestBody @Valid PedidoRequestDto pedidoRequestDto) {
        Pedidos pedido = pedidosService.atualizarPedido(id, pedidoRequestDto);
        return ResponseEntity.ok(new PedidoResponseDto(pedido));
    }

    // Deletar um pedido via id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        pedidosService.deletarPedido(id);
        return ResponseEntity.noContent().build();
    }
}