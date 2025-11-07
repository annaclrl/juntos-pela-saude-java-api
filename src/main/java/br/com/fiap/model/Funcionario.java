package br.com.fiap.model;

public class Funcionario extends Pessoa {

    public Funcionario() { }

    public Funcionario(int codigo, String nome, String email, String cpf, int idade, String telefone1, String telefone2, String senha) {
        super(codigo,nome, email, cpf, idade, telefone1, telefone2, senha);

    }
}