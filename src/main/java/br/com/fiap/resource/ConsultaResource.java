package br.com.fiap.resource;

import br.com.fiap.dto.consulta.AtualizarConsultaDto;
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
public class ConsultaResource {

    @Inject
    private ConsultaService consultaService;

    @Inject
    private ModelMapper mapper;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserir(@Valid CadastroConsultaDto dto, @Context UriInfo uriInfo) throws Exception {
        Consulta consulta = new Consulta();
        consulta.setStatus(dto.getStatus());
        consulta.setDataHora(dto.getDataHora());

        consulta.setPaciente(new Paciente());
        consulta.getPaciente().setCodigo(dto.getPacienteId());

        consulta.setMedico(new Medico());
        consulta.getMedico().setCodigo(dto.getMedicoId());

        if (dto.getFuncionarioId() != null) {
            consulta.setFuncionario(new Funcionario());
            consulta.getFuncionario().setCodigo(dto.getFuncionarioId());
        }

        consultaService.cadastrarConsulta(consulta);

        Consulta consultaCompleta = consultaService.buscarPorCodigo(consulta.getCodigo());

        ListarConsultaDto responseDto = new ListarConsultaDto();
        responseDto.setCodigo(consultaCompleta.getCodigo());
        responseDto.setPacienteId(consultaCompleta.getPaciente() != null ? consultaCompleta.getPaciente().getCodigo() : null);
        responseDto.setMedicoId(consultaCompleta.getMedico() != null ? consultaCompleta.getMedico().getCodigo() : null);
        responseDto.setFuncionarioId(consultaCompleta.getFuncionario() != null ? consultaCompleta.getFuncionario().getCodigo() : null);
        responseDto.setStatus(consultaCompleta.getStatus());
        responseDto.setDataHora(consultaCompleta.getDataHora());

        if (consultaCompleta.getMedico() != null) {
            responseDto.setNomeMedico(consultaCompleta.getMedico().getNome());
            responseDto.setEspecialidadeMedico(consultaCompleta.getMedico().getEspecialidade());
        }

        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(consultaCompleta.getCodigo()))
                .build();

        return Response.created(uri).entity(responseDto).build();
    }


    @GET
    public Response listarTodos() throws SQLException {
        List<Consulta> consultas = consultaService.listarConsultas();
        List<ListarConsultaDto> dtoList = consultas.stream()
                .map(c -> {
                    ListarConsultaDto dto = new ListarConsultaDto();
                    dto.setCodigo(c.getCodigo());
                    dto.setPacienteId(c.getPaciente() != null ? c.getPaciente().getCodigo() : null);
                    dto.setMedicoId(c.getMedico() != null ? c.getMedico().getCodigo() : null);
                    dto.setFuncionarioId(c.getFuncionario() != null ? c.getFuncionario().getCodigo() : null);
                    dto.setStatus(c.getStatus());
                    dto.setDataHora(c.getDataHora());

                    if(c.getMedico() != null) {
                        dto.setNomeMedico(c.getMedico().getNome());
                        dto.setEspecialidadeMedico(c.getMedico().getEspecialidade());
                    }
                    return dto;
                })
                .collect(Collectors.toList());

        return Response.ok(dtoList).build();
    }

    @GET
    @Path("/paciente/{codigo}")
    public Response listarConsultasPorCodigoPaciente(@PathParam("codigo") int codigo) throws SQLException {
        List<Consulta> consultas = consultaService.listarConsultasPorCodigoPaciente(codigo);
        List<ListarConsultaDto> dtoList = consultas.stream()
                .map(c -> {
                    ListarConsultaDto dto = new ListarConsultaDto();
                    dto.setCodigo(c.getCodigo());
                    dto.setPacienteId(c.getPaciente() != null ? c.getPaciente().getCodigo() : null);
                    dto.setMedicoId(c.getMedico() != null ? c.getMedico().getCodigo() : null);
                    dto.setFuncionarioId(c.getFuncionario() != null ? c.getFuncionario().getCodigo() : null);
                    dto.setStatus(c.getStatus());
                    dto.setDataHora(c.getDataHora());

                    if(c.getMedico() != null) {
                        dto.setNomeMedico(c.getMedico().getNome());
                        dto.setEspecialidadeMedico(c.getMedico().getEspecialidade());
                    }
                    return dto;
                })
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
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizar(@PathParam("id") int id, @Valid AtualizarConsultaDto dto) throws Exception {

        Consulta consulta = mapper.map(dto, Consulta.class);

        consulta.setCodigo(id);

        consulta.setPaciente(new Paciente());
        consulta.getPaciente().setCodigo(dto.getPacienteId());

        consulta.setMedico(new Medico());
        consulta.getMedico().setCodigo(dto.getMedicoId());

        consulta.setFuncionario(null);
        if (dto.getFuncionarioId() != null) {
            consulta.setFuncionario(new Funcionario());
            consulta.getFuncionario().setCodigo(dto.getFuncionarioId());
        }

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
