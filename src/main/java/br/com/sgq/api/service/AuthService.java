package br.com.sgq.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import br.com.sgq.api.domain.admin.Admin;
import br.com.sgq.api.domain.admin.AdminRepository;
import br.com.sgq.api.domain.admin.TentativasLogin;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AuthService {

    @Autowired
    private AdminRepository adminRepository;

    private static final int MAX_TENTATIVAS_FALHAS = 20;

    // Mapeia os usuários bloqueados por suas IDs
    private Map<Long, TentativasLogin> usuariosBloqueados = new HashMap<>();

    public ResponseEntity<String> realizarLogin(String login, String senhaEnviada) {
        Admin admin = adminRepository.findByLogin(login);

        if (admin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuário não encontrado.");
        }
        if (!admin.isAtivo()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuário bloqueado.");
        }

        TentativasLogin tentativasLogin = usuariosBloqueados.get(admin.getId());
        if (tentativasLogin != null && tentativasLogin.getQuantidadeDeFalhas() >= MAX_TENTATIVAS_FALHAS) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Usuário bloqueado devido a várias tentativas de login falhas.");
        }

        String senhaCriptografadaEnviada = DigestUtils.md5DigestAsHex(senhaEnviada.getBytes());
        log.info("Minha senha criptografada enviada eh: {}", senhaCriptografadaEnviada);
        log.info("Minha senha criptografada salva eh: {}", admin.getSenha());

        if (!senhaCriptografadaEnviada.equals(admin.getSenha())) {
            // Adicionar uma tentativa falha
            if (tentativasLogin == null) {
                tentativasLogin = new TentativasLogin(admin);
                usuariosBloqueados.put(admin.getId(), tentativasLogin);
            }
            tentativasLogin.registrarFalha();

            if (tentativasLogin.getQuantidadeDeFalhas() >= MAX_TENTATIVAS_FALHAS) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Usuário bloqueado devido a várias tentativas de login falhas.");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Senha incorreta. Tentativas restantes: " + (MAX_TENTATIVAS_FALHAS - tentativasLogin.getQuantidadeDeFalhas()));
            }
        } else {
            // Login bem-sucedido, então remova as tentativas de login falhas
            usuariosBloqueados.remove(admin.getId());
            return ResponseEntity.ok("Login bem-sucedido." + admin.getNome());
        }
    }
}