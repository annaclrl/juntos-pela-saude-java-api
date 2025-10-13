package br.com.fiap.service;

import br.com.fiap.dao.PacienteDao;
import br.com.fiap.model.Paciente;

import java.sql.SQLException;
import java.util.List;

public class PacienteService {

    private final PacienteDao pacienteDao;
    private final ValidationService validationService;

    public PacienteService() throws SQLException, ClassNotFoundException {
        this.pacienteDao = new PacienteDao();
        this.validationService = new ValidationService();
    }

    private boolean validarPaciente(Paciente paciente) {
        if (!validationService.validarNome(paciente.getNome())) {
            System.out.println("Nome inválido! Não deve conter números ou caracteres especiais.");
            return false;
        }

        if (!validationService.validarCPF(paciente.getCpf())) {
            System.out.println("CPF inválido!");
            return false;
        }

        if (!validationService.validarIdade(paciente.getIdade())) {
            System.out.println("Idade inválida! Deve estar entre 1 e 119.");
            return false;
        }

        if (!validationService.validarEmail(paciente.getEmail())) {
            System.out.println("E-mail inválido!");
            return false;
        }

        if (!validationService.validarTelefoneSecundario(paciente.getTelefone1(), paciente.getTelefone2())) {
            System.out.println("O telefone secundário não pode ser igual ao telefone principal!");
            return false;
        }

        return true;
    }

    public boolean cadastrarPaciente(Paciente paciente) throws SQLException {
        if (!validarPaciente(paciente)) return false;

        if (pacienteDao.buscarPorCpf(paciente.getCpf()) != null) {
            System.out.println("Já existe um paciente com este CPF!");
            return false;
        }

        if (pacienteDao.buscarPorEmail(paciente.getEmail()) != null) {
            System.out.println("Já existe um paciente com este email!");
            return false;
        }

        if (pacienteDao.buscarPorTelefone(paciente.getTelefone1(), paciente.getTelefone2()) != null) {
            System.out.println("Já existe um paciente com estes telefones!");
            return false;
        }

        return pacienteDao.inserir(paciente);
    }

    public List<Paciente> listarPacientes() throws SQLException {
        return pacienteDao.listarTodos();
    }

    public Paciente buscarPorCpf(String cpf) throws SQLException {
        return pacienteDao.buscarPorCpf(cpf);
    }

    public Paciente buscarPorCodigo(int codigo) throws SQLException {
        return pacienteDao.buscarPorCodigo(codigo);
    }

    public boolean atualizarPaciente(Paciente paciente) throws SQLException {
        if (!validarPaciente(paciente)) return false;
        return pacienteDao.atualizar(paciente);
    }

    public boolean deletarPaciente(int codigo) throws SQLException {
        return pacienteDao.deletar(codigo);
    }

    public void close() throws SQLException {
        pacienteDao.close();
    }
}
