package br.com.sgq.api.usuario;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Usuario, Long> {
  Optional<Usuario> findByNome(String nome);

  Boolean existsByNome(String nome);

  Boolean existsByLogin(String login);
}
