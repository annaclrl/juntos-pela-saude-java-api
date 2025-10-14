package br.com.fiap.exeption;

public class EntidadeNaoEncontradaException extends Exception{

    public EntidadeNaoEncontradaException() {
    }

    public EntidadeNaoEncontradaException(String message) {
        super(message);
    }
}
