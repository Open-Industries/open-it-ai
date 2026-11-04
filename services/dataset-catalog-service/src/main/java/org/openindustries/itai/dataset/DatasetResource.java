package org.openindustries.itai.dataset;

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

@Path("/api/v1/datasets")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Datasets", description = "Dataset catalogue — metadata only, never content.")
public class DatasetResource {

    @Inject
    DatasetService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a dataset")
    public Response create(@Valid CreateDatasetRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.create(request)).build();
    }

    @GET
    @Operation(summary = "List all")
    public List<Dataset> list() {
        return service.list();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one by id")
    public Dataset get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @POST
    @Path("/{id}/approve")
    @Operation(summary = "Approve (lifecycle transition, no body)")
    public Dataset approve(@PathParam("id") UUID id) {
        return service.approve(id);
    }
}
