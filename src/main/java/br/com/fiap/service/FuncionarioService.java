package br.com.fiap.service;

import br.com.fiap.dao.FuncionarioDao;
import br.com.fiap.exception.CpfJaCadastradoException;
import br.com.fiap.exception.EmailJaCadastradoException;
import br.com.fiap.exception.EntidadeNaoEncontradaException;
import br.com.fiap.exception.TelefoneJaCadastradoException;
import br.com.fiap.model.Funcionario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class FuncionarioService {

    @Inject
    private FuncionarioDao funcionarioDao;

    public void cadastrarFuncionario(Funcionario funcionario)
            throws CpfJaCadastradoException,
                    EmailJaCadastradoException,
                    TelefoneJaCadastradoException,
                    SQLException{

        try {
            if (funcionarioDao.buscarPorCpf(funcionario.getCpf()) != null)
                throw new CpfJaCadastradoException();
        } catch (EntidadeNaoEncontradaException e) {
        }

        try {
            if (funcionarioDao.buscarPorEmail(funcionario.getEmail()) != null)
                throw new EmailJaCadastradoException();
        } catch (EntidadeNaoEncontradaException e) {
        }

        try {
            if (funcionarioDao.buscarPorTelefone(funcionario.getTelefone1(), funcionario.getTelefone2()) != null)
                throw new TelefoneJaCadastradoException();
        } catch (EntidadeNaoEncontradaException e) {
        }

        funcionarioDao.inserir(funcionario);
    }

    public void atualizarFuncionario(Funcionario funcionario) throws EntidadeNaoEncontradaException, SQLException {
        funcionarioDao.atualizar(funcionario);
    }

    public List<Funcionario> listarFuncionarios() throws SQLException {
        return funcionarioDao.listarTodos();
    }

    public Funcionario buscarPorCodigo(int codigo) throws EntidadeNaoEncontradaException, SQLException {
        return funcionarioDao.buscarPorCodigo(codigo);
    }

    public void deletarFuncionario(int codigo) throws EntidadeNaoEncontradaException, SQLException {
        funcionarioDao.deletar(codigo);
    }
}
