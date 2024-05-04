package br.com.ismyburguer.cliente.gateway.out;

import br.com.ismyburguer.cliente.entity.Cliente;

import java.util.Optional;
import java.util.UUID;

public interface ConsultarClienteAPI {
    Optional<Cliente> obterPeloClienteUsername(String username);
    boolean existsById(UUID clienteId);
    boolean existsByUsername(String username);
}
