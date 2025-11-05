package com.flowerlabvlada;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/delivery")
@Produces(MediaType.APPLICATION_JSON)
public class DeliveryResource {

    @Inject
    DeliveryRepository repository;

    @GET
    @Path("/quote")
    public Response getDeliveryQuote(@QueryParam("address") String address) {
        return repository.getQuoteForAddress(address)
                .map(quote -> Response.ok(quote).build())
                .orElse(Response.status(Response.Status.BAD_REQUEST)
                        .entity("Address is required").build());
    }
}