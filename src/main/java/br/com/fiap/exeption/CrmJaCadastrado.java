package br.com.fiap.exeption;

public class CrmJaCadastrado extends RuntimeException {
    public CrmJaCadastrado() {
        super("CRM já cadstrado!");
    }
}
