package br.com.sgq.api.controller;

import br.com.sgq.api.domain.produto.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "*")
public class ProdutoController {
    @Autowired
    private ProdutoRepository repository;

    @GetMapping
    public ResponseEntity<Page<DadosListagemProduto>> listar(@PageableDefault(size = 50, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemProduto::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/inativos")
    public ResponseEntity<Page<DadosListagemProduto>> listarInativos(@PageableDefault(size = 50, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoFalse(paginacao).map(DadosListagemProduto::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhesProduto> detalharProduto(@PathVariable long id) {
        var produto = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhesProduto(produto));
    }

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid CadastroProduto dados, UriComponentsBuilder uriBuilder) {
        var produto = new Produto(dados);
        repository.save(produto);
        var uri = uriBuilder.path("/produtos/{id}").buildAndExpand(produto.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhesProduto(produto));
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoProduto dados) {
        var produto = repository.getReferenceById(dados.id());
        produto.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhesProduto(produto));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable long id) {
        var produto = repository.getReferenceById(id);
        produto.inativar();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/reativar/{id}")
    @Transactional
    public ResponseEntity reativar(@PathVariable long id) {
        var produto = repository.getReferenceById(id);
        produto.ativar();
        return ResponseEntity.noContent().build();
    }
}