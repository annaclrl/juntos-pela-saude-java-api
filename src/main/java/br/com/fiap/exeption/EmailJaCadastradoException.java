package br.com.fiap.exeption;

public class EmailJaCadastradoException extends RegraNegocioExeption {

    public EmailJaCadastradoException() {
        super("E-mail já cadastrado!");
    }
}
