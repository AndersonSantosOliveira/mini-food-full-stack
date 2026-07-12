package br.com.adsuema.minifood.controller;

import br.com.adsuema.minifood.dto.dtopedido.PedidoRequestDto;
import br.com.adsuema.minifood.dto.dtopedido.PedidoResponseDto;
import br.com.adsuema.minifood.model.Pedidos;
import br.com.adsuema.minifood.service.PedidosService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/pedido")
public class PedidoController {
    @Autowired
    private PedidosService pedidosService;

    //lista de pedidos
    @GetMapping
    public ResponseEntity<Page<PedidoResponseDto>>listarPedidos(@PageableDefault(size = 10, sort = "id") Pageable pageable){
       var page = pedidosService.listarPedidos(pageable)
                .map(PedidoResponseDto::new);
          return ResponseEntity.ok(page);
    }

    // buscando um pedido especifico via id
    @GetMapping("/{id}")
    public ResponseEntity <PedidoResponseDto> buscarPedidoId(@PathVariable Long id){
      Pedidos pedidos = pedidosService.buscarPedidoPorId(id);
    return ResponseEntity.ok(new PedidoResponseDto(pedidos));
    }

    //criando um pedido
    @Transactional
    @PostMapping()
    public ResponseEntity<PedidoResponseDto> criarPedido(@RequestBody PedidoRequestDto pedidos, UriComponentsBuilder uriBuilder){
        Pedidos pedido = pedidosService.criarPedido(new Pedidos(pedidos));

        var uri = uriBuilder.path("/pedido/{id}").buildAndExpand(pedido.getId()).toUri();
        var armazenaPedido = new PedidoResponseDto(pedido);
        return ResponseEntity.created(uri).body(armazenaPedido);
    }

    //atualizando Pedido
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity atualizarPedido(@PathVariable Long id, @RequestBody PedidoRequestDto pedidos){
      Pedidos pedido = pedidosService.atualizarPedido(id, new Pedidos(pedidos));
      return ResponseEntity.ok(new PedidoResponseDto(pedido));
    }

    //deletar um pedido via id
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity deletarPedido(@PathVariable Long id){
    pedidosService.deletarPedido(id);
    return ResponseEntity.noContent().build();
    }
}
