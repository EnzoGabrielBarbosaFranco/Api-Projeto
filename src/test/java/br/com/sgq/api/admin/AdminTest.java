// package br.com.sgq.api.admin;

// import br.com.sgq.api.domain.admin.Admin;
// import br.com.sgq.api.domain.admin.AdminRepository;
// import br.com.sgq.api.domain.admin.CadastroAdmin;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;

// import static org.junit.jupiter.api.Assertions.assertEquals;

// @SpringBootTest
// class AdminTest {

//     @Autowired
//     private AdminRepository adminRepository;

//     @BeforeEach
//     public void limparBancoDeDados() {
//         adminRepository.deleteAll();
//     }

//     @Test
//     @DisplayName("Deve criar um novo administrador")
//     void deve_cadastrar_um_novo_admin() {
//         // Arrange
//         CadastroAdmin cadastro = new CadastroAdmin("Joselito", "joselito@teste.com.br", "SenhaDificil123");

//         // Act
//         Admin admin = new Admin(cadastro);
//         Admin adminCadastrado = adminRepository.save(admin);

//         // Assert
//         assertEquals("Joselito", adminCadastrado.getNome());
//         assertEquals("joselito@teste.com.br", adminCadastrado.getLogin());
//         assertEquals("SenhaDificil123", adminCadastrado.getSenha());
//     }
// }