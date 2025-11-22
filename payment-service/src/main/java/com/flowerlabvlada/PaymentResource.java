package com.flowerlabvlada;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/payments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentResource {

    @Inject
    PaymentRepository repository;

    @POST
    @Path("/authorize")
    @Transactional
    public Response authorizePayment(PaymentRequest request) {
        return repository.authorizePayment(request)
                .map(response -> Response.ok(response).build())
                .orElse(Response.status(Response.Status.BAD_REQUEST)
                        .entity("Invalid payment request").build());
    }

    @GET
    public List<PaymentTransaction> getAllPayments() {
        return repository.listAll();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletePayment(@PathParam("id") Long id) {
        boolean deleted = repository.deleteById(id);
        return deleted ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}