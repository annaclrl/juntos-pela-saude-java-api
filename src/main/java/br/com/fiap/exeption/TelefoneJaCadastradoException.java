package br.com.fiap.exeption;

public class TelefoneJaCadastradoException extends RegraNegocioExeption {

  public TelefoneJaCadastradoException() {
    super("Telefone já cadastrado");
  }
}
