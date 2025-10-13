package br.com.fiap.model;

public abstract class Pessoa {

    private int codigo;
    private String nome;
    private String email;
    private String cpf;
    private int idade;
    private String telefone1;
    private String telefone2;


    public Pessoa() { }

    public Pessoa(int codigo, String nome, String email, String cpf, int idade,String telefone1, String telefone2) {
        this.codigo = codigo;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.idade = idade;
        this.telefone1 = telefone1;
        this.telefone2 = telefone2;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public int getIdade() {
        return idade;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public void setTelefone2(String telefone1) {
        this.telefone2 = telefone1;
    }

}
