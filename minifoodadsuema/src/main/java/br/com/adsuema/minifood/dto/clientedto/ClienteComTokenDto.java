package br.com.adsuema.minifood.dto.clientedto;

import br.com.adsuema.minifood.model.Cliente;

public record ClienteComTokenDto(
        Long id,
        String nome,
        String email,
        String telefone,
        String token
) {
    // Construtor auxiliar para facilitar a conversão na hora de retornar a resposta
    public ClienteComTokenDto(Cliente cliente, String token) {
        this(cliente.getId(), cliente.getNome(), cliente.getEmail(), cliente.getTelefone(), token);
    }
}
