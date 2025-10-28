package br.com.fiap.service;

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

    public boolean atualizarMedico(Medico medico) throws EntidadeNaoEncontradaException, SQLException {
        return medicoDao.atualizar(medico);
    }

    public void deletarMedico(int codigo) throws EntidadeNaoEncontradaException, SQLException {
        medicoDao.deletar(codigo);
    }
}
