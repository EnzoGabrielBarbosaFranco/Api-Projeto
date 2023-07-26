package br.com.sgq.api.domain.produto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CadastroProduto(
        @NotBlank
        String nome,
        @NotBlank
        String descricao,
        @NotBlank
        String cor,
        @NotBlank
        String tamanho,
        @Min(1)
        int quantidade,
        @DecimalMin("1.00")
        double valor,
        @NotNull
        Categoria categoria,
        @Lob
        String imagem) {
}
