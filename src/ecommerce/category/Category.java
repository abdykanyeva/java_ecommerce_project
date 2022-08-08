package ecommerce.category;

import java.time.LocalDate;
import java.util.UUID;

public abstract class Category {

    private UUID id;
    private String name;


    public Category(UUID id, String name) {
        this.id = id;
        this.name = name;
    }


    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract LocalDate deliveryDate();

    public String generateCategoryCode(){
        return id.toString().substring(0, 8).concat(name.substring(0, 2));

    }
}
