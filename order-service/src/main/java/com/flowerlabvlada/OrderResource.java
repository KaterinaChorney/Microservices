package com.flowerlabvlada;

import com.flowerlabvlada.clients.CatalogClient;
import com.flowerlabvlada.clients.DeliveryClient;
import com.flowerlabvlada.clients.PaymentClient;
import com.flowerlabvlada.customer.grpc.CustomerGrpcService;
import com.flowerlabvlada.customer.grpc.CustomerRequest;
import com.flowerlabvlada.customer.grpc.CustomerResponse;
import com.flowerlabvlada.models.*;
import io.quarkus.grpc.GrpcClient;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import com.flowerlabvlada.models.Bouquet;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    @RestClient
    CatalogClient catalogClient;

    @Inject
    @RestClient
    DeliveryClient deliveryClient;

    @Inject
    @RestClient
    PaymentClient paymentClient;

    @GrpcClient("customer")
    CustomerGrpcService customerClient;

    @Inject
    OrderRepository orderRepository;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createOrder(OrderRequest orderRequest) {

        CustomerResponse customer;
        try {
            customer = customerClient
                    .getCustomerById(CustomerRequest.newBuilder()
                            .setCustomerId(orderRequest.customerId())
                            .build())
                    .await().atMost(Duration.ofSeconds(5));
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Customer not found: " + e.getMessage()).build();
        }

        Bouquet bouquet;
        try {
            bouquet = catalogClient.getBouquetById(orderRequest.bouquetId());
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Bouquet not found: " + e.getMessage()).build();
        }

        if (bouquet.stockQuantity() <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Bouquet is out of stock: " + bouquet.name()).build();
        }

        DeliveryQuote deliveryQuote;
        try {
            deliveryQuote = deliveryClient.getDeliveryQuote(customer.getAddress());
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Delivery service failed: " + e.getMessage()).build();
        }

        double totalAmount = bouquet.price() + deliveryQuote.price();

        PaymentResponse paymentResponse;
        try {
            PaymentRequest paymentRequest = new PaymentRequest(customer.getId(), totalAmount);
            paymentResponse = paymentClient.authorizePayment(paymentRequest);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Payment service failed: " + e.getMessage()).build();
        }

        if (!paymentResponse.status().equals("APPROVED")) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Payment declined: " + paymentResponse.message()).build();
        }

        Order newOrder = new Order(
                null,
                customer.getId(),
                bouquet.id(),
                totalAmount,
                customer.getAddress(),
                paymentResponse.paymentId(),
                "COMPLETED",
                LocalDate.now()
        );

        Order savedOrder = orderRepository.save(newOrder);

        return Response.status(Response.Status.CREATED).entity(savedOrder).build();//201
    }

    @GET
    public List<Order> getAllOrders() {
        return orderRepository.listAll();
    }

    @GET
    @Path("/bouquets")
    public List<Bouquet> getAvailableBouquets() {
        return catalogClient.getAllBouquets();
    }
}