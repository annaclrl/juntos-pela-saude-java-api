package br.com.fiap.resource;

import br.com.fiap.model.Paciente;
import br.com.fiap.service.PacienteService;
import br.com.fiap.exeption.EntidadeNaoEncontradaException;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;

@Path("/pacientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PacienteResource {

    @Inject
    private PacienteService pacienteService;

    @GET
    public Response listarTodos() {
        try {
            List<Paciente> pacientes = pacienteService.listarPacientes();
            return Response.ok(pacientes).build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Erro ao listar pacientes: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response buscarPorCodigo(@PathParam("id") int id) {
        try {
            Paciente paciente = pacienteService.buscarPorCodigo(id);
            return Response.ok(paciente).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Erro ao buscar paciente: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/cpf/{cpf}")
    public Response buscarPorCpf(@PathParam("cpf") String cpf) {
        try {
            Paciente paciente = pacienteService.buscarPorCpf(cpf);
            return Response.ok(paciente).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Erro ao buscar paciente por CPF: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/email/{email}")
    public Response buscarPorEmail(@PathParam("email") String email) {
        try {
            Paciente paciente = pacienteService.buscarPorEmail(email);
            return Response.ok(paciente).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Erro ao buscar paciente por e-mail: " + e.getMessage())
                    .build();
        }
    }

    @POST
    public Response inserir(Paciente paciente, @Context UriInfo uriInfo) {
        try {
            pacienteService.cadastrarPaciente(paciente);
            URI uri = uriInfo.getAbsolutePathBuilder()
                    .path(String.valueOf(paciente.getCodigo()))
                    .build();
            return Response.created(uri).entity(paciente).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Erro ao cadastrar paciente: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, Paciente paciente) {
        try {
            paciente.setCodigo(id);
            pacienteService.atualizarPaciente(paciente);
            return Response.ok(paciente).build();
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
                    .entity("Erro ao atualizar paciente: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) {
        try {
            pacienteService.deletarPaciente(id);
            return Response.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Erro ao deletar paciente: " + e.getMessage())
                    .build();
        }
    }
}
