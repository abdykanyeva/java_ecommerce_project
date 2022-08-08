package ecommerce.category;

import java.time.LocalDate;
import java.util.UUID;

public class Electronic extends Category{
    public Electronic(UUID id, String name) {
        super(id, name);
    }

    @Override
    public LocalDate deliveryDate() {
        return LocalDate.now().plusDays(4);
    }

    @Override
    public String generateCategoryCode(){
        return "EL- "+getId().toString().substring(0, 2).concat(getName());
    }
}
