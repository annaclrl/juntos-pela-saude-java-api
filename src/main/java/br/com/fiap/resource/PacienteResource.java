package br.com.fiap.resource;

import br.com.fiap.dao.PacienteDao;
import br.com.fiap.exeption.EntidadeNaoEncontradaException;
import br.com.fiap.model.Paciente;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@Path("/pacientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class PacienteResource {

    @Inject
    private PacienteDao dao;

    @GET
    public Response listarTodos() {
        try {
            List<Paciente> pacientes = dao.listarTodos();
            return Response.ok(pacientes).build();
        } catch (SQLException e) {
            return Response.serverError().entity("Erro ao listar pacientes: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/{id}")
    public Response buscarPorCodigo(@PathParam("id") int id) {
        try {
            Paciente paciente = dao.buscarPorCodigo(id);
            return Response.ok(paciente).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.serverError().entity("Erro ao buscar paciente: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/cpf/{cpf}")
    public Response buscarPorCpf(@PathParam("cpf") String cpf) {
        try {
            Paciente paciente = dao.buscarPorCpf(cpf);
            return Response.ok(paciente).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.serverError().entity("Erro ao buscar paciente por CPF: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/email/{email}")
    public Response buscarPorEmail(@PathParam("email") String email) {
        try {
            Paciente paciente = dao.buscarPorEmail(email);
            return Response.ok(paciente).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.serverError().entity("Erro ao buscar paciente por e-mail: " + e.getMessage()).build();
        }
    }

    @POST
    public Response inserir(Paciente paciente, @Context UriInfo uriInfo) {
        try {
            dao.inserir(paciente);
            URI uri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(paciente.getCodigo())).build();
            return Response.created(uri).entity(paciente).build();
        } catch (SQLException e) {
            return Response.serverError().entity("Erro ao cadastrar paciente: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, Paciente paciente) {
        try {
            paciente.setCodigo(id);
            dao.atualizar(paciente);
            return Response.ok(paciente).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.serverError().entity("Erro ao atualizar paciente: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) {
        try {
            dao.deletar(id);
            return Response.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.serverError().entity("Erro ao deletar paciente: " + e.getMessage()).build();
        }
    }
}
