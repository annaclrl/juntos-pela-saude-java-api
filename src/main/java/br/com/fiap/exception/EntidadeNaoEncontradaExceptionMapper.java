package br.com.fiap.exception;

public class EntidadeNaoEncontradaExceptionMapper extends RuntimeException {
  public EntidadeNaoEncontradaExceptionMapper(String message) {
    super(message);
  }
}
