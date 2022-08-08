package ecommerce.order;

import ecommerce.Cart;

import ecommerce.StaticConstants;
import ecommerce.checkout.CheckoutService;
import ecommerce.checkout.CustomerBalanceCheckoutServiceImpl;
import ecommerce.checkout.MixPaymentCheckoutService;
import ecommerce.discount.Discount;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.UUID;


public class OrderServiceImpl implements OrderService{


    @Override
    public String placeOrder(Cart cart) {
        double amountAfterDiscount = cart.calculateCartTotalAmount();

        if(cart.getId() != null){


            try {
                Discount discount = findDiscountById(cart.getId());
                amountAfterDiscount = discount.finalAmountAfterDiscount(amountAfterDiscount);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Which payment option you would like to choose, Type 1: customer balance, Type 2: Mix(gift card + customer balance");
        int paymentType = scanner.nextInt();
        boolean checkOutResult = false;

        switch(paymentType){
            case 1:
                CheckoutService customerBalanceCheckoutService = new CustomerBalanceCheckoutServiceImpl();
                checkOutResult = customerBalanceCheckoutService.checkout(cart.getCustomer(), amountAfterDiscount);
                break;
            case 2:
                CheckoutService customerMixCheckOutService = new MixPaymentCheckoutService();
                checkOutResult = customerMixCheckOutService.checkout(cart.getCustomer(), amountAfterDiscount);
                break;
        }

        if(checkOutResult){
            Order order = new Order(UUID.randomUUID(), LocalDate.now(), cart.calculateCartTotalAmount(), amountAfterDiscount,
                    cart.calculateCartTotalAmount() - amountAfterDiscount, cart.getCustomer().getId(),
                    "Placed: ", cart.getProductMap().keySet());

            StaticConstants.ORDER_LIST.add(order);
            return "Order has been placed successfully";

        }else{
            return "Balance is insufficient. Please add money to your one of balance and try again";
        }




    }

    private static Discount findDiscountById(UUID discountId) throws Exception {
        for(Discount discount: StaticConstants.DISCOUNT_LIST){
            if(discount.getDiscountId().toString().equals(discount.toString())){
                return discount;
            }
        }
        throw new Exception("Discount count not find");
    }
}
