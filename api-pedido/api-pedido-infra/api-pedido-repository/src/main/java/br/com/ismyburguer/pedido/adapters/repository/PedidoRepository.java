package br.com.ismyburguer.pedido.adapters.repository;

import br.com.ismyburguer.core.adapter.out.PersistenceDriver;
import br.com.ismyburguer.pedido.adapters.entity.PedidoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

@PersistenceDriver
public interface PedidoRepository extends JpaRepository<PedidoModel, UUID> {
}
