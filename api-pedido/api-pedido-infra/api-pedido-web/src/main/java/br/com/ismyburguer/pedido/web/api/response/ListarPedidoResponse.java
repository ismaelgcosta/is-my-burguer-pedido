package br.com.ismyburguer.pedido.web.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ListarPedidoResponse {

    private String pedidoId;
    private String clienteId;
    private String status;
    private BigDecimal valorTotal;

}
