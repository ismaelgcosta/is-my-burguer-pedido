package br.com.ismyburguer.produto.adapters.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;

@Entity
@Getter
@Setter
@Table(name = "produto")
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of = "produtoId")
public class ProdutoModel {

    @Id
    private UUID produtoId = UUID.randomUUID();

    @NotBlank(message = "Informe a descrição do produto")
    @Size(min = 3, message = "A descrição deve conter pelo menos 3 letras")
    @NonNull
    private String descricao;

    @NotNull(message = "Informe a categoria do produto")
    @Enumerated(STRING)
    @NonNull
    private Categoria categoria;

    @NotNull(message = "Informe o preço do produto")
    @DecimalMin(value = "0.01", message = "O preço do produto deve ser de no mínimo R$0,01")
    @NonNull
    private BigDecimal preco;

    private boolean ativo = true;

}
