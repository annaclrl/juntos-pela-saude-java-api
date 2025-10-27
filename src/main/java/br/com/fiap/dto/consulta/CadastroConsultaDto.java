package br.com.fiap.dto.consulta;

import br.com.fiap.model.Funcionario;
import br.com.fiap.model.Medico;
import br.com.fiap.model.Paciente;
import br.com.fiap.model.StatusConsulta;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class CadastroConsultaDto {

    @NotNull(message = "O paciente é obrigatório.")
    private Paciente paciente;

    @NotNull(message = "O médico é obrigatório.")
    private Medico medico;

    @NotNull(message = "O funcionário responsável é obrigatório.")
    private Funcionario funcionario;

    @NotNull(message = "O status da consulta é obrigatório.")
    private StatusConsulta status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "A data e hora da consulta são obrigatórias.")
    @Future(message = "A data e hora da consulta devem estar no futuro.")
    private LocalDateTime dataHora;

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public StatusConsulta getStatus() {
        return status;
    }

    public void setStatus(StatusConsulta status) {
        this.status = status;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
}
