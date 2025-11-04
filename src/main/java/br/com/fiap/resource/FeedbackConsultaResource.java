package br.com.fiap.resource;

import br.com.fiap.dto.feedbackconuslta.CadastroFeedbackConsultaDto;
import br.com.fiap.dto.feedbackconuslta.AtualizarFeedbackConsultaDto;
import br.com.fiap.dto.feedbackconuslta.ListarFeedbackConsultaDto;
import br.com.fiap.exception.EntidadeNaoEncontradaException;
import br.com.fiap.model.Consulta;
import br.com.fiap.model.FeedbackConsulta;
import br.com.fiap.service.FeedbackConsultaService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.modelmapper.ModelMapper;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/feedbacks")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FeedbackConsultaResource {

    @Inject
    private FeedbackConsultaService feedbackService;

    @Inject
    private ModelMapper mapper;

    @POST
    public Response inserir(@Valid CadastroFeedbackConsultaDto dto, @Context UriInfo uriInfo) throws Exception {
        FeedbackConsulta feedback = mapper.map(dto, FeedbackConsulta.class);

        Consulta consulta = new Consulta();
        consulta.setCodigo(dto.getConsultaId());
        feedback.setConsulta(consulta);

        feedbackService.cadastrarFeedback(feedback);

        ListarFeedbackConsultaDto responseDto = new ListarFeedbackConsultaDto();
        responseDto.setCodigo(feedback.getCodigo());
        responseDto.setConsultaId(feedback.getConsulta().getCodigo());
        responseDto.setComentario(feedback.getComentario());
        responseDto.setNota(feedback.getNota());

        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(feedback.getCodigo()))
                .build();

        return Response.created(uri).entity(responseDto).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, @Valid AtualizarFeedbackConsultaDto dto) throws Exception {
        FeedbackConsulta feedback = mapper.map(dto, FeedbackConsulta.class);

        feedback.setCodigo(id);

        Consulta consulta = new Consulta();
        consulta.setCodigo(dto.getConsultaId());
        feedback.setConsulta(consulta);

        feedbackService.atualizarFeedback(feedback);
        ListarFeedbackConsultaDto responseDto = new ListarFeedbackConsultaDto();
        responseDto.setCodigo(feedback.getCodigo());
        responseDto.setConsultaId(feedback.getConsulta().getCodigo());
        responseDto.setComentario(feedback.getComentario());
        responseDto.setNota(feedback.getNota());

        return Response.ok(responseDto).build();
    }

    @GET
    public Response listarTodos() throws SQLException {
        List<FeedbackConsulta> feedbacks = feedbackService.listarTodos();
        List<ListarFeedbackConsultaDto> dtoList = feedbacks.stream()
                .map(f -> mapper.map(f, ListarFeedbackConsultaDto.class))
                .collect(Collectors.toList());
        return Response.ok(dtoList).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorCodigo(@PathParam("id") int id) throws EntidadeNaoEncontradaException, SQLException {
        FeedbackConsulta feedback = feedbackService.buscarPorCodigo(id);
        ListarFeedbackConsultaDto dto = new ListarFeedbackConsultaDto();
        dto.setCodigo(feedback.getCodigo());
        dto.setConsultaId(feedback.getConsulta().getCodigo());
        dto.setComentario(feedback.getComentario());
        dto.setNota(feedback.getNota());

        return Response.ok(dto).build();

    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) throws EntidadeNaoEncontradaException, SQLException {
        feedbackService.deletarFeedback(id);
        return Response.noContent().build();
    }
}
