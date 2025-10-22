package br.com.fiap.service;

import br.com.fiap.dao.ConsultaDao;
import br.com.fiap.dao.FuncionarioDao;
import br.com.fiap.dao.MedicoDao;
import br.com.fiap.dao.PacienteDao;
import br.com.fiap.model.Consulta;
import br.com.fiap.model.StatusConsulta;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class ConsultaService {

    @Inject
    private ConsultaDao consultaDao;

    @Inject
    private PacienteDao pacienteDao;

    @Inject
    private MedicoDao medicoDao;

    @Inject
    private FuncionarioDao funcionarioDao;

    private void validarConsulta(Consulta consulta) throws Exception {
        if (consulta.getDataHora() == null || consulta.getDataHora().isBefore(LocalDateTime.now())) {
            throw new Exception("Data/hora inválida! Deve ser futura.");
        }

        if (consulta.getPaciente() == null || pacienteDao.buscarPorCodigo(consulta.getPaciente().getCodigo()) == null) {
            throw new Exception("Paciente não encontrado!");
        }

        if (consulta.getMedico() == null || medicoDao.buscarPorCodigo(consulta.getMedico().getCodigo()) == null) {
            throw new Exception("Médico não encontrado!");
        }

        if (consulta.getFuncionario() != null && funcionarioDao.buscarPorCodigo(consulta.getFuncionario().getCodigo()) == null) {
            throw new Exception("Funcionário não encontrado!");
        }
    }

    public void cadastrarConsulta(Consulta consulta) throws Exception {
        validarConsulta(consulta);
        consulta.setStatus(StatusConsulta.CONFIRMADA);
        consultaDao.inserir(consulta);
    }

    public void atualizarConsulta(Consulta consulta) throws Exception {
        validarConsulta(consulta);
        consultaDao.atualizar(consulta);
    }

    public List<Consulta> listarConsultas() throws SQLException {
        return consultaDao.listarTodos();
    }

    public List<Consulta> listarPorPaciente(int pacienteCodigo) throws SQLException {
        return consultaDao.listarPorPaciente(pacienteCodigo);
    }

    public List<Consulta> listarPorMedico(int medicoCodigo) throws SQLException {
        return consultaDao.listarPorMedico(medicoCodigo);
    }

    public Consulta buscarPorCodigo(int codigo) throws Exception {
        return consultaDao.buscarPorCodigo(codigo);
    }

    public void deletarConsulta(int codigo) throws Exception {
        consultaDao.deletar(codigo);
    }
}
