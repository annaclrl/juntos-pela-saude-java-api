package br.com.fiap.service;

import br.com.fiap.dao.*;
import br.com.fiap.exception.EntidadeNaoEncontradaException;
import br.com.fiap.exception.RegraNegocioExeption;
import br.com.fiap.model.Consulta;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
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

    @Inject
    private FeedbackConsultaDao feedbackDao;

    public void cadastrarConsulta(Consulta consulta) throws Exception {
        verificarPacienteExistente(consulta);
        verificarMedicoExistente(consulta);
        verificarFuncionarioExistente(consulta);

        List<Consulta> consultasExistentes = consultaDao.listarTodos();
        boolean conflito = consultasExistentes.stream().anyMatch(c ->
                c.getPaciente().getCodigo() == consulta.getPaciente().getCodigo() &&
                        c.getMedico().getCodigo() == consulta.getMedico().getCodigo() &&
                        mesmoFuncionario(c, consulta) &&
                        c.getDataHora().equals(consulta.getDataHora())
        );

        if (conflito) {
            throw new RegraNegocioExeption("Já existe uma consulta agendada para esse paciente, médico, funcionário e horário.");
        }

        consultaDao.inserir(consulta);
    }

    private boolean mesmoFuncionario(Consulta c1, Consulta c2) {
        if (c1.getFuncionario() == null && c2.getFuncionario() == null) return true;
        if (c1.getFuncionario() == null || c2.getFuncionario() == null) return false;
        return c1.getFuncionario().getCodigo() == c2.getFuncionario().getCodigo();
    }

    public void atualizarConsulta(Consulta consulta) throws Exception {
        verificarPacienteExistente(consulta);
        verificarMedicoExistente(consulta);
        verificarFuncionarioExistente(consulta);

        List<Consulta> consultasExistentes = consultaDao.listarTodos();
        boolean conflito = consultasExistentes.stream().anyMatch(c ->
                c.getCodigo() != consulta.getCodigo() &&
                        c.getPaciente().getCodigo() == consulta.getPaciente().getCodigo() &&
                        c.getMedico().getCodigo() == consulta.getMedico().getCodigo() &&
                        c.getFuncionario().getCodigo() == consulta.getFuncionario().getCodigo() &&
                        c.getDataHora().equals(consulta.getDataHora())
        );

        if (conflito) {
            throw new RegraNegocioExeption("Já existe uma consulta agendada para esse paciente, médico, funcionário e horário.");
        }

        consultaDao.atualizar(consulta);
    }

    public List<Consulta> listarConsultas() throws SQLException {
        return consultaDao.listarTodos();
    }

    public Consulta buscarPorCodigo(int codigo) throws EntidadeNaoEncontradaException, SQLException {
        return consultaDao.buscarPorCodigo(codigo);
    }

    public void deletarConsulta(int codigo) throws EntidadeNaoEncontradaException, SQLException {
        feedbackDao.deletarPorConsulta(codigo);
        consultaDao.deletar(codigo);
    }

    private void verificarPacienteExistente(Consulta consulta) throws EntidadeNaoEncontradaException {
        try {
            if (consulta.getPaciente() == null || pacienteDao.buscarPorCodigo(consulta.getPaciente().getCodigo()) == null) {
                throw new EntidadeNaoEncontradaException("Paciente não encontrado!");
            }
        } catch (Exception e) {
            throw new EntidadeNaoEncontradaException("Paciente não encontrado!");
        }
    }

    private void verificarMedicoExistente(Consulta consulta) throws EntidadeNaoEncontradaException {
        try {
            if (consulta.getMedico() == null || medicoDao.buscarPorCodigo(consulta.getMedico().getCodigo()) == null) {
                throw new EntidadeNaoEncontradaException("Médico não encontrado!");
            }
        } catch (Exception e) {
            throw new EntidadeNaoEncontradaException("Médico não encontrado!");
        }
    }

    private void verificarFuncionarioExistente(Consulta consulta) throws EntidadeNaoEncontradaException {
        if (consulta.getFuncionario() != null) {
            try {
                if (funcionarioDao.buscarPorCodigo(consulta.getFuncionario().getCodigo()) == null) {
                    throw new EntidadeNaoEncontradaException("Funcionário não encontrado!");
                }
            } catch (Exception e) {
                throw new EntidadeNaoEncontradaException("Funcionário não encontrado!");
            }
        }
    }
}
