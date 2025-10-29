package br.com.fiap.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.LinkedHashMap;
import java.util.Map;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {


        if (exception instanceof ConstraintViolationException ve) {
            Map<String, String> erros = new LinkedHashMap<>();


            for (ConstraintViolation<?> violacao : ve.getConstraintViolations()) {
                String campo = violacao.getPropertyPath().toString();
                erros.putIfAbsent(campo, violacao.getMessage());
            }

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ValidationErrorResponse("Erro de validação", erros))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }


        if (exception instanceof RegraNegocioExeption e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }


        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Erro interno no servidor."))
                .type(MediaType.APPLICATION_JSON)
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


    public static class ValidationErrorResponse extends ErrorResponse {
        private Map<String, String> campos;

        public ValidationErrorResponse(String mensagem, Map<String, String> campos) {
            super(mensagem);
            this.campos = campos;
        }

        public Map<String, String> getCampos() {
            return campos;
        }
    }
}
