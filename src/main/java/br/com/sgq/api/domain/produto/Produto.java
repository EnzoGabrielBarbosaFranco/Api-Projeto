package br.com.sgq.api.domain.produto;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.*;

@Table(name = "produtos")
@Entity(name = "Produto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private String cor;
    private String tamanho;
    private int quantidade;
    private double valor;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    private boolean ativo;
    private String imagem;


    public Produto(CadastroProduto dados) {
        this.ativo = true;
        this.nome = dados.nome();
        this.cor = dados.cor();
        this.descricao = dados.descricao();
        this.quantidade = dados.quantidade();
        this.tamanho = dados.tamanho();
        this.valor = dados.valor();
        this.categoria = dados.categoria();
        this.imagem = dados.imagem();
    }

    public void atualizarInformacoes(DadosAtualizacaoProduto dados) {
        this.nome = dados.nome() != null ? dados.nome() : this.nome;
        this.cor = dados.cor() != null ? dados.cor() : this.cor;
        this.descricao = dados.descricao() != null ? dados.descricao() : this.descricao;
        this.quantidade = dados.quantidade() != 0 ? dados.quantidade() : this.quantidade;
        this.tamanho = dados.tamanho() != null ? dados.tamanho() : this.tamanho;
        this.valor = dados.valor() != 0 ? dados.valor() : this.valor;
        this.categoria = dados.categoria() != null ? dados.categoria() : this.categoria;
        this.imagem = dados.imagem() != null ? dados.imagem() : this.imagem;
    }

    public void inativar() {
        this.ativo = false;
    }
    public void ativar() {
        this.ativo = true;
    }
}