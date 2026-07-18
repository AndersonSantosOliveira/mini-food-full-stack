package br.com.adsuema.minifood.controller;

import br.com.adsuema.minifood.dto.produtodto.ProdutoRequestDto;
import br.com.adsuema.minifood.dto.produtodto.ProdutoResponseDto;
import br.com.adsuema.minifood.dto.produtodto.dadosDetalhamentoProduto;
import br.com.adsuema.minifood.model.Produtos;
import br.com.adsuema.minifood.service.ProdutosService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private ProdutosService produtosService;

    //buscando a lista de produtos
    @GetMapping
    public ResponseEntity<Page<ProdutoResponseDto>> listarProdutos(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        var page = produtosService.listarProdutos(pageable)
                .map(ProdutoResponseDto::new);
        return ResponseEntity.ok(page);
    }

    //buscando um produto especifico via id
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> listarProdutoId(@PathVariable Long id) {
        Produtos produtos = produtosService.listarProdutosPorId(id);
        return ResponseEntity.ok(new ProdutoResponseDto(produtos));
    }

    //criando um produto
    @Transactional
    @PostMapping
    public ResponseEntity criarProduto(@RequestBody @Valid ProdutoRequestDto produtos, UriComponentsBuilder uriBuilder) {
        Produtos produto = produtosService.criarProdutos(new Produtos(produtos));
        var uri = uriBuilder.path("/produtos/{id}").buildAndExpand(produto.getId()).toUri();

        return ResponseEntity.created(uri).body(new dadosDetalhamentoProduto(produto));
    }

    //atualizando produto
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<dadosDetalhamentoProduto> atualizarProduto(@RequestBody @Valid ProdutoRequestDto produtos, @PathVariable Long id) {
        Produtos atualizado = produtosService.atualizarProdutos(new Produtos(produtos), id);
        return ResponseEntity.ok(new dadosDetalhamentoProduto(atualizado));
    }

    @Transactional
    @DeleteMapping(value = "/{id}")
    public ResponseEntity deletarProdutoId(@PathVariable Long id) {
        produtosService.deletarProdutoId(id);
        return ResponseEntity.noContent().build();
    }
}
