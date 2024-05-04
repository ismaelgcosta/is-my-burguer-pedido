package br.com.ismyburguer.controlepedido.adapters.client;

import br.com.ismyburguer.controlepedido.adapters.response.ControlePedidoResponse;
import br.com.ismyburguer.controlepedido.adapters.response.StatusControlePedido;
import br.com.ismyburguer.core.adapter.out.PersistenceDriver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@PersistenceDriver
public interface ControlePedidoRepository extends JpaRepository<ControlePedidoResponse, UUID> {

    Optional<ControlePedidoResponse> findByPedidoId(UUID pedidoId);
    List<ControlePedidoResponse> findAllByStatusControlePedidoNot(StatusControlePedido statusControlePedido);

}
