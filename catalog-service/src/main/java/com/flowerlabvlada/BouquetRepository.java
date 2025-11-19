package com.flowerlabvlada;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import com.flowerlabvlada.Bouquet;

@ApplicationScoped
public class BouquetRepository implements PanacheRepository<Bouquet> {

}