package br.com.fiap.model;

public class Medico extends Pessoa {

    private String especialidade;
    private String crm;

    public Medico(int codigo, String nome, String email, String cpf, int idade, String telefone1,String telefone2, String crm, String especialidade, String senha) {
        super(codigo, nome, email, cpf, idade, telefone1, telefone2, senha);
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


}
