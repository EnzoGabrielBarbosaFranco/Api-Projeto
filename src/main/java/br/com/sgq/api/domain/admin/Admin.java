package br.com.sgq.api.domain.admin;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Table(name = "admins")
@Entity(name = "admin")
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String login;

    private String senha;

    private boolean ativo;

    public Admin(CadastroAdmin dados) {
        this.nome = dados.nome();
        this.login = dados.login();
        this.senha = dados.senha();
        this.ativo = true;
    }

    public Admin(String nome, String login, String senha){
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.ativo = true;
    }

    public void atualizarInformacoes(DadosAtualizacaoAdmin dados) {
        this.nome = dados.nome() != null ? dados.nome() : this.nome;
        this.login = dados.login() != null ? dados.login() : this.login;
        this.senha = dados.senha() != null ? dados.senha() : this.senha;
    }

    public void inativar() {
        this.ativo = false;
    }

    public void ativar() {
        this.ativo = true;
    }
}