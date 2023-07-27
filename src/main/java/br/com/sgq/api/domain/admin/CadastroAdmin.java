package br.com.sgq.api.domain.admin;

import jakarta.validation.constraints.NotBlank;
import lombok.Setter;

public record CadastroAdmin(
        @NotBlank
        String nome,
        @NotBlank
        String login,
        @NotBlank
        String senha
) {
}