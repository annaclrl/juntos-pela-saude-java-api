package br.com.fiap.model;

public class Paciente extends Pessoa {


    public Paciente() { }

    public Paciente(int codigo, String nome, String email, String cpf, int idade, String telefone1, String telefone2) {
        super(codigo, nome, email, cpf, idade, telefone1, telefone2);
    }


    @Override
    public String toString() {
        return "\nCódigo: " + getCodigo() +
                "\nNome: " + getNome() +
                "\nCPF: " + getCpf() +
                "\nIdade: " + getIdade() +
                "\nEmail: " + getEmail() +
                "\nPrimeiro telefone: " + getTelefone1() +
                "\nSegundo telefone: " + getTelefone2();
    }
}

