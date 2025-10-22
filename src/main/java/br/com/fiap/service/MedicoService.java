package br.com.fiap.service;

import br.com.fiap.dao.MedicoDao;
import br.com.fiap.exeption.EntidadeNaoEncontradaException;
import br.com.fiap.model.Medico;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class MedicoService {

    @Inject
    private MedicoDao medicoDao;

    public void cadastrarMedico(Medico medico) throws Exception {
        try {
            if (medicoDao.buscarPorCpf(medico.getCpf()) != null)
                throw new CpfJaCadastradoException();
        } catch (EntidadeNaoEncontradaException ignored) {
        }

        try {
            if (medicoDao.buscarPorEmail(medico.getEmail()) != null)
                throw new EmailJaCadastradoException();
        } catch (EntidadeNaoEncontradaException ignored) {
        }

        try {
            if (medicoDao.buscarPorTelefone(medico.getTelefone1(), medico.getTelefone2()) != null)
                throw new TelefoneJaCadastradoException();
        } catch (EntidadeNaoEncontradaException ignored) {
        }

        try {
            if (medicoDao.buscarPorCrm(medico.getCrm()) != null)
                throw new Exception("CRM já cadastrado");
        } catch (EntidadeNaoEncontradaException ignored) {
        }

        medicoDao.inserir(medico);
    }

    public List<Medico> listarMedicos() throws SQLException {
        return medicoDao.listarTodos();
    }

    public Medico buscarPorCrm(String crm) throws Exception {
        return medicoDao.buscarPorCrm(crm);
    }

    public Medico buscarPorCodigo(int codigo) throws Exception {
        return medicoDao.buscarPorCodigo(codigo);
    }

    public Medico buscarPorCpf(String cpf) throws Exception {
        return medicoDao.buscarPorCpf(cpf);
    }

    public Medico buscarPorEmail(String email) throws Exception {
        return medicoDao.buscarPorEmail(email);
    }

    public boolean atualizarMedico(Medico medico) throws Exception {
        return medicoDao.atualizar(medico);
    }

    public void deletarMedico(int codigo) throws Exception {
        medicoDao.deletar(codigo);
    }
}
