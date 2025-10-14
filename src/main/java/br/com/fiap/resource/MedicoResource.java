package br.com.fiap.resource;

import br.com.fiap.dao.MedicoDao;
import br.com.fiap.exeption.EntidadeNaoEncontradaException;
import br.com.fiap.model.Medico;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/medicos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MedicoResource {

    @Inject
    private MedicoDao medicoDao;

    @GET
    public Response listarTodos() {
        try {
            List<Medico> medicos = medicoDao.listarTodos();
            return Response.ok(medicos).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar médicos: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") int id) {
        try {
            Medico medico = medicoDao.buscarPorCodigo(id);
            return Response.ok(medico).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar médico: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/crm/{crm}")
    public Response buscarPorCrm(@PathParam("crm") String crm) {
        try {
            Medico medico = medicoDao.buscarPorCrm(crm);
            return Response.ok(medico).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar médico por CRM: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/cpf/{cpf}")
    public Response buscarPorCpf(@PathParam("cpf") String cpf) {
        try {
            Medico medico = medicoDao.buscarPorCpf(cpf);
            return Response.ok(medico).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar médico por CPF: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/email/{email}")
    public Response buscarPorEmail(@PathParam("email") String email) {
        try {
            Medico medico = medicoDao.buscarPorEmail(email);
            return Response.ok(medico).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar médico por e-mail: " + e.getMessage()).build();
        }
    }

    @POST
    public Response inserir(Medico medico) {
        try {
            medicoDao.inserir(medico);
            return Response.status(Response.Status.CREATED).entity(medico).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao cadastrar médico: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, Medico medico) {
        try {
            medico.setCodigo(id);
            medicoDao.atualizar(medico);
            return Response.ok(medico).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar médico: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) {
        try {
            medicoDao.deletar(id);
            return Response.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao deletar médico: " + e.getMessage()).build();
        }
    }
}

