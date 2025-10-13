package br.com.fiap.service;

import br.com.fiap.dao.FuncionarioDao;
import br.com.fiap.model.Funcionario;

import java.sql.SQLException;
import java.util.List;

public class FuncionarioService {

    private final FuncionarioDao funcionarioDao;
    private final ValidationService validationService;

    public FuncionarioService() throws SQLException, ClassNotFoundException {
        this.funcionarioDao = new FuncionarioDao();
        this.validationService = new ValidationService();
    }

    private boolean validarFuncionario(Funcionario funcionario) {
        if (!validationService.validarNome(funcionario.getNome())) {
            System.out.println("Nome inválido! Não deve conter números ou caracteres especiais.");
            return false;
        }

        if (!validationService.validarCPF(funcionario.getCpf())) {
            System.out.println("CPF inválido!");
            return false;
        }

        if (!validationService.validarIdade(funcionario.getIdade())) {
            System.out.println("Idade inválida! Deve estar entre 1 e 119.");
            return false;
        }

        if (!validationService.validarEmail(funcionario.getEmail())) {
            System.out.println("E-mail inválido!");
            return false;
        }

        if (!validationService.validarTelefoneSecundario(funcionario.getTelefone1(), funcionario.getTelefone2())) {
            System.out.println("O telefone secundário não pode ser igual ao telefone principal!");
            return false;
        }

        return true;
    }

    public boolean cadastrarFuncionario(Funcionario funcionario) throws SQLException {
        if (!validarFuncionario(funcionario)) return false;

        if (funcionarioDao.buscarPorCpf(funcionario.getCpf()) != null) {
            System.out.println("Já existe um funcionário com este CPF!");
            return false;
        }

        if (funcionarioDao.buscarPorEmail(funcionario.getEmail()) != null) {
            System.out.println("Já existe um funcionário com este email!");
            return false;
        }

        if (funcionarioDao.buscarPorTelefone(funcionario.getTelefone1(), funcionario.getTelefone2()) != null) {
            System.out.println("Já existe um funcionário com estes telefones!");
            return false;
        }

        return funcionarioDao.inserir(funcionario);
    }

    public boolean atualizarFuncionario(Funcionario funcionario) throws SQLException {
        if (!validarFuncionario(funcionario)) return false;
        return funcionarioDao.atualizar(funcionario);
    }

    public List<Funcionario> listarFuncionarios() throws SQLException {
        return funcionarioDao.listarTodos();
    }

    public Funcionario buscarPorCpf(String cpf) throws SQLException {
        return funcionarioDao.buscarPorCpf(cpf);
    }

    public Funcionario buscarPorCodigo(int codigo) throws SQLException {
        return funcionarioDao.buscarPorCodigo(codigo);
    }

    public boolean deletarFuncionario(int codigo) throws SQLException {
        return funcionarioDao.deletar(codigo);
    }

    public void close() throws SQLException {
        funcionarioDao.close();
    }
}
