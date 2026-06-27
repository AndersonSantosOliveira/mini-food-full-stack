package br.com.adsuema.minifood.controller;

import br.com.adsuema.minifood.dto.PedidoRequestDto;
import br.com.adsuema.minifood.dto.PedidoResponseDto;
import br.com.adsuema.minifood.model.Pedidos;
import br.com.adsuema.minifood.service.PedidosService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/pedido")
public class PedidoController {
    @Autowired
    private PedidosService pedidosService;

    //lista de pedidos
    @GetMapping
    public List<PedidoResponseDto> listarPedidos(){
        return pedidosService.listarPedidos()
                .stream().map(PedidoResponseDto::new).toList();
    }

    // buscando um pedido especifico via id
    @GetMapping("/{id}")
    public PedidoResponseDto buscarPedidoId(@PathVariable Long id){
      Pedidos pedidos = pedidosService.buscarPedidoPorId(id);
      return new PedidoResponseDto(pedidos);
    }

    //criando um pedido
    @Transactional
    @PostMapping()
    public PedidoResponseDto criarPedido(@RequestBody PedidoRequestDto pedidos){
        Pedidos pedido = pedidosService.criarPedido(new Pedidos(pedidos));
        return new PedidoResponseDto(pedido);
    }

    //atualizando Pedido
    @PutMapping("/{id}")
    public PedidoResponseDto atualizarPedido(@PathVariable Long id, @RequestBody PedidoRequestDto pedidos){
      Pedidos pedido = pedidosService.atualizarPedido(id, new Pedidos(pedidos));
      return new PedidoResponseDto(pedido);
    }

    //deletar um pedido via id
    @DeleteMapping("/{id}")
    public void deletarPedido(@PathVariable Long id){
        pedidosService.deletarPedido(id);
    }
}
