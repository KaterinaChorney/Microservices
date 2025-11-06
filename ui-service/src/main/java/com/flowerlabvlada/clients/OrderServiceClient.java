package com.flowerlabvlada.clients;

import com.flowerlabvlada.models.Order;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import java.util.List;
import com.flowerlabvlada.models.Bouquet;

import com.flowerlabvlada.models.OrderRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.core.Response;

@RegisterRestClient(configKey = "com.flowerlabvlada.clients.OrderServiceClient")
@Path("/orders")
public interface OrderServiceClient {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Order> getAllOrders();

    @GET
    @Path("/bouquets")
    List<Bouquet> getAvailableBouquets();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    //Order createOrder(OrderRequest request);
    Response createOrder(OrderRequest request);
}