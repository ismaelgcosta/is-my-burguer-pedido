package br.com.ismyburguer.cliente.adapters.client;

import br.com.ismyburguer.cliente.adapters.response.ClienteResponse;
import feign.FeignException;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.UUID;

public interface ClienteAPI {

    @Headers("Content-Type: application/json")
    @RequestLine("GET /clientes/{clienteId}")
    ClienteResponse findById(@Param("clienteId") UUID clienteId);

    default boolean existsById(UUID clienteId) {
        try {
            return findById(clienteId) != null;
        } catch (FeignException e) {
            return false;
        }
    }

    @Headers("Content-Type: application/json")
    @RequestLine("GET /clientes/username/{username}")
    ClienteResponse findByUsername(@Param("username") String username);

    default boolean existsByUsername(String username) {
        try {
            return findByUsername(username) != null;
        } catch (FeignException e) {
            return false;
        }
    }


}
