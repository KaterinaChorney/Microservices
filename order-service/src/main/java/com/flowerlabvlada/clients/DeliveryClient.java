package com.flowerlabvlada.clients;

import com.flowerlabvlada.entities.DeliveryQuote;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "com.flowerlabvlada.clients.DeliveryClient")
@Path("/delivery")
public interface DeliveryClient {

    @GET
    @Path("/quote")
    DeliveryQuote getDeliveryQuote(@QueryParam("address") String address);
}