package br.com.sgq.api.usuario;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
public record LoginUsuario(
    
        @NotBlank
        String login,
        @NotBlank
        String senha
) {
}
