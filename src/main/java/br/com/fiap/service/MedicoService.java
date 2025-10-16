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

    @Inject
    private ValidationService validationService;

    public void cadastrarMedico(Medico medico) throws Exception {
        validarMedico(medico);

        try {
            if (medicoDao.buscarPorCpf(medico.getCpf()) != null)
                throw new Exception("CPF já cadastrado");
        } catch (EntidadeNaoEncontradaException ignored) {
        }

        try {
            if (medicoDao.buscarPorEmail(medico.getEmail()) != null)
                throw new Exception("Email já cadastrado");
        } catch (EntidadeNaoEncontradaException ignored) {
        }

        try {
            if (medicoDao.buscarPorTelefone(medico.getTelefone1(), medico.getTelefone2()) != null)
                throw new Exception("Telefone já cadastrado");
        } catch (EntidadeNaoEncontradaException ignored) {
        }

        try {
            if (medicoDao.buscarPorCrm(medico.getCrm()) != null)
                throw new Exception("CRM já cadastrado");
        } catch (EntidadeNaoEncontradaException ignored) {
        }

        medicoDao.inserir(medico);
    }

    private void validarMedico(Medico medico) throws Exception {
        if (!validationService.validarNome(medico.getNome()))
            throw new Exception("Nome inválido");
        if (!validationService.validarCPF(medico.getCpf()))
            throw new Exception("CPF inválido");
        if (!validationService.validarIdade(medico.getIdade()))
            throw new Exception("Idade inválida");
        if (!validationService.validarEmail(medico.getEmail()))
            throw new Exception("Email inválido");
        if (!validationService.validarTelefoneSecundario(medico.getTelefone1(), medico.getTelefone2()))
            throw new Exception("Telefone secundário igual ao principal");
        if (medico.getCrm() == null || !medico.getCrm().matches("\\d{6}"))
            throw new Exception("CRM inválido! Deve conter exatamente 6 dígitos numéricos.");
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
        validarMedico(medico);
        return medicoDao.atualizar(medico);
    }

    public void deletarMedico(int codigo) throws Exception {
        medicoDao.deletar(codigo);
    }
}
