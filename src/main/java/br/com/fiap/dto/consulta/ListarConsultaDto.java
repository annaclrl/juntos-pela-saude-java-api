package br.com.fiap.dto.consulta;

import br.com.fiap.model.Funcionario;
import br.com.fiap.model.Medico;
import br.com.fiap.model.Paciente;
import br.com.fiap.model.StatusConsulta;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class ListarConsultaDto {

    private int codigo;

    @JsonProperty("paciente_id")
    private int pacienteId;

    @JsonProperty("medico_id")
    private int medicoId;

    @JsonProperty("funcionario_id")
    private int funcionarioId;

    private StatusConsulta status;

    private LocalDateTime dataHora;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

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
