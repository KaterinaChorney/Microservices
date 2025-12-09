package com.flowerlabvlada;

import com.flowerlabvlada.clients.CatalogClient;
import com.flowerlabvlada.clients.DeliveryClient;
import com.flowerlabvlada.clients.PaymentClient;
import com.flowerlabvlada.customer.grpc.CustomerGrpcService;
import com.flowerlabvlada.customer.grpc.CustomerRequest;
import com.flowerlabvlada.customer.grpc.CustomerResponse;
import com.flowerlabvlada.entities.*;
import io.quarkus.grpc.GrpcClient;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject @RestClient CatalogClient catalogClient;
    @Inject @RestClient DeliveryClient deliveryClient;
    @Inject @RestClient PaymentClient paymentClient;
    @GrpcClient("customer") CustomerGrpcService customerClient;
    @Inject OrderRepository orderRepository;
    @Inject
    @Channel("orders-out")
    Emitter<String> orderEmitter;
    @Inject
    ObjectMapper objectMapper;

    @POST
    @Transactional
    public Response createOrder(OrderRequest orderRequest) {
        CustomerResponse customer;
        try {
            customer = customerClient.getCustomerById(CustomerRequest.newBuilder().setCustomerId(orderRequest.customerId()).build()).await().atMost(Duration.ofSeconds(5));
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Customer not found: " + e.getMessage()).build();
        }

        Bouquet bouquet;
        try {
            bouquet = catalogClient.getBouquetById(orderRequest.bouquetId());
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).entity("Bouquet not found: " + e.getMessage()).build();
        }
        if (bouquet.stockQuantity() <= 0) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Bouquet is out of stock: " + bouquet.name()).build();
        }

        DeliveryQuote deliveryQuote;
        try {
            deliveryQuote = deliveryClient.getDeliveryQuote(customer.getAddress());
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Delivery service failed: " + e.getMessage()).build();
        }

        double totalAmount = bouquet.price() + deliveryQuote.price();

        PaymentResponse paymentResponse;
        try {
            PaymentRequest paymentRequest = new PaymentRequest(customer.getId(), totalAmount);
            paymentResponse = paymentClient.authorizePayment(paymentRequest);
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Payment service failed: " + e.getMessage()).build();
        }

        if (!paymentResponse.status().equals("APPROVED")) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Payment declined: " + paymentResponse.message()).build();
        }

        Order newOrder = new Order(
                customer.getId(),
                bouquet.id(),
                totalAmount,
                customer.getAddress(),
                paymentResponse.paymentId(),
                "COMPLETED",
                LocalDate.now()
        );

        orderRepository.persist(newOrder);

        try {
            OrderCreatedEvent event = new OrderCreatedEvent(
                    newOrder.id,
                    newOrder.totalAmount,
                    newOrder.deliveryAddress
            );

            String jsonEvent = objectMapper.writeValueAsString(event);

            orderEmitter.send(jsonEvent);

            System.out.println(">>> Message sent to RabbitMQ: " + jsonEvent);

        } catch (JsonProcessingException e) {
            System.err.println("Failed to send message to RabbitMQ: " + e.getMessage());
        }

        return Response.status(Response.Status.CREATED).entity(newOrder).build();
    }

    @GET
    public List<Order> getAllOrders() {
        return orderRepository.listAll();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateOrder(@PathParam("id") Long id, Order updatedOrder) {
        Order entity = orderRepository.findById(id);
        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        entity.status = updatedOrder.status;
        return Response.ok(entity).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteOrder(@PathParam("id") Long id) {
        boolean deleted = orderRepository.deleteById(id);
        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build(); // 204
        } else {
            return Response.status(Response.Status.NOT_FOUND).build(); // 404
        }
    }

    @GET
    @Path("/bouquets")
    public List<Bouquet> getAvailableBouquets() {
        return catalogClient.getAllBouquets();
    }
}