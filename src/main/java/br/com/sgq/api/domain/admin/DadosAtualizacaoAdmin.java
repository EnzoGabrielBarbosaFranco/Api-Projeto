package br.com.sgq.api.domain.admin;

import jakarta.validation.constraints.NotNull;
public record DadosAtualizacaoAdmin(@NotNull
                                    long id,
                                    String nome,
                                    String login,
                                    String senha

) {
}