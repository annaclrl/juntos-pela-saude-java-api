package br.com.fiap.service;

import br.com.fiap.dao.ConsultaDao;
import br.com.fiap.dao.FeedbackConsultaDao;
import br.com.fiap.exception.EntidadeNaoEncontradaException;
import br.com.fiap.exception.FeedbackJaExisteException;
import br.com.fiap.model.FeedbackConsulta;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.sql.SQLException;
import java.util.List;

@ApplicationScoped
public class FeedbackConsultaService {

    @Inject
    private FeedbackConsultaDao feedbackDao;

    @Inject
    private ConsultaDao consultaDao;


    public void cadastrarFeedback(FeedbackConsulta feedback) throws Exception {
        if (feedback == null)
            throw new Exception("Feedback não pode ser nulo.");

        if (feedback.getConsulta() == null || feedback.getConsulta().getCodigo() <= 0)
            throw new Exception("Consulta inválida!");

        if (!feedback.notaValida())
            throw new Exception("Nota inválida! Deve ser entre 0 e 5.");

        if (!feedback.comentarioValido())
            throw new Exception("Comentário inválido! Máx. 100 caracteres.");

        try {
            consultaDao.buscarPorCodigo(feedback.getConsulta().getCodigo());
        } catch (EntidadeNaoEncontradaException e) {
            throw new EntidadeNaoEncontradaException("Consulta não encontrada!");
        }

        try {
            if (!feedbackDao.listarPorConsulta(feedback.getConsulta().getCodigo()).isEmpty())
                throw new FeedbackJaExisteException((feedback.getConsulta().getCodigo()));
        } catch (SQLException ignored) {}

        feedbackDao.inserir(feedback);
    }


    public void atualizarFeedback(FeedbackConsulta feedback) throws Exception {

        if (!feedback.notaValida() || !feedback.comentarioValido())
            throw new Exception("Dados inválidos! Verifique nota e comentário.");

        feedbackDao.atualizar(feedback);
    }

    public List<FeedbackConsulta> listarTodos() throws SQLException {
        return feedbackDao.listarTodos();
    }

    public FeedbackConsulta buscarPorCodigo(int codigo) throws EntidadeNaoEncontradaException, SQLException {
        return feedbackDao.buscarPorCodigo(codigo);
    }

    public void deletarFeedback(int codigo) throws EntidadeNaoEncontradaException, SQLException {
        feedbackDao.deletar(codigo);

    }
}
