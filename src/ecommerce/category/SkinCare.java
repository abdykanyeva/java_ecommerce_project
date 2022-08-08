package ecommerce.category;

import java.time.LocalDate;
import java.util.UUID;

public class SkinCare extends Category{


    public SkinCare(UUID id, String name) {
        super(id, name);
    }

    @Override
    public LocalDate deliveryDate() {
        return LocalDate.now();
    }
}
