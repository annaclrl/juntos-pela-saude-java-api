package br.com.fiap.model;

public class Medico extends Pessoa {

    private String especialidade;
    private String crm;

    public Medico(int codigo, String nome, String email, String cpf, int idade, String telefone1,String telefone2, String crm, String especialidade) {
        super(codigo, nome, email, cpf, idade, telefone1, telefone2);
        this.crm = crm;
        this.especialidade = especialidade;
    }

    public Medico() {

    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }


    @Override
    public String toString() {
        return "\nCódigo: " + getCodigo() +
                "\nNome: " + getNome() +
                "\nEmail: " + getEmail() +
                "\nCPF: " + getEmail() +
                "\nIdade: " + getIdade() +
                "\nEmail: " + getEmail() +
                "\nPrimeiro telefone: " + getTelefone1() +
                "\nSegundo telefone: " + getTelefone2() +
                "\nEspecialidade: " + especialidade +
                "\nCRM: " + crm;
    }

}
