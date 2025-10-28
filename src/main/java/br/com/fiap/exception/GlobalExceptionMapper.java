package br.com.fiap.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {

        if (exception instanceof ConstraintViolationException ve) {
            String mensagem = ve.getConstraintViolations()
                    .stream()
                    .findFirst()
                    .map(v -> v.getMessage())
                    .orElse("Erro de validação");
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(mensagem)
                    .type(MediaType.TEXT_PLAIN)
                    .build();
        }

        if (exception instanceof EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }

        if (exception instanceof RegraNegocioExeption e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Erro interno no servidor."))
                .build();
    }

    public static class ErrorResponse {
        private String mensagem;

        public ErrorResponse(String mensagem) {
            this.mensagem = mensagem;
        }

        public String getMensagem() {
            return mensagem;
        }
    }
}
