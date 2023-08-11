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
        if (nome == null || login == null || senha == null) {
            throw new IllegalArgumentException("Dados de entrada inválidos");
        }
        if (!isSenhaSegura(senha)) {
            throw new IllegalArgumentException("A senha não atende aos requisitos de segurança");
        }
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.ativo = true;
    }

    private boolean isSenhaSegura(String senha) {
        // Verificar se a senha tem pelo menos 8 caracteres
        if (senha.length() < 8) {
            return false;
        }
        
        // Verificar se a senha contém pelo menos uma letra maiúscula
        boolean hasUpperCase = false;
        for (char c : senha.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
                break;
            }
        }
        
        // Verificar se a senha contém pelo menos um número
        boolean hasDigit = false;
        for (char c : senha.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
                break;
            }
        }
        
        // Verificar se a senha atende a todos os critérios
        return hasUpperCase && hasDigit;
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