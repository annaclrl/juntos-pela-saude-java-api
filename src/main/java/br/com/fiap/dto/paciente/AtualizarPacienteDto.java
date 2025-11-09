package br.com.fiap.dto.paciente;

import jakarta.validation.constraints.*;

public class AtualizarPacienteDto {

    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres.")
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "O e-mail deve ser válido.")
    private String email;

    @NotBlank(message = "O CPF é obrigatório.")
    @Pattern(
            regexp = "\\d{11}",
            message = "O CPF deve conter exatamente 11 dígitos numéricos."
    )
    private String cpf;

    @Min(value = 1, message = "A idade mínima permitida é 1 ano.")
    @Max(value = 120, message = "A idade máxima permitida é 120 anos.")
    private int idade;

    @NotBlank(message = "O telefone principal é obrigatório.")
    @Pattern(
            regexp = "\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}",
            message = "O telefone principal deve estar em um formato válido. Ex: 11912345678"
    )
    private String telefone1;

    @Pattern(
            regexp = "^(\\(?\\d{2}\\)?\\s?)?(9\\d{4}-?\\d{4})$",
            message = "O telefone secundário deve estar em um formato válido. Ex: 11912345678"
    )
    private String telefone2;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
    @Pattern(
            regexp = "^(?!.*(\\$\\$|&)).*$",
            message = "A senha não pode conter os caracteres '$$' nem '&'."
    )
    private String senha;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
