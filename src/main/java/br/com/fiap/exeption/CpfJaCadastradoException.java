package br.com.fiap.exeption;

public class CpfJaCadastradoException extends RegraNegocioExeption {

    public CpfJaCadastradoException() {
        super("CPF já cadastrado");
    }
}
