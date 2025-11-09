package br.com.fiap.dto.consulta;


import br.com.fiap.model.StatusConsulta;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class ListarConsultaDto {

    private int codigo;

    @JsonProperty("paciente_id")
    private Integer pacienteId;

    @JsonProperty("medico_id")
    private Integer medicoId;

    @JsonProperty("funcionario_id")
    private Integer funcionarioId;

    private StatusConsulta status;

    private LocalDateTime dataHora;

    private String nomeMedico;

    private String especialidadeMedico;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

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

    public String getNomeMedico() {
        return nomeMedico;
    }

    public void setNomeMedico(String nomeMedico) {
        this.nomeMedico = nomeMedico;
    }

    public String getEspecialidadeMedico() {
        return especialidadeMedico;
    }

    public void setEspecialidadeMedico(String especialidadeMedico) {
        this.especialidadeMedico = especialidadeMedico;
    }
}
