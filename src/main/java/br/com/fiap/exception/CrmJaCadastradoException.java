package br.com.fiap.exception;

public class CrmJaCadastradoException extends RegraNegocioExeption {
    public CrmJaCadastradoException() {
        super("CRM já cadastrado!");
    }
}
