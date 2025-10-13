package br.com.fiap.service;

import br.com.fiap.dao.ConsultaDao;
import br.com.fiap.dao.FeedbackConsultaDao;
import br.com.fiap.model.FeedbackConsulta;

import java.sql.SQLException;
import java.util.List;

public class FeedbackConsultaService implements AutoCloseable {

    private final FeedbackConsultaDao feedbackDao;
    private final ConsultaDao consultaDao;

    public FeedbackConsultaService() throws SQLException, ClassNotFoundException {
        this.feedbackDao = new FeedbackConsultaDao();
        this.consultaDao = new ConsultaDao();
    }

    private boolean validarFeedback(FeedbackConsulta feedback) throws SQLException {
        if (!feedback.notaValida()) {
            System.out.println("Nota inválida! Deve ser entre 0 e 5.");
            return false;
        }

        if (!feedback.comentarioValido()) {
            System.out.println("Comentário inválido! Máx. 100 caracteres.");
            return false;
        }

        if (feedback.getConsulta() == null || feedback.getConsulta().getCodigo() <= 0) {
            System.out.println("Consulta inválida!");
            return false;
        }

        if (consultaDao.buscarPorCodigo(feedback.getConsulta().getCodigo()) == null) {
            System.out.println("Consulta não encontrada!");
            return false;
        }

        if (!feedbackDao.listarPorConsulta(feedback.getConsulta().getCodigo()).isEmpty()) {
            System.out.println("Você já deu um feedback para esta consulta!");
            return false;
        }

        return true;
    }

    public boolean cadastrarFeedback(FeedbackConsulta feedback) throws SQLException {
        if (!validarFeedback(feedback)) return false;
        return feedbackDao.inserir(feedback);
    }

    public boolean atualizarFeedback(FeedbackConsulta feedback) throws SQLException {
        if (!feedback.notaValida() || !feedback.comentarioValido()) {
            System.out.println("\nDados inválidos! Verifique nota e comentário.");
            return false;
        }
        return feedbackDao.atualizar(feedback);
    }

    public FeedbackConsulta buscarPorCodigo(int codigo) throws SQLException {
        return feedbackDao.buscarPorCodigo(codigo);
    }

    public List<FeedbackConsulta> listarTodos() throws SQLException {
        return feedbackDao.listarTodos();
    }

    public List<FeedbackConsulta> listarPorConsulta(int consultaId) throws SQLException {
        return feedbackDao.listarPorConsulta(consultaId);
    }

    public boolean deletarFeedback(int codigo) throws SQLException {
        return feedbackDao.deletar(codigo);
    }

    public void close() throws SQLException {
        feedbackDao.close();
        consultaDao.close();
    }
}
