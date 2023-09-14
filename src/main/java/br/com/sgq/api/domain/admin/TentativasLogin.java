package br.com.sgq.api.domain.admin;

public class TentativasLogin {
    private int quantidadeDeFalhas;
    private Admin admin;

    public TentativasLogin(Admin admin) {
        this.admin = admin;
        this.quantidadeDeFalhas = 0;
    }

    public void registrarFalha() {
        this.quantidadeDeFalhas++;
    }

    public int getQuantidadeDeFalhas() {
        return quantidadeDeFalhas;
    }

    public Admin getAdmin() {
        return admin;
    }
}

