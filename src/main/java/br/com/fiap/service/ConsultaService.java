package br.com.fiap.service;

import br.com.fiap.dao.ConsultaDao;
import br.com.fiap.dao.FuncionarioDao;
import br.com.fiap.dao.MedicoDao;
import br.com.fiap.dao.PacienteDao;
import br.com.fiap.exception.EntidadeNaoEncontradaException;
import br.com.fiap.exception.RegraNegocioExeption;
import br.com.fiap.model.Consulta;
import br.com.fiap.model.StatusConsulta;
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

    public void cadastrarConsulta(Consulta consulta) throws Exception {

        try {
            if (consulta.getPaciente() == null || pacienteDao.buscarPorCodigo(consulta.getPaciente().getCodigo()) == null) {
                throw new EntidadeNaoEncontradaException("Paciente não encontrado!");
            }
        } catch (Exception e) {
            throw new EntidadeNaoEncontradaException("Paciente não encontrado!");
        }

        try {
            if (consulta.getMedico() == null || medicoDao.buscarPorCodigo(consulta.getMedico().getCodigo()) == null) {
                throw new EntidadeNaoEncontradaException("Médico não encontrado!");
            }
        } catch (Exception e) {
            throw new EntidadeNaoEncontradaException("Médico não encontrado!");
        }

        if (consulta.getFuncionario() != null) {
            try {
                if (funcionarioDao.buscarPorCodigo(consulta.getFuncionario().getCodigo()) == null) {
                    throw new EntidadeNaoEncontradaException("Funcionário não encontrado!");
                }
            } catch (Exception e) {
                throw new EntidadeNaoEncontradaException("Funcionário não encontrado!");
            }
        }

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
        consultaDao.inserir(consulta);
    }

    public void atualizarConsulta(Consulta consulta) throws Exception {
        cadastrarConsulta(consulta);
        consultaDao.atualizar(consulta);
    }

    public List<Consulta> listarConsultas() throws SQLException {
        return consultaDao.listarTodos();
    }

    public Consulta buscarPorCodigo(int codigo) throws EntidadeNaoEncontradaException, SQLException {
        return consultaDao.buscarPorCodigo(codigo);
    }

    public void deletarConsulta(int codigo) throws EntidadeNaoEncontradaException, SQLException {
        consultaDao.deletar(codigo);
    }
}
