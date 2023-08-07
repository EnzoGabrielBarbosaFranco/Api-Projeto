package br.com.sgq.api.usuario;

import br.com.sgq.api.utils.EntidadeBase;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;


import javax.persistence.Column;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter @Setter
public class Usuario extends EntidadeBase {
    
    @Column(length = 20, nullable = false, unique = true)
	private String nome;

	@Column(length = 50, nullable = false, unique = true)
	private String login;

	@Column(length = 120, nullable = false)
	private String senha;

    @OneToMany
	private Set<Role> roles = new HashSet<>();

    public Usuario(String nome, String login, String senha) {
		this.nome = nome;
		this.login = login;
		this.senha = senha;
	}
}
