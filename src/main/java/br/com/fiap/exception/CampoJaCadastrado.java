package br.com.fiap.exception;

public class CampoJaCadastrado extends RuntimeException {
    public CampoJaCadastrado(String campo) {
        super(campo + " já cadastrado!");
    }
}
