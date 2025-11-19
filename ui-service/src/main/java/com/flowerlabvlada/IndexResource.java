package com.flowerlabvlada;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import java.net.URI;

@Path("/")
public class IndexResource {

    @GET
    public Response redirectToOrders() {
        return Response.status(Response.Status.TEMPORARY_REDIRECT)
                .location(URI.create("/orders"))
                .build();
    }
}