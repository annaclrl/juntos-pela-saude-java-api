package br.com.fiap.service;

import br.com.fiap.dao.PacienteDao;
import br.com.fiap.exception.CpfJaCadastradoException;
import br.com.fiap.exception.EmailJaCadastradoException;
import br.com.fiap.exception.EntidadeNaoEncontradaException;
import br.com.fiap.exception.TelefoneJaCadastradoException;
import br.com.fiap.model.Paciente;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.List;


@ApplicationScoped
public class PacienteService {

    @Inject
    private PacienteDao pacienteDao;

    public void cadastrarPaciente(Paciente paciente)
            throws CpfJaCadastradoException,
            EmailJaCadastradoException,
            TelefoneJaCadastradoException,
            SQLException {

        try {
            if (pacienteDao.buscarPorCpf(paciente.getCpf()) != null)
                throw new CpfJaCadastradoException();
        } catch (EntidadeNaoEncontradaException e) {
        }

        try {
            if (pacienteDao.buscarPorEmail(paciente.getEmail()) != null)
                throw new EmailJaCadastradoException();
        } catch (EntidadeNaoEncontradaException e) {
        }

        try {
            if (pacienteDao.buscarPorTelefone(paciente.getTelefone1(), paciente.getTelefone2()) != null)
                throw new TelefoneJaCadastradoException();
        } catch (EntidadeNaoEncontradaException e) {
        }

        pacienteDao.inserir(paciente);
    }


    public List<Paciente> listarPacientes() throws SQLException {
        return pacienteDao.listarTodos();
    }

    public Paciente buscarPorCodigo(int codigo) throws EntidadeNaoEncontradaException, SQLException {
        return pacienteDao.buscarPorCodigo(codigo);
    }

    public void atualizarPaciente(Paciente paciente) throws EntidadeNaoEncontradaException, SQLException {
        pacienteDao.atualizar(paciente);
    }

    public void deletarPaciente(int codigo) throws EntidadeNaoEncontradaException, SQLException {
        pacienteDao.deletar(codigo);
    }
}
