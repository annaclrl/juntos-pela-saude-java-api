package br.com.fiap.exeption;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class RegraNegocioExceptionMapper implements ExceptionMapper<RegraNegocioExeption> {

    @Override
    public Response toResponse(RegraNegocioExeption e) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(e.getMessage())
                .build();
    }
}
