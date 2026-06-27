package br.com.adsuema.minifood.controller;

import br.com.adsuema.minifood.dto.ProdutoRequestDto;
import br.com.adsuema.minifood.dto.ProdutoResponseDto;
import br.com.adsuema.minifood.model.Produtos;
import br.com.adsuema.minifood.service.ProdutosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private ProdutosService produtosService;

    //buscando a lista de produtos
    @GetMapping
    public List<ProdutoResponseDto> listarProdutos() {
        return produtosService.listarProdutos().stream()
                .map(ProdutoResponseDto::new).toList();
    }

    //buscando um produto especifico via id
    @GetMapping("/{id}")
    public ProdutoResponseDto listarProdutoId(@PathVariable Long id) {
        Produtos produtos = produtosService.listarProdutosPorId(id);
        return new ProdutoResponseDto(produtos);
    }

    //criando um produto
    @PostMapping
    public ProdutoResponseDto criarProduto(@RequestBody ProdutoRequestDto produtos) {
        Produtos produto = produtosService.criarProdutos(new Produtos(produtos));
        return new ProdutoResponseDto(produto);
    }

    //atualizando produto
    @PutMapping("/{id}")
    public ProdutoResponseDto atualizarProduto(@RequestBody ProdutoRequestDto produtos, @PathVariable Long id) {
        Produtos atualizado = produtosService.atualizarProdutos(new Produtos(produtos), id);
        return new ProdutoResponseDto(atualizado);
    }
    @DeleteMapping("/{id}")
    public void deletarProdutoId(@PathVariable Long id){
        produtosService.deletarProdutoId(id);
    }
}
