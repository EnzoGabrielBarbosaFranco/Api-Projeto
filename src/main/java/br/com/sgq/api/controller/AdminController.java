package br.com.sgq.api.controller;

import br.com.sgq.api.domain.admin.*;
import br.com.sgq.api.domain.produto.DadosDetalhesProduto;
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
@RequestMapping("/admins")
@CrossOrigin(origins = "*")
public class AdminController {
    @Autowired
    private AdminRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid CadastroAdmin dados, UriComponentsBuilder uriBuilder) {
        var admin = new Admin(dados);
        repository.save(admin);
        var uri = uriBuilder.path("/admins/{id}").buildAndExpand(admin.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhesAdmin(admin));
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemAdmin>> listar(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoTrue(paginacao).map(DadosListagemAdmin::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/inativos")
    public ResponseEntity<Page<DadosListagemAdmin>> listarInativos(@PageableDefault(size = 10, sort = {"nome"}) Pageable paginacao) {
        var page = repository.findAllByAtivoFalse(paginacao).map(DadosListagemAdmin::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhesAdmin> detalharAdmin(@PathVariable long id) {
        var admin = repository.getReferenceById(id);
        return ResponseEntity.ok(new DadosDetalhesAdmin(admin));
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizacaoAdmin dados) {
        var admin = repository.getReferenceById(dados.id());
        admin.atualizarInformacoes(dados);
        return ResponseEntity.ok(new DadosDetalhesAdmin(admin));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluir(@PathVariable long id) {
        var admin = repository.getReferenceById(id);
        admin.inativar();
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
