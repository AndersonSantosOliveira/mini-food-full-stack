package br.com.adsuema.minifood.service;

import br.com.adsuema.minifood.dto.clientedto.ClienteRequestDto;
import br.com.adsuema.minifood.dto.clientedto.DadosDeAtulizacaoDoCliente;
import br.com.adsuema.minifood.model.Cliente;
import br.com.adsuema.minifood.repository.ClienteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public Cliente criarCliente(ClienteRequestDto clienteRequesteDto) {
        if (clienteRepository.existsByEmail(clienteRequesteDto.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado");
        }
        if (clienteRepository.existsByTelefone(clienteRequesteDto.telefone())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Telefone já cadastrado");
        }
        Cliente cliente = new Cliente();
        cliente.setNome(clienteRequesteDto.nome());
        cliente.setEmail(clienteRequesteDto.email());
        cliente.setTelefone(clienteRequesteDto.telefone());
        return clienteRepository.save(cliente);
    }

    // Atualização parcial: só altera os campos que vieram no corpo
    @Transactional
    public Cliente atualizarDadosCliente(Long id, DadosDeAtulizacaoDoCliente dto) {
        if (dto.nome() == null && dto.email() == null && dto.telefone() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Informe ao menos um campo para atualizar");
        }

        Cliente cliente = buscarClienteId(id);

        if (dto.nome() != null) {
            cliente.setNome(dto.nome());
        }
        if (dto.email() != null) {
            if (clienteRepository.existsByEmailAndIdNot(dto.email(), id)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado no sistema");
            }
            cliente.setEmail(dto.email());
        }
        if (dto.telefone() != null) {
            if (clienteRepository.existsByTelefoneAndIdNot(dto.telefone(), id)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Telefone já cadastrado no sistema");
            }
            cliente.setTelefone(dto.telefone());
        }

        return clienteRepository.save(cliente);
    }

    public Cliente buscarClienteId(Long id) {
        return clienteRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    public Page<Cliente> listarClientes(Pageable pageable) {
        return clienteRepository.findAll(pageable);
    }

    @Transactional
    public void deletarCliente(Long id) {
        Cliente cliente = buscarClienteId(id);
        clienteRepository.deleteById(cliente.getId());
    }
}