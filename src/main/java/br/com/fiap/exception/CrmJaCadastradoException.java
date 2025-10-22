package br.com.fiap.exception;

public class CrmJaCadastradoException extends RuntimeException {
    public CrmJaCadastradoException() {
        super("CRM já cadstrado!");
    }
}
