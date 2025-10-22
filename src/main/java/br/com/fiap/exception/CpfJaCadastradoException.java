package br.com.fiap.exception;

public class CpfJaCadastradoException extends RegraNegocioExeption {

    public CpfJaCadastradoException() {
        super("CPF já cadastrado!");
    }
}
