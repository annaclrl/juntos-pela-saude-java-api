package br.com.fiap.resource;

import br.com.fiap.dto.consulta.CadastroConsultaDto;
import br.com.fiap.dto.consulta.ListarConsultaDto;
import br.com.fiap.model.Consulta;
import br.com.fiap.model.Funcionario;
import br.com.fiap.model.Medico;
import br.com.fiap.model.Paciente;
import br.com.fiap.service.ConsultaService;
import br.com.fiap.exception.EntidadeNaoEncontradaException;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.modelmapper.ModelMapper;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/consultas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ConsultaResource {

    @Inject
    private ConsultaService consultaService;

    @Inject
    private ModelMapper mapper;

    @POST
    public Response inserir(@Valid CadastroConsultaDto dto, @Context UriInfo uriInfo) throws Exception {
        Consulta consulta = mapper.map(dto, Consulta.class);

        consulta.setPaciente(new Paciente());
        consulta.getPaciente().setCodigo(dto.getPacienteId());

        consulta.setMedico(new Medico());
        consulta.getMedico().setCodigo(dto.getMedicoId());

        consulta.setFuncionario(new Funcionario());
        consulta.getFuncionario().setCodigo(dto.getFuncionarioId());

        consultaService.cadastrarConsulta(consulta);


        ListarConsultaDto responseDto = new ListarConsultaDto();
        responseDto.setCodigo(consulta.getCodigo());
        responseDto.setPacienteId(dto.getPacienteId());
        responseDto.setMedicoId(dto.getMedicoId());
        responseDto.setFuncionarioId(dto.getFuncionarioId());
        responseDto.setStatus(dto.getStatus());
        responseDto.setDataHora(dto.getDataHora());

        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(consulta.getCodigo()))
                .build();

        return Response.created(uri).entity(responseDto).build();
    }

    @GET
    public Response listarTodos() throws SQLException {
        List<Consulta> consultas = consultaService.listarConsultas();
        List<ListarConsultaDto> dtoList = consultas.stream()
                .map(c -> mapper.map(c, ListarConsultaDto.class))
                .collect(Collectors.toList());
        return Response.ok(dtoList).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorCodigo(@PathParam("id") int id) throws EntidadeNaoEncontradaException, SQLException {
        Consulta consulta = consultaService.buscarPorCodigo(id);
        ListarConsultaDto dto = mapper.map(consulta, ListarConsultaDto.class);
        return Response.ok(dto).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, @Valid CadastroConsultaDto dto) throws Exception {

        Consulta consulta = mapper.map(dto, Consulta.class);

        consulta.setCodigo(id);

        consulta.setPaciente(new Paciente());
        consulta.getPaciente().setCodigo(dto.getPacienteId());

        consulta.setMedico(new Medico());
        consulta.getMedico().setCodigo(dto.getMedicoId());

        consulta.setFuncionario(new Funcionario());
        consulta.getFuncionario().setCodigo(dto.getFuncionarioId());


        consultaService.atualizarConsulta(consulta);

        ListarConsultaDto responseDto = mapper.map(consulta, ListarConsultaDto.class);
        return Response.ok(responseDto).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) throws EntidadeNaoEncontradaException, SQLException {
        consultaService.deletarConsulta(id);
        return Response.noContent().build();
    }
}
