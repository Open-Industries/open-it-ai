package org.openindustries.itai.model;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@Path("/api/v1/models")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "AI Models", description = "AI model registry with lifecycle governance.")
public class AiModelResource {

    @Inject
    AiModelService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a aimodel")
    public Response create(@Valid CreateAiModelRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.create(request)).build();
    }

    @GET
    @Operation(summary = "List all")
    public List<AiModel> list() {
        return service.list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one by id")
    public AiModel get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @POST
    @Path("/{id}/approve")
    @Operation(summary = "Approve this aimodel (lifecycle transition, no body)")
    public AiModel approve(@PathParam("id") UUID id) {
        return service.approve(id);
    }

    @POST
    @Path("/{id}/retire")
    @Operation(summary = "Retire this aimodel (lifecycle transition, no body)")
    public AiModel retire(@PathParam("id") UUID id) {
        return service.retire(id);
    }
}
