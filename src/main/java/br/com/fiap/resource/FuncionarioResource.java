package br.com.fiap.resource;

import br.com.fiap.model.Funcionario;
import br.com.fiap.service.FuncionarioService;
import br.com.fiap.exeption.EntidadeNaoEncontradaException;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;

@Path("/funcionarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FuncionarioResource {

    @Inject
    private FuncionarioService funcionarioService;

    @GET
    public Response listarTodos() {
        try {
            List<Funcionario> funcionarios = funcionarioService.listarFuncionarios();
            return Response.ok(funcionarios).build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Erro ao listar funcionários: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response buscarPorCodigo(@PathParam("id") int id) {
        try {
            Funcionario funcionario = funcionarioService.buscarPorCodigo(id);
            return Response.ok(funcionario).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Erro ao buscar funcionário: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/cpf/{cpf}")
    public Response buscarPorCpf(@PathParam("cpf") String cpf) {
        try {
            Funcionario funcionario = funcionarioService.buscarPorCpf(cpf);
            return Response.ok(funcionario).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Erro ao buscar funcionário por CPF: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/email/{email}")
    public Response buscarPorEmail(@PathParam("email") String email) {
        try {
            Funcionario funcionario = funcionarioService.buscarPorEmail(email);
            return Response.ok(funcionario).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Erro ao buscar funcionário por e-mail: " + e.getMessage())
                    .build();
        }
    }

    @POST
    public Response inserir(Funcionario funcionario, @Context UriInfo uriInfo) {
        try {
            funcionarioService.cadastrarFuncionario(funcionario);
            URI uri = uriInfo.getAbsolutePathBuilder()
                    .path(String.valueOf(funcionario.getCodigo()))
                    .build();
            return Response.created(uri).entity(funcionario).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Erro ao cadastrar funcionário: " + e.getMessage())
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, Funcionario funcionario) {
        try {
            funcionario.setCodigo(id);
            funcionarioService.atualizarFuncionario(funcionario);
            return Response.ok(funcionario).build();
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
                    .entity("Erro ao atualizar funcionário: " + e.getMessage())
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) {
        try {
            funcionarioService.deletarFuncionario(id);
            return Response.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        } catch (Exception e) {
            return Response.serverError()
                    .entity("Erro ao deletar funcionário: " + e.getMessage())
                    .build();
        }
    }
}
