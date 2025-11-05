package com.flowerlabvlada.clients;

import com.flowerlabvlada.models.Bouquet;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "com.flowerlabvlada.clients.CatalogClient")
@Path("/bouquets") // Базовий шлях до catalog-service
public interface CatalogClient {

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    Bouquet getBouquetById(@PathParam("id") Long id);
}
