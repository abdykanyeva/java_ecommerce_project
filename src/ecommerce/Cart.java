package ecommerce;

import java.util.Map;
import java.util.UUID;

public class Cart {

    Customer customer;
    UUID id;
    Map<Product, Integer> productMap;


    public Cart(Customer customer) {
        this.customer = customer;
    }


    public Cart(Customer customer, UUID id, Map<Product, Integer> productMap) {
        this.customer = customer;
        this.id = id;
        this.productMap = productMap;
    }

    public Double calculateCartTotalAmount(){
        double totalAmount = 0;
        for(Product eachProduct: productMap.keySet()){
            totalAmount += eachProduct.getPrice() * productMap.get(eachProduct);
        }
        return totalAmount;
    }


    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Map<Product, Integer> getProductMap() {
        return productMap;
    }

    public void setProductMap(Map<Product, Integer> productMap) {
        this.productMap = productMap;
    }
}
