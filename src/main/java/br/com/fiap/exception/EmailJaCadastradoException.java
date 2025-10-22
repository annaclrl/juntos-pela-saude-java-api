package br.com.fiap.exception;

public class EmailJaCadastradoException extends RegraNegocioExeption {

    public EmailJaCadastradoException() {
        super("E-mail já cadastrado!");
    }
}
