package br.com.fiap.service;

import br.com.fiap.dao.ConsultaDao;
import br.com.fiap.dao.FuncionarioDao;
import br.com.fiap.dao.MedicoDao;
import br.com.fiap.dao.PacienteDao;
import br.com.fiap.model.Consulta;
import br.com.fiap.model.StatusConsulta;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class ConsultaService {

    private final ConsultaDao consultaDao;
    private final PacienteDao pacienteDao;
    private final MedicoDao medicoDao;
    private final FuncionarioDao funcionarioDao;

    public ConsultaService() throws SQLException, ClassNotFoundException {
        this.consultaDao = new ConsultaDao();
        this.pacienteDao = new PacienteDao();
        this.medicoDao = new MedicoDao();
        this.funcionarioDao = new FuncionarioDao();
    }

    private boolean validarConsulta(Consulta consulta) throws SQLException {
        if (consulta.getDataHora() == null || consulta.getDataHora().isBefore(LocalDateTime.now())) {
            System.out.println("Data/hora inválida! Deve ser futura.");
            return false;
        }

        if (consulta.getPaciente() == null || pacienteDao.buscarPorCodigo(consulta.getPaciente().getCodigo()) == null) {
            System.out.println("Paciente não encontrado!");
            return false;
        }

        if (consulta.getMedico() == null || medicoDao.buscarPorCodigo(consulta.getMedico().getCodigo()) == null) {
            System.out.println("Médico não encontrado!");
            return false;
        }

        if (consulta.getFuncionario() != null && funcionarioDao.buscarPorCodigo(consulta.getFuncionario().getCodigo()) == null) {
            System.out.println("Funcionário não encontrado!");
            return false;
        }

        return true;
    }

    public boolean cadastrarConsulta(Consulta consulta) throws SQLException {
        if (!validarConsulta(consulta)) return false;

        consulta.setStatus(StatusConsulta.CONFIRMADA);
        return consultaDao.inserir(consulta);
    }

    public boolean atualizarConsulta(Consulta consulta) throws SQLException {
        if (!validarConsulta(consulta)) return false;
        return consultaDao.atualizar(consulta);
    }

    public List<Consulta> listarConsultas() throws SQLException {
        return consultaDao.listarTodos();
    }

    public List<Consulta> listarConsultasPorPaciente(int pacienteCodigo) throws SQLException {
        return consultaDao.listarPorPaciente(pacienteCodigo);
    }

    public List<Consulta> listarConsultasPorMedico(int medicoCodigo) throws SQLException {
        return consultaDao.listarPorMedico(medicoCodigo);
    }

    public Consulta buscarConsultaPorCodigo(int codigo) throws SQLException {
        return consultaDao.buscarPorCodigo(codigo);
    }

    public boolean deletarConsulta(int codigo) throws SQLException {
        return consultaDao.deletar(codigo);
    }

    public void close() throws SQLException {
        consultaDao.close();
        pacienteDao.close();
        medicoDao.close();
        funcionarioDao.close();
    }
}
