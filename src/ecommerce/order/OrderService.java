package ecommerce.order;

import ecommerce.Cart;

public interface OrderService  {

    String placeOrder(Cart cart);

}
