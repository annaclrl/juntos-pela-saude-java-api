package br.com.fiap.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Consulta {

    private int codigo;
    private Paciente paciente;
    private Medico medico;
    private Funcionario funcionario;
    private StatusConsulta status;
    private LocalDateTime dataHora;

    public Consulta() { }

    public Consulta(int codigo, Paciente paciente, Medico medico, Funcionario funcionario, LocalDateTime dataHora, StatusConsulta status) {
        this.codigo = codigo;
        this.paciente = paciente;
        this.medico = medico;
        this.funcionario = funcionario;
        this.dataHora = dataHora;
        this.status = status;
    }


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


    public String getDataHoraFormatada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dataHora.format(formatter);
    }
    @Override
    public String toString() {
        return "\nCódigo: " + codigo +
                "\nPaciente: " + (paciente != null ? "ID " + paciente.getCodigo() : "N/A") +
                "\nMédico: " + (medico != null ? "ID " + medico.getCodigo() : "N/A") +
                "\nStatus: " + status +
                "\nData e Hora: " + (dataHora != null ? getDataHoraFormatada() : "N/A");
    }

}

