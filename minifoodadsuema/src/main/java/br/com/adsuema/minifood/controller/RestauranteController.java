package br.com.adsuema.minifood.controller;

import br.com.adsuema.minifood.model.Restaurante;
import br.com.adsuema.minifood.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restaurante")
public class RestauranteController {
    @Autowired
   private RestauranteService restauranteService;

    //metodo que faz a busca de todos os restaurantes
   @GetMapping
    public List<Restaurante> listarRestaurante(){
        return restauranteService.listarTodosRestaurante();
    }

    //metodo que faz busca de um restaurante por um id especifico
    @GetMapping("/{id}")
    public Restaurante buscarRestauranteId(@PathVariable Long id){
       return restauranteService.buscarRestaurantePorId(id);
    }

    //metodo que cria um restaurante
    @PostMapping
    public Restaurante criarRestaurante(@RequestBody Restaurante restaurante){
       return restauranteService.criarRestaurante(restaurante);
    }

    //metodo que atualiza um restaurante existente
    @PutMapping("/{id}")
    public Restaurante atualizarRestaurante(@PathVariable Long id,@RequestBody Restaurante restaurante){
       return restauranteService.atualizarRestaurante(id,restaurante);
    }

    //metodo que deleta um restaurante pelo id
    @DeleteMapping("/{id}")
    public void deletarRestauranteId(@PathVariable Long id){
       restauranteService.deletarRestauranteId(id);
    }

}
