package br.com.fiap.resource;

import br.com.fiap.model.Consulta;
import br.com.fiap.service.ConsultaService;
import br.com.fiap.exception.EntidadeNaoEncontradaException;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/consultas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConsultaResource {

    @Inject
    private ConsultaService consultaService;

    @POST
    public Response inserir(Consulta consulta) {
        try {
            consultaService.cadastrarConsulta(consulta);
            return Response.status(Response.Status.CREATED).entity(consulta).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao inserir consulta: " + e.getMessage())
                    .build();
        }
    }

    @GET
    public Response listarTodos() {
        try {
            List<Consulta> consultas = consultaService.listarConsultas();
            return Response.ok(consultas).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar consultas: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{codigo}")
    public Response buscarPorCodigo(@PathParam("codigo") int codigo) {
        try {
            Consulta consulta = consultaService.buscarPorCodigo(codigo);
            return Response.ok(consulta).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar consulta: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/medico/{codigo}")
    public Response listarPorMedico(@PathParam("codigo") int codigo) {
        try {
            List<Consulta> consultas = consultaService.listarPorMedico(codigo);
            return Response.ok(consultas).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar consultas do médico: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/paciente/{codigo}")
    public Response listarPorPaciente(@PathParam("codigo") int codigo) {
        try {
            List<Consulta> consultas = consultaService.listarPorPaciente(codigo);
            return Response.ok(consultas).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar consultas do paciente: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{codigo}")
    public Response atualizar(@PathParam("codigo") int codigo, Consulta consulta) {
        try {
            consulta.setCodigo(codigo);
            consultaService.atualizarConsulta(consulta);
            return Response.ok(consulta).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Erro ao atualizar consulta: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{codigo}")
    public Response deletar(@PathParam("codigo") int codigo) {
        try {
            consultaService.deletarConsulta(codigo);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao deletar consulta: " + e.getMessage())
                    .build();
        }
    }
}
