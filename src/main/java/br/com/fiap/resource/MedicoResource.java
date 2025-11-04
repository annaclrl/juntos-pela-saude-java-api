package br.com.fiap.resource;


import br.com.fiap.dto.medico.CadastroMedicoDto;
import br.com.fiap.dto.medico.ListarMedicoDto;
import br.com.fiap.exception.*;
import br.com.fiap.model.Medico;
import br.com.fiap.service.MedicoService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/medicos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MedicoResource {

    @Inject
    private MedicoService medicoService;

    @Inject
    private ModelMapper mapper;

    @GET
    public Response listarTodos() throws SQLException {
        List<Medico> medicos = medicoService.listarMedicos();
        List<ListarMedicoDto> dtoList = medicos.stream()
                .map(m -> mapper.map(m, ListarMedicoDto.class))
                .collect(Collectors.toList());
        return Response.ok(dtoList).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorCodigo(@PathParam("id") int id)
            throws EntidadeNaoEncontradaException, SQLException {
        Medico medico = medicoService.buscarPorCodigo(id);
        ListarMedicoDto dto = mapper.map(medico, ListarMedicoDto.class);
        return Response.ok(dto).build();
    }

    @POST
    public Response inserir(@Valid CadastroMedicoDto dto, @Context UriInfo uriInfo)
            throws CampoJaCadastrado, SQLException {
        Medico medico = mapper.map(dto, Medico.class);
        medicoService.cadastrarMedico(medico);
        ListarMedicoDto responseDto = mapper.map(dto, ListarMedicoDto.class);

        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(medico.getCodigo()))
                .build();
        return Response.created(uri).entity(responseDto).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, @Valid CadastroMedicoDto dto) throws EntidadeNaoEncontradaException, SQLException {
        Medico medico = mapper.map(dto, Medico.class);
        medico.setCodigo(id);
        medicoService.atualizarMedico(medico);
        ListarMedicoDto responseDto = mapper.map(dto, ListarMedicoDto.class);
        return Response.ok(responseDto).build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(MediaType.WILDCARD)
    public Response deletar(@PathParam("id") int id) throws EntidadeNaoEncontradaException, SQLException {
        medicoService.deletarMedico(id);
        return Response.noContent().build();
    }
}
