package br.com.sgq.api.domain.produto;

import br.com.sgq.api.domain.produto.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Page<Produto> findAllByAtivoTrue(Pageable paginacao);

    Page<Produto> findAllByAtivoFalse(Pageable paginacao);
}
