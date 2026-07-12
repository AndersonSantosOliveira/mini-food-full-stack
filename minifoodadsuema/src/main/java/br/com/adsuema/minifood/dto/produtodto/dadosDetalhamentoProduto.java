package br.com.adsuema.minifood.dto.produtodto;

import br.com.adsuema.minifood.model.Produtos;
import br.com.adsuema.minifood.model.Restaurante;

public record dadosDetalhamentoProduto(
        String nome,
        String Descricao,
        Double preco,
        String urlImagem,
        String category,
        Restaurante restaurante
) {
    public dadosDetalhamentoProduto(Produtos produtos){
        this(
               produtos.getNome(),
               produtos.getDescricao(),
                produtos.getPreco(),
                produtos.getUrlImagem(),
                produtos.getCategory(),
                produtos.getRestaurante()
        );
    }
}
