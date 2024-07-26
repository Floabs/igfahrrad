package at.igfahrrad;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BicycleRepository implements PanacheRepository<Bicycle> {
    
}
