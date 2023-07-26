package br.com.sgq.api.domain.admin;

public record DadosListagemAdmin(
        long id,
        String nome,
        String login,
        String senha) {

    public DadosListagemAdmin(Admin admin) {
        this(admin.getId(), admin.getNome(), admin.getLogin(), admin.getSenha());
    }
}