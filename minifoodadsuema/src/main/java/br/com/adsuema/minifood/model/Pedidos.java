package br.com.adsuema.minifood.model;

import jakarta.persistence.*;
import java.util.List;
import java.util.Objects;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(name = "valor_total", nullable = false)
    private Double valorTotal;

    public Pedidos() {
    }

    public Pedidos(Long id, List<Produtos> produtos, Restaurante restaurante, Cliente cliente, Double valorTotal) {
        this.id = id;
        this.produtos = produtos;
        this.restaurante = restaurante;
        this.cliente = cliente;
        this.valorTotal = valorTotal;
    }

    public void calcularValorTotal() {
        if (this.produtos != null && !this.produtos.isEmpty()) {
            this.valorTotal = this.produtos.stream()
                    .mapToDouble(prod -> prod.getPreco() != null ? prod.getPreco() : 0.0)
                    .sum();
        } else {
            this.valorTotal = 0.0;
        }
    }

    public Long getId() {
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedidos pedidos = (Pedidos) o;
        return id != null && Objects.equals(id, pedidos.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}