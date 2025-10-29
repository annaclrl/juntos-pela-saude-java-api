package br.com.fiap.exception;


import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class EntidadeNaoEncontradaExceptionMapper implements ExceptionMapper<EntidadeNaoEncontradaException> {

    @Override
    public Response toResponse(EntidadeNaoEncontradaException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(exception.getMessage())
                .type(MediaType.TEXT_PLAIN)
                .build();
    }
}
