package br.com.fiap.dto.consulta;

import br.com.fiap.model.Funcionario;
import br.com.fiap.model.Medico;
import br.com.fiap.model.Paciente;
import br.com.fiap.model.StatusConsulta;

import java.time.LocalDateTime;

public class ListarConsultaDto {

    private int codigo;
    private Paciente paciente;
    private Medico medico;
    private Funcionario funcionario;
    private StatusConsulta status;
    private LocalDateTime dataHora;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

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
