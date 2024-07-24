package at.igfahrrad;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Bicycle {

    @Id
    @GeneratedValue
    public Long id;
    public String name = "Velo";
    public String description = "";
    public BigDecimal price = BigDecimal.ZERO;
    public String type = "";
    public String image = "";
}
