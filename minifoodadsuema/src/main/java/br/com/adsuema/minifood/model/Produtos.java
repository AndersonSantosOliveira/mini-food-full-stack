package br.com.adsuema.minifood.model;

import br.com.adsuema.minifood.dto.produtodto.ProdutoRequestDto;
import jakarta.persistence.*;

@Entity
@Table(name = "tabelaprodutos")
public class Produtos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(name = "descricao")
    private String descricao;

    private Double preco;

    private String urlImagem;

    private String category;

    private boolean available = true;

    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    private Restaurante restaurante;

    public Produtos(ProdutoRequestDto produtos) {
        this.nome = produtos.nome();
        this.descricao = produtos.descricao();
        this.preco = produtos.preco();
        this.urlImagem = produtos.urlImagem();
        this.restaurante = new Restaurante();
        this.restaurante.setId(produtos.restauranteId());

    }

    //criando contrutores
    public Produtos() {

    }

    public Produtos(Long id, String nome, String descricao,
                    Double preco, Restaurante restaurante, String urlImagem) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.restaurante = restaurante;
        this.urlImagem = urlImagem;
    }

    //criando geteres e seteres


    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}