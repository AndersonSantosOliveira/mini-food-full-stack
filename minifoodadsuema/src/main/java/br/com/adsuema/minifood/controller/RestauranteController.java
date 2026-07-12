package br.com.adsuema.minifood.controller;

import br.com.adsuema.minifood.dto.restaurantedto.RestauranteRequestDto;
import br.com.adsuema.minifood.dto.restaurantedto.RestauranteResponse;
import br.com.adsuema.minifood.model.Restaurante;
import br.com.adsuema.minifood.service.RestauranteService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/restaurante")
public class RestauranteController {
    @Autowired
    private RestauranteService restauranteService;

    //metodo que faz a busca de todos os restaurantes
    @GetMapping
    public ResponseEntity<Page<RestauranteResponse>> listarRestaurante(@PageableDefault(size = 10, sort = "id") Pageable pageable) {
        var page = restauranteService.listarTodosRestaurante(pageable).
                map(RestauranteResponse::new);
        return ResponseEntity.ok(page);
    }

    //metodo que faz busca de um restaurante por um id especifico
    @GetMapping("/{id}")
    public ResponseEntity buscarRestauranteId(@PathVariable Long id) {
        Restaurante restaurante = restauranteService.buscarRestaurantePorId(id);
        var valueRestaurante = new RestauranteResponse(restaurante);
        return ResponseEntity.ok(valueRestaurante);
    }

    //metodo que cria um restaurante
    @PostMapping
    @Transactional
    public ResponseEntity<RestauranteResponse> criarRestaurante
    (@RequestBody RestauranteRequestDto restaurante, UriComponentsBuilder uriBuilder) {
        Restaurante restaurante1 = restauranteService.criarRestaurante(new Restaurante(restaurante));
        var builder = uriBuilder.path("/restaurante/{id}").buildAndExpand(restaurante1.getId()).toUri();

        return ResponseEntity.created(builder).body(new RestauranteResponse(restaurante1));
    }

    //metodo que atualiza um restaurante existente
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<RestauranteResponse> atualizarRestaurante(@PathVariable Long id, @RequestBody RestauranteRequestDto restaurante) {
        Restaurante atualizado = restauranteService.atualizarRestaurante(id, new Restaurante(restaurante));
        return ResponseEntity.ok(new RestauranteResponse(atualizado));
    }

    //metodo que deleta um restaurante pelo id
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity deletarRestauranteId(@PathVariable Long id) {
        restauranteService.deletarRestauranteId(id);
        return ResponseEntity.noContent().build();
    }

}
