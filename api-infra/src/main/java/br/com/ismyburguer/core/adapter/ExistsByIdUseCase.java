package br.com.ismyburguer.core.adapter;

import java.util.UUID;

public interface ExistsByIdUseCase<T> {

    boolean existsById(UUID id);
}
