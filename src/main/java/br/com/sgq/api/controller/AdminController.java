package br.com.sgq.api.controller;

import br.com.sgq.api.domain.admin.*;
import br.com.sgq.api.service.AuthService;
import br.com.sgq.api.usuario.LoginUsuario;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/admins")
public class AdminController {
    @Autowired
    private AdminRepository repository;

      @Autowired
    private AuthService authService;

    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid CadastroAdmin dados, UriComponentsBuilder uriBuilder) {
        String senhaCriptografada = DigestUtils.md5DigestAsHex(dados.senha().getBytes());
        var admin = new Admin(HtmlUtils.htmlEscape(dados.nome()), HtmlUtils.htmlEscape(dados.login()),
                senhaCriptografada);
        repository.save(admin);
        var uri = uriBuilder.path("/admins/{id}").buildAndExpand(admin.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhesAdmin(admin));
    }

    @GetMapping("/verificarAdmin/{login}")
    public ResponseEntity<Map<String, Boolean>> verificarAdmin(@PathVariable String login) {
        Admin admin = repository.findByLogin(login);
        Map<String, Boolean> response = new HashMap<>();
        response.put("admin", admin != null && admin.isAdmin());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<String> logar(@RequestBody @Valid LoginUsuario dados) {
        String login = dados.login();
        String senha = dados.senha();
        ResponseEntity<String> respostaLogin = authService.realizarLogin(login, senha);
        if (respostaLogin.getStatusCode() == HttpStatus.OK) {
            // Se o login for bem-sucedido, extraia o token do corpo da resposta
            String token = extrairTokenDoCorpoResposta(respostaLogin.getBody());

            // Adicione o token ao cabeçalho da resposta
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);

            // Retorne a resposta com o token no cabeçalho
            return ResponseEntity.ok().headers(headers).body(respostaLogin.getBody());
        } else {
            // Se o login não for bem-sucedido, simplesmente retorne a resposta do AuthService
            return respostaLogin;
        }
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemAdmin>> listar(
            @PageableDefault(size = 50, sort = { "nome" }) Pageable paginacao) {
        var page = repository.findAllByAdminTrue(paginacao).map(DadosListagemAdmin::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/inativos")
    public ResponseEntity<Page<DadosListagemAdmin>> listarInativos(
            @PageableDefault(size = 50, sort = { "nome" }) Pageable paginacao) {
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
    private String extrairTokenDoCorpoResposta(String corpoResposta) {
        // Analise o corpo da resposta para extrair o token (ajuste conforme necessário)
        // Suponha que o token seja a última parte do corpo da resposta
        String[] partes = corpoResposta.split("\n");
        return partes[partes.length - 1].replace("Token: ", "").trim();
    }
}