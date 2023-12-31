package br.com.sgq.api.domain.produto;

public record DadosListagemProduto(
        long id,
        String nome,
        String descricao,
        String cor,
        String tamanho,
        int quantidade,
        double valor,
        Categoria categoria,
        String imagem) {

    public DadosListagemProduto(Produto produto) {
        this(produto.getId(), produto.getNome(), produto.getDescricao(), produto.getCor(), produto.getTamanho(), produto.getQuantidade(),produto.getValor(), produto.getCategoria(), produto.getImagem());
    }
}