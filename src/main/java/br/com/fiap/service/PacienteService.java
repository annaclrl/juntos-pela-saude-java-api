package br.com.fiap.service;

import br.com.fiap.dao.PacienteDao;
import br.com.fiap.exeption.EntidadeNaoEncontradaException;
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
    private ValidationService validationService;

    private void validarPaciente(Paciente paciente) throws Exception {
        if (!validationService.validarNome(paciente.getNome()))
            throw new Exception("Nome inválido");
        if (!validationService.validarCPF(paciente.getCpf()))
            throw new Exception("CPF inválido");
        if (!validationService.validarIdade(paciente.getIdade()))
            throw new Exception("Idade inválida");
        if (!validationService.validarEmail(paciente.getEmail()))
            throw new Exception("Email inválido");
        if (!validationService.validarTelefoneSecundario(paciente.getTelefone1(), paciente.getTelefone2()))
            throw new Exception("Telefone secundário igual ao principal");
    }

    public void cadastrarPaciente(Paciente paciente) throws Exception {
        validarPaciente(paciente);

        try {
            if (pacienteDao.buscarPorCpf(paciente.getCpf()) != null)
                throw new Exception("CPF já cadastrado");
        } catch (EntidadeNaoEncontradaException ignored) {
        }

        try {
            if (pacienteDao.buscarPorEmail(paciente.getEmail()) != null)
                throw new Exception("Email já cadastrado");
        } catch (EntidadeNaoEncontradaException ignored) {
        }

        try {
            if (pacienteDao.buscarPorTelefone(paciente.getTelefone1(), paciente.getTelefone2()) != null)
                throw new Exception("Telefone já cadastrado");
        } catch (EntidadeNaoEncontradaException ignored) {
        }

        pacienteDao.inserir(paciente);
    }

    public List<Paciente> listarPacientes() throws SQLException {
        return pacienteDao.listarTodos();
    }

    public Paciente buscarPorCpf(String cpf) throws Exception {
        return pacienteDao.buscarPorCpf(cpf);
    }

    public Paciente buscarPorCodigo(int codigo) throws Exception {
        return pacienteDao.buscarPorCodigo(codigo);
    }

    public Paciente buscarPorEmail(String email) throws Exception {
        return pacienteDao.buscarPorEmail(email);
    }

    public void atualizarPaciente(Paciente paciente) throws Exception {
        validarPaciente(paciente);
        pacienteDao.atualizar(paciente);
    }

    public void deletarPaciente(int codigo) throws Exception {
        pacienteDao.deletar(codigo);
    }
}
