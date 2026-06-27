package br.com.adsuema.minifood.service;

import br.com.adsuema.minifood.model.Produtos;
import br.com.adsuema.minifood.model.Restaurante;
import br.com.adsuema.minifood.repository.ProdutosRepository;
import br.com.adsuema.minifood.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProdutosService {
    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    private ProdutosRepository produtosRepository;

    //Metodo Criar produtos;
    public Produtos criarProdutos(Produtos produtos) {
        Long idRestaurante = produtos.getRestaurante().getId();
        Restaurante restaurante = restauranteRepository.findById(idRestaurante)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurante não encontrado"));
        produtos.setRestaurante(restaurante);
        return produtosRepository.save(produtos);
    }

    //Metodo de atualizar Produtos
    public Produtos atualizarProdutos(Produtos produtos, Long idProdutos) {
        Produtos produtosExistentes = produtosRepository.findById(idProdutos)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
        produtosExistentes.setNome(produtos.getNome());
        produtosExistentes.setDescricao(produtos.getDescricao());
        produtosExistentes.setPreco(produtos.getPreco());
        produtosExistentes.setUrlImagem(produtos.getUrlImagem());

        //mudando de restaurante
        if (produtos.getRestaurante() != null) {
            Long id = produtos.getRestaurante().getId();
            Restaurante restaurante = restauranteRepository.
                    findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Restaurante não encontrado"));
            produtosExistentes.setRestaurante(restaurante);
        }
        return produtosRepository.save(produtosExistentes);
    }

    //metodo de listar Produtos
    public List<Produtos> listarProdutos(){
        return produtosRepository.findAll();
    }

    //metodo de buscar Produtos
    public Produtos listarProdutosPorId(Long id){
        return produtosRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    //metodo de deletar Produtos por id
    public void deletarProdutoId(Long id){
        if (!produtosRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");
        }
        produtosRepository.deleteById(id);
    }
}