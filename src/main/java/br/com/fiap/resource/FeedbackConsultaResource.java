package br.com.fiap.resource;

import br.com.fiap.model.FeedbackConsulta;
import br.com.fiap.service.FeedbackConsultaService;
import br.com.fiap.exception.EntidadeNaoEncontradaException;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/feedbacks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FeedbackConsultaResource {

    @Inject
    private FeedbackConsultaService feedbackService;

    @GET
    public Response listarTodos() {
        try {
            List<FeedbackConsulta> lista = feedbackService.listarTodos();
            return Response.ok(lista).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar feedbacks: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response buscarPorCodigo(@PathParam("id") int id) {
        try {
            FeedbackConsulta feedback = feedbackService.buscarPorCodigo(id);
            return Response.ok(feedback).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar feedback: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/consulta/{idConsulta}")
    public Response listarPorConsulta(@PathParam("idConsulta") int idConsulta) {
        try {
            List<FeedbackConsulta> lista = feedbackService.listarPorConsulta(idConsulta);
            return Response.ok(lista).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar feedbacks da consulta: " + e.getMessage())
                    .build();
        }
    }

    @POST
    public Response inserir(FeedbackConsulta feedback) {
        try {
            feedbackService.cadastrarFeedback(feedback);
            return Response.status(Response.Status.CREATED).entity(feedback).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao inserir feedback: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, FeedbackConsulta feedback) {
        try {
            feedback.setCodigo(id);
            feedbackService.atualizarFeedback(feedback);
            return Response.ok(feedback).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao atualizar feedback: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) {
        try {
            feedbackService.deletarFeedback(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao deletar feedback: " + e.getMessage())
                    .build();
        }
    }
}
