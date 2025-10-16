package br.com.fiap.resource;

import br.com.fiap.model.Medico;
import br.com.fiap.service.MedicoService;
import br.com.fiap.exeption.EntidadeNaoEncontradaException;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;

@Path("/medicos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MedicoResource {

    @Inject
    private MedicoService medicoService;

    @GET
    public Response listarTodos() {
        try {
            List<Medico> medicos = medicoService.listarMedicos();
            return Response.ok(medicos).build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Erro ao listar médicos: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response buscarPorCodigo(@PathParam("id") int id) {
        try {
            Medico medico = medicoService.buscarPorCodigo(id);
            return Response.ok(medico).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Erro ao buscar médico: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/crm/{crm}")
    public Response buscarPorCrm(@PathParam("crm") String crm) {
        try {
            Medico medico = medicoService.buscarPorCrm(crm);
            return Response.ok(medico).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Erro ao buscar médico por CRM: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/cpf/{cpf}")
    public Response buscarPorCpf(@PathParam("cpf") String cpf) {
        try {
            Medico medico = medicoService.buscarPorCpf(cpf);
            return Response.ok(medico).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Erro ao buscar médico por CPF: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/email/{email}")
    public Response buscarPorEmail(@PathParam("email") String email) {
        try {
            Medico medico = medicoService.buscarPorEmail(email);
            return Response.ok(medico).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Erro ao buscar médico por e-mail: " + e.getMessage())
                    .build();
        }
    }

    @POST
    public Response inserir(Medico medico, @Context UriInfo uriInfo) {
        try {
            medicoService.cadastrarMedico(medico);
            URI uri = uriInfo.getAbsolutePathBuilder()
                    .path(String.valueOf(medico.getCodigo()))
                    .build();
            return Response.created(uri).entity(medico).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Erro ao cadastrar médico: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, Medico medico) {
        try {
            medico.setCodigo(id);
            medicoService.atualizarMedico(medico);
            return Response.ok(medico).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Erro ao atualizar médico: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) {
        try {
            medicoService.deletarMedico(id);
            return Response.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Erro ao deletar médico: " + e.getMessage())
                    .build();
        }
    }
}
