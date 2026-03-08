package br.com.adsuema.minifood.controller;

import br.com.adsuema.minifood.model.Pedidos;
import br.com.adsuema.minifood.service.PedidosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    @Autowired
    private PedidosService pedidosService;

    //lista de pedidos
    @GetMapping
    public List<Pedidos> listarPedidos(){
        return pedidosService.listarPedidos();
    }

    // buscando um pedido especifico via id
    @GetMapping("/{id}")
    public Pedidos buscarPedidoId(@PathVariable Long id){
        return pedidosService.buscarPedidoPorId(id);
    }

    //criando um pedido
    @PostMapping()
    public Pedidos criarPedido(@RequestBody Pedidos pedidos){
        return pedidosService.criarPedido(pedidos);
    }

    //atualizando Pedido
    @PutMapping("/{id}")
    public Pedidos atualizarPedido(@PathVariable Long id, @RequestBody Pedidos pedidos){
        return pedidosService.atualizarPedido(id, pedidos);
    }

    //deletar um pedido via id
    @DeleteMapping("/{id}")
    public void deletarPedido(@PathVariable Long id){
        pedidosService.deletarPedido(id);
    }
}
