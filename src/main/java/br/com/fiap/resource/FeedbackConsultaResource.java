package br.com.fiap.resource;

import br.com.fiap.model.FeedbackConsulta;
import br.com.fiap.service.FeedbackConsultaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@Path("/feedbacks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FeedbackConsultaResource {

    @Inject
    private FeedbackConsultaService feedbackService;

    @POST
    public Response inserir(@Valid FeedbackConsulta feedback, @Context UriInfo uriInfo) {
        try {
            feedbackService.cadastrarFeedback(feedback);

            URI uri = uriInfo.getAbsolutePathBuilder()
                    .path(String.valueOf(feedback.getCodigo()))
                    .build();
            return Response.created(uri).entity(feedback).build();

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, @Valid FeedbackConsulta feedback) {
        try {
            feedback.setCodigo(id);
            feedbackService.atualizarFeedback(feedback);
            return Response.ok(feedback).build();

        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    public Response listarTodos() throws SQLException {
        List<FeedbackConsulta> lista = feedbackService.listarTodos();
        return Response.ok(lista).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorCodigo(@PathParam("id") int id) {
        try {
            FeedbackConsulta feedback = feedbackService.buscarPorCodigo(id);
            return Response.ok(feedback).build();

        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) {
        try {
            feedbackService.deletarFeedback(id);
            return Response.noContent().build();

        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }
}
