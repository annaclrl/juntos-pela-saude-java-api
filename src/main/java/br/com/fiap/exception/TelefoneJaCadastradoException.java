package br.com.fiap.exception;

public class TelefoneJaCadastradoException extends RegraNegocioExeption {

  public TelefoneJaCadastradoException() {
    super("Telefone já cadastrado!");
  }
}
