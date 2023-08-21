package br.com.sgq.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import br.com.sgq.api.domain.admin.Admin;
import br.com.sgq.api.domain.admin.AdminRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private AdminRepository adminRepository;

    public ResponseEntity<String> realizarLogin(String login, String senhaEnviada) {
        Admin admin = adminRepository.findByLogin(login);

        if (admin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuário não encontrado.");
        }
        // Minha senha criptografada enviada eh: [B@56358c1a
        // Minha senha criptografada enviada eh: [B@1a0e42f7
        String senhaCriptografadaEnviada = DigestUtils.md5DigestAsHex(senhaEnviada.getBytes());
        log.info("Minha senha criptografada enviada eh: {}", senhaCriptografadaEnviada);
        log.info("Minha senha criptografada salva eh: {}", admin.getSenha());

        if (!senhaCriptografadaEnviada.equals(admin.getSenha())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Senha incorreta.");
        }

        return ResponseEntity.ok("Login bem-sucedido." + admin.getNome());
    }
}
