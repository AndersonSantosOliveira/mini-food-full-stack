package br.com.adsuema.minifood.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tabelapedidos")
public class Pedidos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "pedido_produto",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "produto_id")
    )
    private List<Produtos> produtos;

    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    private Restaurante restaurante;

    @Column(name = "customer")
    private String customer;

    //criando construtores
    public Pedidos() {
    }

    public Pedidos(Long id, List<Produtos> produtos, Restaurante
            restaurante, String customer) {
        this.id = id;
        this.produtos = produtos;
        this.restaurante = restaurante;
        this.customer = customer;
    }
    //criando geteres e seteres

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Produtos> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produtos> produtos) {
        this.produtos = produtos;
    }

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Pedidos{" +
                "id=" + id +
                ", produtos=" + produtos +
                ", restaurante=" + restaurante +
                ", customer='" + customer + '\'' +
                '}';
    }
}
