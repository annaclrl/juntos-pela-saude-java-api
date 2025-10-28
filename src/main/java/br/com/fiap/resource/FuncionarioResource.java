package br.com.fiap.resource;

import br.com.fiap.dto.funcionario.CadastroFuncionarioDto;
import br.com.fiap.dto.funcionario.ListarFuncionarioDto;
import br.com.fiap.exception.*;
import br.com.fiap.model.Funcionario;
import br.com.fiap.service.FuncionarioService;
import br.com.fiap.exception.EntidadeNaoEncontradaException;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/funcionarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FuncionarioResource {

    @Inject
    private FuncionarioService funcionarioService;

    @Inject
    private ModelMapper mapper;

    @GET
    public Response listarTodos() throws SQLException {
        List<Funcionario> funcionarios = funcionarioService.listarFuncionarios();
        List<ListarFuncionarioDto> dtoList = funcionarios.stream()
                .map(f -> mapper.map(f, ListarFuncionarioDto.class))
                .collect(Collectors.toList());
        return  Response.ok(dtoList).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorCodigo(@PathParam("id") int id) throws
            EntidadeNaoEncontradaException, SQLException {
        Funcionario funcionario = funcionarioService.buscarPorCodigo(id);
        ListarFuncionarioDto dto = mapper.map(funcionario, ListarFuncionarioDto.class);
        return Response.ok(dto).build();
    }


    @POST
    public Response inserir(@Valid CadastroFuncionarioDto dto, @Context UriInfo uriInfo)
            throws CampoJaCadastrado, SQLException {
        Funcionario funcionario = mapper.map(dto, Funcionario.class);
        funcionarioService.cadastrarFuncionario(funcionario);
        ListarFuncionarioDto responseDto = mapper.map(dto, ListarFuncionarioDto.class);

        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(funcionario.getCodigo()))
                .build();
        return Response.created(uri).entity(responseDto).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, @Valid CadastroFuncionarioDto dto) throws EntidadeNaoEncontradaException, SQLException {
        Funcionario funcionario = mapper.map(dto, Funcionario.class);
        funcionario.setCodigo(id);
        funcionarioService.atualizarFuncionario(funcionario);
        ListarFuncionarioDto responseDto = mapper.map(dto, ListarFuncionarioDto.class);
        return Response.ok(responseDto).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) throws EntidadeNaoEncontradaException, SQLException {
        funcionarioService.deletarFuncionario(id);
        return Response.noContent().build();
    }
}
