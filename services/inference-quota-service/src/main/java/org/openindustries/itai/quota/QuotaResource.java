package org.openindustries.itai.quota;

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

@Path("/api/v1/quotas")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Inference Quotas", description = "Guarded inference/training budgets per team and the usage records against them.")
public class QuotaResource {

    @Inject
    QuotaService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Open a inferencequota")
    public Response open(@Valid OpenInferenceQuotaRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.open(request)).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one by id")
    public InferenceQuota get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @POST
    @Path("/{id}/usage")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Draw from the account (rejects if it would overdraw, 422)")
    public InferenceUsage consume(@PathParam("id") UUID id, @Valid CreateInferenceUsageRequest request) {
        return service.consume(id, request);
    }

    @GET
    @Path("/{id}/usage")
    @Operation(summary = "List consumption records of an account")
    public List<InferenceUsage> records(@PathParam("id") UUID id) {
        return service.recordsOf(id);
    }
}
