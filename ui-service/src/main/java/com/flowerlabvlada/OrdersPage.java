package com.flowerlabvlada;

import com.flowerlabvlada.clients.OrderServiceClient;
import com.flowerlabvlada.models.Order;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import java.util.List;
import com.flowerlabvlada.models.Bouquet;
import com.flowerlabvlada.models.OrderRequest;
import jakarta.ws.rs.core.Response;
import java.net.URI;

@Path("/orders")
public class OrdersPage {

    @Inject
    Template OrdersPage;

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    @RestClient
    OrderServiceClient orderServiceClient;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        String name = securityIdentity.getPrincipal().getName();
        List<Order> orders = orderServiceClient.getAllOrders();
        List<Bouquet> bouquets = orderServiceClient.getAvailableBouquets(); // <-- НОВИЙ РЯДОК

        return OrdersPage
                .data("name", name)
                .data("orders", orders)
                .data("bouquets", bouquets);
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response createOrder(@FormParam("bouquetId") Long bouquetId) {

        Long customerId = 1L;
        OrderRequest request = new OrderRequest(customerId, bouquetId);

        try {
            Response response = orderServiceClient.createOrder(request);
            if (response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL) {
                String errorMsg = response.readEntity(String.class);
                System.err.println("ПОМИЛКА СТВОРЕННЯ (від order-service): " + errorMsg);
            }
            response.close();

        } catch (Exception e) {
            System.err.println("ПОМИЛКА КЛІЄНТА (ui-service): " + e.getMessage());
        }
        return Response.status(Response.Status.SEE_OTHER)
                .location(URI.create("/orders"))
                .build();
    }
}