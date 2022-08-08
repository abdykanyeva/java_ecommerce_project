package ecommerce.checkout;

import ecommerce.Customer;

public interface CheckoutService {

    boolean checkout(Customer customer, Double totalAmount);


}
