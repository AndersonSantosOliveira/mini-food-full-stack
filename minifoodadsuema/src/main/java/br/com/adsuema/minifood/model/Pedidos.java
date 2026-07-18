package br.com.adsuema.minifood.model;

import br.com.adsuema.minifood.dto.dtopedido.PedidoRequestDto;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

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
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    @Column(name = "customer", nullable = false)
    private String customer;

    @Column(nullable = false)
    private Double valorTotal;

    public Pedidos(PedidoRequestDto pedidos) {
        this.customer = pedidos.customer();
        this.restaurante = new Restaurante();
        this.restaurante.setId(pedidos.restaurante().id());

        if (pedidos.produtos() != null) {
            this.produtos = pedidos.produtos().stream()
                    .map(prod -> {
                        Produtos p = new Produtos();
                        p.setId(prod.id());
                        return p;
                    }).collect(Collectors.toList());
        }
    }


    //criando construtores
    public Pedidos() {
    }

    public Pedidos(Long id, List<Produtos> produtos, Restaurante
            restaurante, String customer, Double valorTotal) {
        this.id = id;
        this.produtos = produtos;
        this.restaurante = restaurante;
        this.customer = customer;
        this.valorTotal = valorTotal;
    }
    //criando geteres e seteres

    @Column(name = "valor_total")
    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void calcularValorTotal() {
        if (this.produtos != null && !this.produtos.isEmpty()) {
            this.valorTotal = this.produtos.stream().mapToDouble(Produtos::getPreco).sum();
        } else {
            this.valorTotal = 0.0;
        }
    }

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
}