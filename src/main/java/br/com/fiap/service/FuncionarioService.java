package br.com.fiap.service;

import br.com.fiap.dao.FuncionarioDao;
import br.com.fiap.exeption.EntidadeNaoEncontradaException;
import br.com.fiap.model.Funcionario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class FuncionarioService {

    @Inject
    private FuncionarioDao funcionarioDao;

    @Inject
    private ValidationService validationService;

    private void validarFuncionario(Funcionario funcionario) throws Exception {
        if (!validationService.validarNome(funcionario.getNome()))
            throw new Exception("Nome inválido! Não deve conter números ou caracteres especiais.");
        if (!validationService.validarCPF(funcionario.getCpf()))
            throw new Exception("CPF inválido!");
        if (!validationService.validarIdade(funcionario.getIdade()))
            throw new Exception("Idade inválida! Deve estar entre 1 e 119.");
        if (!validationService.validarEmail(funcionario.getEmail()))
            throw new Exception("E-mail inválido!");
        if (!validationService.validarTelefoneSecundario(funcionario.getTelefone1(), funcionario.getTelefone2()))
            throw new Exception("O telefone secundário não pode ser igual ao telefone principal!");
    }

    public void cadastrarFuncionario(Funcionario funcionario) throws Exception {
        validarFuncionario(funcionario);

        try {
            if (funcionarioDao.buscarPorCpf(funcionario.getCpf()) != null)
                throw new Exception("Já existe um funcionário com este CPF!");
        } catch (EntidadeNaoEncontradaException ignored) {
        }

        try {
            if (funcionarioDao.buscarPorEmail(funcionario.getEmail()) != null)
                throw new Exception("Já existe um funcionário com este email!");
        } catch (EntidadeNaoEncontradaException ignored) {
        }

        try {
            if (funcionarioDao.buscarPorTelefone(funcionario.getTelefone1(), funcionario.getTelefone2()) != null)
                throw new Exception("Telefone já cadastrado");
        } catch (EntidadeNaoEncontradaException ignored) {
        }

        funcionarioDao.inserir(funcionario);
    }

    public void atualizarFuncionario(Funcionario funcionario) throws Exception {
        validarFuncionario(funcionario);
        funcionarioDao.atualizar(funcionario);
    }

    public List<Funcionario> listarFuncionarios() throws SQLException {
        return funcionarioDao.listarTodos();
    }

    public Funcionario buscarPorCpf(String cpf) throws Exception {
        return funcionarioDao.buscarPorCpf(cpf);
    }

    public Funcionario buscarPorCodigo(int codigo) throws Exception {
        return funcionarioDao.buscarPorCodigo(codigo);
    }

    public Funcionario buscarPorEmail(String email) throws Exception {
        return funcionarioDao.buscarPorEmail(email);
    }

    public void deletarFuncionario(int codigo) throws Exception {
        funcionarioDao.deletar(codigo);
    }
}
