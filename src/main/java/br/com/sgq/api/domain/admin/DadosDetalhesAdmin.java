package br.com.sgq.api.domain.admin;

public record DadosDetalhesAdmin(
        long id,
        String nome,
        String login,
        String senha) {

    public DadosDetalhesAdmin(Admin admin) {
        this(admin.getId(), admin.getNome(), admin.getLogin(), admin.getSenha());
    }
}