package br.com.adsuema.minifood.model;

import br.com.adsuema.minifood.dto.restaurantedto.RestauranteRequestDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "tablerestaurant")
public class Restaurante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "nome")
    private String nome;

    @Column(name = "tiposcomidas")
    private String tipoComidas;

    @Column(name = "email")
    @Email
    @NotBlank
    private String email;

    //construtores

    public Restaurante(RestauranteRequestDto restaurante){
        this.nome = restaurante.nome();
        this.tipoComidas = restaurante.tipoComidas();
        this.email = restaurante.email();
    }
    public Restaurante() {

    }

    public Restaurante(Long id, String nome, String tipoComidas, String email) {
        this.id = id;
        this.nome = nome;
        this.tipoComidas = tipoComidas;
        this.email = email;
    }

    //geteres e seteres

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

    public String getTipoComidas() {
        return tipoComidas;
    }

    public void setTipoComidas(String tipoComidas) {
        this.tipoComidas = tipoComidas;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}