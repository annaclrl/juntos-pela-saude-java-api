package br.com.fiap.service;

import br.com.fiap.dao.ConsultaDao;
import br.com.fiap.dao.FeedbackConsultaDao;
import br.com.fiap.dao.MedicoDao;
import br.com.fiap.exception.*;
import br.com.fiap.model.Medico;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class MedicoService {

    @Inject
    private MedicoDao medicoDao;

    @Inject
    private ConsultaDao consultaDao;

    @Inject
    private FeedbackConsultaDao feedbackDao;


    public void cadastrarMedico(Medico medico)  throws CampoJaCadastrado, SQLException {

        try {
            if (medicoDao.buscarPorCpf(medico.getCpf()) != null)
                throw new CampoJaCadastrado("CPF");
        } catch (EntidadeNaoEncontradaException e) {
        }

        try {
            if (medicoDao.buscarPorEmail(medico.getEmail()) != null)
                throw new CampoJaCadastrado("E-mail");
        } catch (EntidadeNaoEncontradaException e) {
        }

        try {
            if (medicoDao.buscarPorTelefone(medico.getTelefone1(), medico.getTelefone2()) != null)
                throw new CampoJaCadastrado("Telefone");
        } catch (EntidadeNaoEncontradaException e) {
        }

        try {
            if (medicoDao.buscarPorCrm(medico.getCrm()) != null)
                throw new CampoJaCadastrado("CRM");
        } catch (EntidadeNaoEncontradaException e) {
        }

        medicoDao.inserir(medico);
    }

    public List<Medico> listarMedicos() throws SQLException {
        return medicoDao.listarTodos();
    }

    public Medico buscarPorCodigo(int codigo) throws EntidadeNaoEncontradaException, SQLException {
        return medicoDao.buscarPorCodigo(codigo);
    }

    public List<Medico> buscarPorEspecialidade(String especialidade) throws SQLException {
        if (especialidade == null || especialidade.isBlank()) {
            throw new SQLException("Especialidade inválida ou não informada");
        }

        try {
            List<Medico> medicos = medicoDao.buscarPorEspecialidade(especialidade);

            // Evita retornar null e gerar erro no mapper
            if (medicos == null) {
                return List.of();
            }

            return medicos;
        } catch (SQLException e) {
            System.err.println("Erro ao buscar médicos por especialidade: " + e.getMessage());
            throw e; // lança de novo, mas com log
        } catch (Exception e) {
            System.err.println("Erro inesperado ao buscar médicos: " + e.getMessage());
            return List.of(); // retorna vazio para não quebrar o front
        }
    }



    public boolean atualizarMedico(Medico medico) throws EntidadeNaoEncontradaException, SQLException {
        return medicoDao.atualizar(medico);
    }

    public void deletarMedico(int codigo) throws  SQLException, EntidadeNaoEncontradaException {

        var consultas = consultaDao.buscarPorMedico(codigo);

        for (var consulta : consultas) {
            feedbackDao.deletarPorConsulta(consulta.getCodigo());
        }

        consultaDao.deletarPorMedico(codigo);
        medicoDao.deletar(codigo);
    }
}
