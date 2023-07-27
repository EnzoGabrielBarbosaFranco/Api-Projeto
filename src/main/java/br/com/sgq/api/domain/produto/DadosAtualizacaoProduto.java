package br.com.sgq.api.domain.produto;

import jakarta.validation.constraints.NotNull;

public record DadosAtualizacaoProduto(@NotNull
                                      long id,
                                      String nome,
                                      String descricao,
                                      String cor,
                                      String tamanho,
                                      int quantidade,
                                      double valor,
                                      Categoria categoria,
                                      String imagem) {
}