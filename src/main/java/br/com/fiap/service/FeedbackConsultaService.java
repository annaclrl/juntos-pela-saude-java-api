package br.com.fiap.service;

import br.com.fiap.dao.ConsultaDao;
import br.com.fiap.dao.FeedbackConsultaDao;
import br.com.fiap.exeption.EntidadeNaoEncontradaException;
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

    private void validarFeedback(FeedbackConsulta feedback) throws Exception {
        if (!feedback.notaValida())
            throw new Exception("Nota inválida! Deve ser entre 0 e 5.");

        if (!feedback.comentarioValido())
            throw new Exception("Comentário inválido! Máx. 100 caracteres.");

        if (feedback.getConsulta() == null || feedback.getConsulta().getCodigo() <= 0)
            throw new Exception("Consulta inválida!");

        try {
            if (consultaDao.buscarPorCodigo(feedback.getConsulta().getCodigo()) == null)
                throw new Exception("Consulta não encontrada!");
        } catch (EntidadeNaoEncontradaException ignored) {
            throw new Exception("Consulta não encontrada!");
        }

        try {
            if (!feedbackDao.listarPorConsulta(feedback.getConsulta().getCodigo()).isEmpty())
                throw new Exception("Você já deu um feedback para esta consulta!");
        } catch (SQLException ignored) {
        }
    }

    public void cadastrarFeedback(FeedbackConsulta feedback) throws Exception {
        validarFeedback(feedback);
        feedbackDao.inserir(feedback);
    }

    public void atualizarFeedback(FeedbackConsulta feedback) throws Exception {
        if (!feedback.notaValida() || !feedback.comentarioValido())
            throw new Exception("Dados inválidos! Verifique nota e comentário.");
        feedbackDao.atualizar(feedback);
    }

    public FeedbackConsulta buscarPorCodigo(int codigo) throws Exception {
        return feedbackDao.buscarPorCodigo(codigo);
    }

    public List<FeedbackConsulta> listarTodos() throws SQLException {
        return feedbackDao.listarTodos();
    }

    public List<FeedbackConsulta> listarPorConsulta(int consultaId) throws SQLException {
        return feedbackDao.listarPorConsulta(consultaId);
    }

    public void deletarFeedback(int codigo) throws Exception {
        feedbackDao.deletar(codigo);
    }
}
