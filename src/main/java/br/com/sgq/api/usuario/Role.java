package br.com.sgq.api.usuario;


import jakarta.persistence.Entity;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import br.com.sgq.api.utils.EntidadeBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role extends EntidadeBase {
    
	@Enumerated(EnumType.STRING)
	@Column(length = 20)
	private ERole name;
}
