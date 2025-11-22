package com.flowerlabvlada;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/delivery")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DeliveryResource {

    @Inject
    DeliveryRepository repository;

    @GET
    @Path("/quote")
    public Response getDeliveryQuote(@QueryParam("address") String address) {
        return repository.calculateQuoteForAddress(address)
                .map(quote -> Response.ok(quote).build())
                .orElse(Response.status(Response.Status.BAD_REQUEST)
                        .entity("Address is required").build());
    }

    @GET
    @Path("/tariffs")
    public List<DeliveryTariff> getAllTariffs() {
        return repository.listAll();
    }

    @POST
    @Path("/tariffs")
    @Transactional
    public Response addTariff(DeliveryTariff tariff) {
        repository.persist(tariff);
        return Response.status(Response.Status.CREATED).entity(tariff).build();
    }

    @PUT
    @Path("/tariffs/{id}")
    @Transactional
    public Response updateTariff(@PathParam("id") Long id, DeliveryTariff tariffUpdates) {
        DeliveryTariff entity = repository.findById(id);

        if (entity == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        if (tariffUpdates.getCityKeyword() != null) {
            entity.setCityKeyword(tariffUpdates.getCityKeyword());
        }
        if (tariffUpdates.getPrice() != null) {
            entity.setPrice(tariffUpdates.getPrice());
        }
        return Response.ok(entity).build();
    }

    @DELETE
    @Path("/tariffs/{id}")
    @Transactional
    public Response deleteTariff(@PathParam("id") Long id) {
        boolean deleted = repository.deleteById(id);
        return deleted ? Response.noContent().build() : Response.status(Response.Status.NOT_FOUND).build();
    }
}