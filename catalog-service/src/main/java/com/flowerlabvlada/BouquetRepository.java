package com.flowerlabvlada;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class BouquetRepository {
    private List<Bouquet> bouquets = List.of(
            new Bouquet(1L, "Букет \"Романтичний\"", "Букет із кущових троянд", 1200.00, 15),
            new Bouquet(2L, "Букет \"З троянд\"", "Букет із троянд сорту Експлорер", 1100.00, 10),
            new Bouquet(3L, "Букет \"Осінній\"", "Букет з хризантем,жоржин та гербер", 950.00, 10),
            new Bouquet(4L, "Композиція \"Ніжна\"", "Композиція із троянд,гортензій та орхідей", 1800.00, 10),
            new Bouquet(5L, "Композиція \"Екзотична\"", "Мікс екзотичних квітів у коробці", 1500.00, 15)
    );

    public Optional<Bouquet> findBouquetById(Long id) {
        return bouquets.stream()
                .filter(b -> b.id().equals(id))
                .findFirst();
    }

    public List<Bouquet> listAll() {
        return bouquets;
    }
}