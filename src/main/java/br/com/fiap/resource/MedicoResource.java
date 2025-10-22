package br.com.fiap.resource;


import br.com.fiap.model.Medico;
import br.com.fiap.service.MedicoService;
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
    public Response listarTodos() throws Exception {
        List<Medico> medicos = medicoService.listarMedicos();
        return Response.ok(medicos).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorCodigo(@PathParam("id") int id) throws Exception {
        Medico medico = medicoService.buscarPorCodigo(id);
        return Response.ok(medico).build();
    }

    @POST
    public Response inserir(Medico medico, @Context UriInfo uriInfo) throws Exception {
        medicoService.cadastrarMedico(medico);
        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(medico.getCodigo()))
                .build();
        return Response.created(uri).entity(medico).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizar(@PathParam("id") int id, Medico medico) throws Exception {
        medico.setCodigo(id);
        medicoService.atualizarMedico(medico);
        return Response.ok(medico).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) throws Exception {
        medicoService.deletarMedico(id);
        return Response.noContent().build();
    }
}
