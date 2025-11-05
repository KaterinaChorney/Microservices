package com.flowerlabvlada;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/bouquets")
@Produces(MediaType.APPLICATION_JSON)
public class BouquetResource {

    @Inject
    BouquetRepository repository;

    @GET
    public List<Bouquet> getAllBouquets() {
        return repository.listAll();
    }

    @GET
    @Path("/{id}")
    public Response getBouquetById(@PathParam("id") Long id) {
        return repository.findBouquetById(id)
                .map(bouquet -> Response.ok(bouquet).build()) // 200
                .orElse(Response.status(Response.Status.NOT_FOUND).build()); //404
    }
}
