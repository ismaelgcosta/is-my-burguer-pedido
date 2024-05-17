package br.com.ismyburguer.cliente.adapter.interfaces.out;

import br.com.ismyburguer.cliente.entity.Cliente;
import br.com.ismyburguer.core.adapter.ExistsByIdUseCase;
import br.com.ismyburguer.core.adapter.ExistsByUsernameUseCase;

public interface ConsultarClienteUseCase extends ExistsByIdUseCase<Cliente>, ExistsByUsernameUseCase<Cliente> {

    Cliente buscarPorUsername(ConsultaClientePorUsername query);

    record ConsultaClientePorUsername(String username) {

    }
}
