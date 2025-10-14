package br.com.fiap.resource;

import br.com.fiap.dao.FuncionarioDao;
import br.com.fiap.exeption.EntidadeNaoEncontradaException;
import br.com.fiap.model.Funcionario;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;
import java.util.List;

@Path("/funcionarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FuncionarioResource {

    @Inject
    private FuncionarioDao funcionarioDao;

    @GET
    public Response listarTodos() {
        try {
            List<Funcionario> funcionarios = funcionarioDao.listarTodos();
            return Response.ok(funcionarios).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao listar funcionários: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") int id) {
        try {
            Funcionario funcionario = funcionarioDao.buscarPorCodigo(id);
            return Response.ok(funcionario).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar funcionário: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/cpf/{cpf}")
    public Response buscarPorCpf(@PathParam("cpf") String cpf) {
        try {
            Funcionario funcionario = funcionarioDao.buscarPorCpf(cpf);
            return Response.ok(funcionario).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar funcionário por CPF: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/email/{email}")
    public Response buscarPorEmail(@PathParam("email") String email) {
        try {
            Funcionario funcionario = funcionarioDao.buscarPorEmail(email);
            return Response.ok(funcionario).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar funcionário por e-mail: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/telefone/{telefone}")
    public Response buscarPorTelefone(@PathParam("telefone") String telefone) {
        try {
            Funcionario funcionario = funcionarioDao.buscarPorTelefone(telefone);
            return Response.ok(funcionario).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao buscar funcionário por telefone: " + e.getMessage()).build();
        }
    }

    @POST
    public Response inserir(Funcionario funcionario) {
        try {
            funcionarioDao.inserir(funcionario);
            return Response.status(Response.Status.CREATED).entity(funcionario).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao cadastrar funcionário: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, Funcionario funcionario) {
        try {
            funcionario.setCodigo(id);
            funcionarioDao.atualizar(funcionario);
            return Response.ok(funcionario).build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao atualizar funcionário: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) {
        try {
            funcionarioDao.deletar(id);
            return Response.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao deletar funcionário: " + e.getMessage()).build();
        }
    }
}

