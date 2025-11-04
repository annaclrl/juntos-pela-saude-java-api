package br.com.fiap.service;

import br.com.fiap.dao.ConsultaDao;
import br.com.fiap.dao.FeedbackConsultaDao;
import br.com.fiap.dao.FuncionarioDao;
import br.com.fiap.exception.CampoJaCadastrado;
import br.com.fiap.exception.EntidadeNaoEncontradaException;
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
    private ConsultaDao consultaDao;

    @Inject
    private FeedbackConsultaDao feedbackDao;

    public void cadastrarFuncionario(Funcionario funcionario) throws CampoJaCadastrado,SQLException{

        try {
            if (funcionarioDao.buscarPorCpf(funcionario.getCpf()) != null)
                throw new CampoJaCadastrado("CPF");
        } catch (EntidadeNaoEncontradaException e) {
        }

        try {
            if (funcionarioDao.buscarPorEmail(funcionario.getEmail()) != null)
                throw new CampoJaCadastrado("E-mail");
        } catch (EntidadeNaoEncontradaException e) {
        }

        try {
            if (funcionarioDao.buscarPorTelefone(funcionario.getTelefone1(), funcionario.getTelefone2()) != null)
                throw new CampoJaCadastrado("Telefone");
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

        var consultas = consultaDao.buscarPorFuncionario(codigo);

        for (var consulta : consultas){
            feedbackDao.deletarPorConsulta(consulta.getCodigo());
        }

        consultaDao.deletarPorFuncionario(codigo);
        funcionarioDao.deletar(codigo);
    }
}
