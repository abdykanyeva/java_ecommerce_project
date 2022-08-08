package ecommerce;

import ecommerce.category.Category;

import java.time.LocalDate;
import java.util.UUID;

public class Product {

    private UUID id;
    private String name;
    private Double price;
    private Integer stock;
    private Integer remainingStock;
    private UUID categoryId;


    public Product(UUID id, String name, Double price, Integer stock, Integer remainingStock, UUID categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.remainingStock = remainingStock;
        this.categoryId = categoryId;
    }


    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public Integer getRemainingStock() {
        return remainingStock;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public String getProductCategoryName() throws Exception{

        for(Category category: StaticConstants.CATEGORY_LIST){
            if(category.getId().toString().equals(getCategoryId().toString())){
                return category.getName();
            }

        }
        throw new Exception("Category not found, "+ getName());

    }

    public LocalDate getDeliveryDueDate() throws Exception {
        for(Category category: StaticConstants.CATEGORY_LIST){
            if(category.getId().toString().equals(getCategoryId().toString())){
                return category.deliveryDate();
            }
        }
        throw new Exception("Category not found");

    }


    public void setRemainingStock(Integer remainingStock) {
        this.remainingStock = remainingStock;
    }
}