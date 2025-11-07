package br.com.fiap.mapper;

import br.com.fiap.dto.exception.ErrorResponseDto;
import br.com.fiap.dto.exception.ValidationErrorResponseDto;
import br.com.fiap.exception.*;
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
        exception.printStackTrace();

        if (exception instanceof ConstraintViolationException ve) {
            Map<String, String> erros = new LinkedHashMap<>();

            for (ConstraintViolation<?> violacao : ve.getConstraintViolations()) {
                String campo = violacao.getPropertyPath().toString();
                erros.putIfAbsent(campo, violacao.getMessage());
            }

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ValidationErrorResponseDto("Erro de validação", erros))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        if (exception instanceof RegraNegocioExeption e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponseDto(e.getMessage()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        if (exception instanceof CampoJaCadastrado e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorResponseDto(e.getMessage()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        if (exception instanceof EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponseDto(e.getMessage()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        if (exception instanceof FeedbackJaExisteException e) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorResponseDto(e.getMessage()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponseDto("Erro interno no servidor."))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
