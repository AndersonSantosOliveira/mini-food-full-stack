package br.com.adsuema.minifood.controller;

import br.com.adsuema.minifood.model.Produtos;
import br.com.adsuema.minifood.service.ProdutosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private ProdutosService produtosService;

    //buscando a lista de produtos
    @GetMapping
    public List<Produtos> listarProdutos() {
        return produtosService.listarProdutos();
    }

    //buscando um produto especifico via id
    @GetMapping("/{id}")
    public Produtos listarProdutoId(@PathVariable Long id) {
        return produtosService.listarProdutosPorId(id);
    }

    //criando um produto
    @PostMapping
    public Produtos criarProduto(@RequestBody Produtos produtos) {
        return produtosService.criarProdutos(produtos);
    }

    //atualizando produto
    @PutMapping("/{id}")
    public Produtos atualizarProduto(@RequestBody Produtos produtos, @PathVariable Long id) {
        return produtosService.atualizarProdutos(produtos, id);
    }
    @DeleteMapping("/{id}")
    public void deletarProdutoId(@PathVariable Long id){
        produtosService.deletarProdutoId(id);
    }
}
