package br.com.ismyburguer.cliente.adapters.response;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(of = "clienteId")
public class ClienteResponse {

    private UUID clienteId = UUID.randomUUID();

    @NotBlank(message = "Informe o campo nome")
    @Size(min = 3, message = "O nome deve conter pelo menos 3 letras")
    private String nome;

    private String sobrenome;

    @Email(message = "Informe um e-mail válido")
    private String email;

    @CPF(message = "Informe um CPF válido")
    private String cpf;

    private boolean ativo = true;

    @NotBlank(message = "Informe o campo username")
    @Size(min = 3, message = "O username deve conter pelo menos 3 letras")
    private String username;

    public ClienteResponse() {
    }

    public ClienteResponse(String nome, String sobrenome, String email, String cpf, String username) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.cpf = cpf;
        this.username = username;
    }

    public Optional<String> getCpf() {
        return Optional.ofNullable(cpf);
    }

}
