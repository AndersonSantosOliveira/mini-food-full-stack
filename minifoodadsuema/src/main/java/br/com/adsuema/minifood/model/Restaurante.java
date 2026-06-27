package br.com.adsuema.minifood.model;

import br.com.adsuema.minifood.dto.RestauranteRequestDto;
import br.com.adsuema.minifood.dto.RestauranteResponse;
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

    private Double latitude;
    private Double longitude;
    private String address;
    private Integer deliveryRadiusKm = 10;
    private String openTime = "08:00";
    private String closeTime = "22:00";

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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getDeliveryRadiusKm() {
        return deliveryRadiusKm;
    }

    public void setDeliveryRadiusKm(Integer deliveryRadiusKm) {
        this.deliveryRadiusKm = deliveryRadiusKm;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }
}