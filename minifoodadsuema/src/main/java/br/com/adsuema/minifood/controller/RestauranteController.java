package br.com.adsuema.minifood.controller;

import br.com.adsuema.minifood.dto.RestauranteRequestDto;
import br.com.adsuema.minifood.dto.RestauranteResponse;
import br.com.adsuema.minifood.model.Restaurante;
import br.com.adsuema.minifood.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/restaurante")
public class RestauranteController {
    @Autowired
   private RestauranteService restauranteService;

    //metodo que faz a busca de todos os restaurantes
   @GetMapping
    public List<RestauranteResponse> listarRestaurante(){
       return restauranteService.listarTodosRestaurante().
               stream().map(RestauranteResponse::new).toList();
    }

    //metodo que faz busca de um restaurante por um id especifico
    @GetMapping("/{id}")
    public RestauranteResponse buscarRestauranteId(@PathVariable Long id){
       Restaurante restaurante =  restauranteService.buscarRestaurantePorId(id);
       return new RestauranteResponse(restaurante);
    }

    //metodo que cria um restaurante
    @PostMapping
    public RestauranteResponse criarRestaurante(@RequestBody RestauranteRequestDto restaurante){
       Restaurante restaurante1 = restauranteService.criarRestaurante(new Restaurante(restaurante));
       return new RestauranteResponse(restaurante1);
    }

    //metodo que atualiza um restaurante existente
    @PutMapping("/{id}")
    public RestauranteResponse atualizarRestaurante(@PathVariable Long id,@RequestBody RestauranteRequestDto restaurante){
       Restaurante atualizado = restauranteService.atualizarRestaurante(id,new Restaurante(restaurante));
       return new RestauranteResponse(atualizado);
    }

    //metodo que deleta um restaurante pelo id
    @DeleteMapping("/{id}")
    public void deletarRestauranteId(@PathVariable Long id){
       restauranteService.deletarRestauranteId(id);
    }

}
