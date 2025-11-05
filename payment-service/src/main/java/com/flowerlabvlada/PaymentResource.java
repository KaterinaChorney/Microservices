package com.flowerlabvlada;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/payments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentResource {

    @Inject
    PaymentRepository repository;

    @POST
    @Path("/authorize")
    public Response authorizePayment(PaymentRequest request) {
        return repository.authorizePayment(request)
                .map(response -> Response.ok(response).build()) // 200
                .orElse(Response.status(Response.Status.BAD_REQUEST)
                        .entity("Invalid payment request").build()); // 400
    }
}