package br.com.fiap.resource;

import br.com.fiap.dto.paciente.CadastroPacienteDto;
import br.com.fiap.dto.paciente.ListarPacienteDto;
import br.com.fiap.exception.CampoJaCadastrado;
import br.com.fiap.exception.EntidadeNaoEncontradaException;
import br.com.fiap.model.Paciente;
import br.com.fiap.service.PacienteService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/pacientes")
@Produces(MediaType.APPLICATION_JSON)
public class PacienteResource {

    @Inject
    private PacienteService pacienteService;

    @Inject
    private ModelMapper mapper;

    @GET
    public Response listarTodos() throws SQLException {
        List<Paciente> pacientes = pacienteService.listarPacientes();
        List<ListarPacienteDto> dtoList = pacientes.stream()
                .map(p -> mapper.map(p, ListarPacienteDto.class))
                .collect(Collectors.toList());
        return Response.ok(dtoList).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorCodigo(@PathParam("id") int id)
            throws EntidadeNaoEncontradaException, SQLException {
        Paciente paciente = pacienteService.buscarPorCodigo(id);
        ListarPacienteDto dto = mapper.map(paciente, ListarPacienteDto.class);
        return Response.ok(dto).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserir(@Valid CadastroPacienteDto dto, @Context UriInfo uriInfo)
            throws CampoJaCadastrado, SQLException {

        Paciente paciente = mapper.map(dto, Paciente.class);
        pacienteService.cadastrarPaciente(paciente);
        ListarPacienteDto responseDto = mapper.map(paciente, ListarPacienteDto.class);

        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(paciente.getCodigo()))
                .build();

        return Response.created(uri).entity(responseDto).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizar(@PathParam("id") int id, @Valid CadastroPacienteDto dto)
            throws EntidadeNaoEncontradaException, SQLException {
        Paciente paciente = mapper.map(dto, Paciente.class);
        paciente.setCodigo(id);
        pacienteService.atualizarPaciente(paciente);
        ListarPacienteDto responseDto = mapper.map(paciente, ListarPacienteDto.class);
        return Response.ok(responseDto).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id)
            throws EntidadeNaoEncontradaException, SQLException {
        pacienteService.deletarPaciente(id);
        return Response.noContent().build();
    }
}
