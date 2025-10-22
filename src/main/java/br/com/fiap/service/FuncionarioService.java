package br.com.fiap.service;

import br.com.fiap.dao.FuncionarioDao;
import br.com.fiap.exeption.CpfJaCadastradoException;
import br.com.fiap.exeption.EmailJaCadastradoException;
import br.com.fiap.exeption.EntidadeNaoEncontradaException;
import br.com.fiap.exeption.TelefoneJaCadastradoException;
import br.com.fiap.model.Funcionario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class FuncionarioService {

    @Inject
    private FuncionarioDao funcionarioDao;

    public void cadastrarFuncionario(Funcionario funcionario) throws Exception {
        try {
            if (funcionarioDao.buscarPorCpf(funcionario.getCpf()) != null)
                throw new CpfJaCadastradoException();
        } catch (EntidadeNaoEncontradaException ignored) {
        }

        try {
            if (funcionarioDao.buscarPorEmail(funcionario.getEmail()) != null)
                throw new EmailJaCadastradoException();
        } catch (EntidadeNaoEncontradaException ignored) {
        }

        try {
            if (funcionarioDao.buscarPorTelefone(funcionario.getTelefone1(), funcionario.getTelefone2()) != null)
                throw new TelefoneJaCadastradoException();
        } catch (EntidadeNaoEncontradaException ignored) {
        }

        funcionarioDao.inserir(funcionario);
    }

    public void atualizarFuncionario(Funcionario funcionario) throws Exception {
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
