package br.com.fiap.service;

import br.com.fiap.dao.ConsultaDao;
import br.com.fiap.dao.FeedbackConsultaDao;
import br.com.fiap.dao.PacienteDao;
import br.com.fiap.exception.CampoJaCadastrado;
import br.com.fiap.exception.EntidadeNaoEncontradaException;
import br.com.fiap.model.Paciente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.List;


@ApplicationScoped
public class PacienteService {

    @Inject
    private PacienteDao pacienteDao;

    @Inject
    private ConsultaDao consultaDao;

    @Inject
    private FeedbackConsultaDao feedbackDao;

    public void cadastrarPaciente(Paciente paciente)  throws CampoJaCadastrado, SQLException {

        try {
            if (pacienteDao.buscarPorCpf(paciente.getCpf()) != null)
                throw new CampoJaCadastrado("CPF");
        } catch (EntidadeNaoEncontradaException e) {
        }

        try {
            if (pacienteDao.buscarPorEmail(paciente.getEmail()) != null)
                throw new CampoJaCadastrado("E-mail");
        } catch (EntidadeNaoEncontradaException e) {
        }

        try {
            if (pacienteDao.buscarPorTelefone(paciente.getTelefone1(), paciente.getTelefone2()) != null)
                throw new CampoJaCadastrado("Telefone");
        } catch (EntidadeNaoEncontradaException e) {
        }

        pacienteDao.inserir(paciente);
    }


    public List<Paciente> listarPacientes() throws SQLException {
        return pacienteDao.listarTodos();
    }

    public Paciente buscarPorCodigo(int codigo) throws EntidadeNaoEncontradaException, SQLException {
        return pacienteDao.buscarPorCodigo(codigo);
    }

    public void atualizarPaciente(Paciente paciente) throws EntidadeNaoEncontradaException, SQLException {
        pacienteDao.atualizar(paciente);
    }

    public void deletarPaciente(int codigo) throws SQLException, EntidadeNaoEncontradaException {

        var consultas = consultaDao.buscarPorPaciente(codigo);

        for (var consulta : consultas) {
            feedbackDao.deletarPorConsulta(consulta.getCodigo());
        }

        consultaDao.deletarPorPaciente(codigo);
        pacienteDao.deletar(codigo);
    }
}
