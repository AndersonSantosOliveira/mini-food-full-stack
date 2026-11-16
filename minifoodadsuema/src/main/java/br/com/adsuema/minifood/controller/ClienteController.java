package br.com.adsuema.minifood.controller;

import br.com.adsuema.minifood.dto.clientedto.ClienteRequestDto;
import br.com.adsuema.minifood.dto.clientedto.ClienteResponseDto;
import br.com.adsuema.minifood.dto.clientedto.DadosDeAtulizacaoDoCliente;
import br.com.adsuema.minifood.model.Cliente;
import br.com.adsuema.minifood.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDto> criarCliente(
            @Valid @RequestBody ClienteRequestDto clienteRequestDto,
            UriComponentsBuilder uriBuilder) {
        Cliente cliente = clienteService.criarCliente(clienteRequestDto);
        var uri = uriBuilder.path("/cliente/{id}").buildAndExpand(cliente.getId()).toUri();
        return ResponseEntity.created(uri).body(new ClienteResponseDto(cliente));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> atualizarDadosCliente(
            @PathVariable Long id,
            @Valid @RequestBody DadosDeAtulizacaoDoCliente dados) {
        Cliente cliente = clienteService.atualizarDadosCliente(id, dados);
        return ResponseEntity.ok(new ClienteResponseDto(cliente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDto> buscarClienteId(@PathVariable Long id) {
        Cliente cliente = clienteService.buscarClienteId(id);
        return ResponseEntity.ok(new ClienteResponseDto(cliente));
    }

    @GetMapping
    public ResponseEntity<Page<ClienteResponseDto>> listarClientes(
            @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        var page = clienteService.listarClientes(pageable).map(ClienteResponseDto::new);
        return ResponseEntity.ok(page);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }
}