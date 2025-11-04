package br.com.fiap.exception;

public class FeedbackJaExisteException extends RuntimeException {
    public FeedbackJaExisteException(int consultaId) {
        super("Você já deu um feedback para a consulta " + consultaId + "!");
    }
}
