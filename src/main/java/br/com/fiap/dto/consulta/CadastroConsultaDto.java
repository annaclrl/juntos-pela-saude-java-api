package br.com.fiap.dto.consulta;

import br.com.fiap.model.StatusConsulta;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class CadastroConsultaDto {

    @NotNull(message = "O paciente é obrigatório.")
    @JsonProperty("paciente_id")
    private int pacienteId;

    @NotNull(message = "O médico é obrigatório.")
    @JsonProperty("medico_id")
    private int medicoId;

    @NotNull(message = "O funcionário responsável é obrigatório.")
    @JsonProperty("funcionario_id")
    private int funcionarioId;

    @NotNull(message = "O status da consulta é obrigatório.")
    private StatusConsulta status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "A data e hora da consulta são obrigatórias.")
    @Future(message = "A data e hora da consulta devem estar no futuro.")
    private LocalDateTime dataHora;


    public int getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }

    public int getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(int medicoId) {
        this.medicoId = medicoId;
    }

    public int getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(int funcionarioId) {
        this.funcionarioId = funcionarioId;
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
