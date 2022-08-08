package ecommerce.category;

import java.time.LocalDate;
import java.util.UUID;

public class Furniture extends Category{
    public Furniture(UUID id, String name) {
        super(id, name);
    }

    @Override
    public LocalDate deliveryDate() {
        return LocalDate.now().plusDays(1);
    }
}
