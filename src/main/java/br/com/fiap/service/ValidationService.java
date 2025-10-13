package br.com.fiap.service;

public class ValidationService {

    public boolean validarNome(String nome) {
        if (nome == null || nome.isBlank()) {
            return false;
        }
        return nome.matches("[A-Za-zÀ-ú ]+");
    }

    public boolean validarCPF(String cpf) {
        if (cpf == null) return false;
        String cpfNumeros = cpf.replaceAll("\\D", "");
        return cpfNumeros.length() == 11;
    }

    public boolean validarIdade(int idade) {
        return idade > 0 && idade < 120;
    }

    public boolean validarEmail(String email) {
        if (email == null || email.isBlank()) return false;
        return email.contains("@");
    }

    public boolean validarTelefoneSecundario(String tel1, String tel2) {
        if (tel1 == null || tel2 == null) return false;
        return !tel1.equals(tel2);
    }

}
