package br.com.fiap.model;

public class Funcionario extends Pessoa {

    private int codigo;

    public Funcionario() { }

    public Funcionario(int codigo, String nome, String email, String cpf, int idade, String telefone1, String telefone2) {
        super(codigo,nome, email, cpf, idade, telefone1, telefone2);
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "\nCódigo: " + codigo +
                "\nNome: " + getNome() +
                "\nCPF: " + getCpf() +
                "\nIdade: " + getIdade() +
                "\nEmail: " + getEmail() +
                "\nPrimeiro telefone: " + getTelefone1() +
                "\nSegundo telefone: " + getTelefone2();
    }
}