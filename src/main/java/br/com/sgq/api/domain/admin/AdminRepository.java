package br.com.sgq.api.domain.admin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Page<Admin> findAllByAtivoTrue(Pageable paginacao);
    Page<Admin> findAllByAtivoFalse(Pageable paginacao);

    Page<Admin> findAllByAdminTrue(Pageable paginacao);
    Admin findByLogin(String login);
}