package br.com.fiap.dto.consulta;


import br.com.fiap.model.StatusConsulta;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class AtualizarConsultaDto {

    @NotNull(message = "O paciente é obrigatório.")
    @JsonProperty("paciente_id")
    private Integer pacienteId;

    @NotNull(message = "O médico é obrigatório.")
    @JsonProperty("medico_id")
    private Integer medicoId;

    @JsonProperty("funcionario_id")
    private Integer funcionarioId;

    @NotNull(message = "O status da consulta é obrigatório.")
    private StatusConsulta status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "A data e hora da consulta são obrigatórias.")
    @Future(message = "A data e hora da consulta devem estar no futuro.")
    private LocalDateTime dataHora;

    public Integer getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Integer pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Integer getMedicoId() {
        return medicoId;
    }

    public void setMedicoId(Integer medicoId) {
        this.medicoId = medicoId;
    }

    public Integer getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Integer funcionarioId) {
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
