package br.com.adsuema.minifood.dto.clientedto;

import br.com.adsuema.minifood.model.Cliente;

public record DadosDetalhamentoCliente(
        Long id,
        String nome,
        String email,
        String telefone

) {
    public DadosDetalhamentoCliente(Cliente cliente){
       this(
               cliente.getId(),
               cliente.getNome(),
               cliente.getEmail(),
               cliente.getTelefone());
    }

}
