package br.com.sgq.api;

import br.com.sgq.api.domain.produto.Categoria;
import br.com.sgq.api.domain.produto.CadastroProduto;
import br.com.sgq.api.domain.produto.Produto;
import br.com.sgq.api.domain.produto.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProdutoTest {

    @Autowired
    private ProdutoRepository produtoRepository;

    @BeforeEach
    public void limparBancoDeDados() {
        produtoRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve criar um novo produto")
    void deve_criar_um_novo_produto() {
        // Arrange
        CadastroProduto cadastro = new CadastroProduto("Tenis All Star", "Um Tenis muito massa!",
                "Preto", "42", 7, 299.90, Categoria.CALCADOS, null);

        // Act
        Produto produto = new Produto(cadastro);
        Produto produtoCadastrado = produtoRepository.save(produto);

        // Assert
        assertNotNull(produtoCadastrado.getId());
        assertEquals(cadastro.nome(), produtoCadastrado.getNome());
        assertEquals(cadastro.descricao(), produtoCadastrado.getDescricao());
        assertEquals(cadastro.cor(), produtoCadastrado.getCor());
        assertEquals(cadastro.tamanho(), produtoCadastrado.getTamanho());
        assertEquals(cadastro.quantidade(), produtoCadastrado.getQuantidade());
        assertEquals(cadastro.valor(), produtoCadastrado.getValor());
        assertEquals(cadastro.categoria(), produtoCadastrado.getCategoria());
    }

    @Test
    @DisplayName("Deve atualizar um novo produto existente")
    void deve_atualizar_produto_existente() {
        Produto produtoExistente = new Produto(new CadastroProduto("Camiseta Palmeiras", "Uma Camiseta de um time que não tem mundial!",
                "Verde", "G", 2, 15.00, Categoria.ROUPAS, null));
        produtoRepository.save(produtoExistente);

        Produto produtoAtualizado = new Produto(new CadastroProduto("Camiseta Corinthians", "Uma Camiseta de um time que tem mundial!",
                "Branco", "G", 7, 99.00, Categoria.ROUPAS, null));
        produtoAtualizado.setId(produtoExistente.getId());
        produtoRepository.save(produtoAtualizado);

        Produto produtoRetornado = produtoRepository.findById(produtoExistente.getId()).orElse(null);

        assertEquals(produtoAtualizado.getId(), produtoRetornado.getId());
        assertEquals(produtoAtualizado.getNome(), produtoRetornado.getNome());
        assertEquals(produtoAtualizado.getDescricao(), produtoRetornado.getDescricao());
        assertEquals(produtoAtualizado.getCor(), produtoRetornado.getCor());
        assertEquals(produtoAtualizado.getTamanho(), produtoRetornado.getTamanho());
        assertEquals(produtoAtualizado.getQuantidade(), produtoRetornado.getQuantidade());
        assertEquals(produtoAtualizado.getValor(), produtoRetornado.getValor());
        assertEquals(produtoAtualizado.getCategoria(), produtoRetornado.getCategoria());
    }

    @Test
    @DisplayName("Deve deletar um produto")
    void deve_excluir_produto() {
        Produto produto = new Produto(new CadastroProduto("Boné", "Um boné que vai te deixar careca!",
                "Preto", "Unico", 10, 100.0, Categoria.ACESSORIOS, null));
        produtoRepository.save(produto);

        produtoRepository.deleteById(produto.getId());

        assertFalse(produtoRepository.findById(produto.getId()).isPresent());
    }

    @Test
    @DisplayName("Deve inativar um produto")
    void deve_inativar_um_produto() {
        Produto produto = new Produto(new CadastroProduto("Boné", "Um boné que vai te deixar careca!",
                "Preto", "Unico", 10, 100.0, Categoria.ACESSORIOS, null));
        produto.setAtivo(true);
        produtoRepository.save(produto);

        produto.inativar();

        assertFalse(produto.isAtivo());
    }
}