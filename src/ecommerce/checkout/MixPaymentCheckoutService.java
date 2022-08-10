package ecommerce.checkout;

import ecommerce.Customer;
import ecommerce.StaticConstants;
import ecommerce.balance.Balance;
import ecommerce.balance.CustomerBalance;
import ecommerce.balance.GiftCardBalance;

import java.util.UUID;


public class MixPaymentCheckoutService implements CheckoutService{


    @Override
    public boolean checkout(Customer customer, Double totalAmount) {
        try {

            GiftCardBalance giftCardBalance = findGiftCardBalance(customer.getId());


            // 300 giftCard balance
            // 450 customer balance
            // 600 cart


            // 300 - 600 = -300, so we will go to else
            // mixed balance
            final double giftBalance = giftCardBalance.getBalance() - totalAmount;

            if (giftBalance > 0) {
                giftCardBalance.setBalance(giftBalance);
                return true;
            } else {
                CustomerBalance customerBalance = findCustomerBalance(customer.getId());
                final double mixBalance = customerBalance.getBalance() + giftCardBalance.getBalance() - totalAmount;
                if (mixBalance > 0) {
                    giftCardBalance.setBalance(0d);
                    customerBalance.setBalance(mixBalance);
                    return true;
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());


        }
        return false;




    }

    private CustomerBalance findCustomerBalance(UUID id) {

        for(Balance customerBalance: StaticConstants.CUSTOMER_BALANCE_LIST){
            if(customerBalance.getCustomerId().toString().equals(id.toString())){
                return(CustomerBalance) customerBalance;
            }
        }
        CustomerBalance customerBalance = new CustomerBalance(UUID.randomUUID(), 0d);
        StaticConstants.CUSTOMER_BALANCE_LIST.add(customerBalance);
        return customerBalance;
    }

    private GiftCardBalance findGiftCardBalance(UUID id) {

        for(Balance giftCardBalance: StaticConstants.GIFT_CARD_BALANCE_LIST){

            if(giftCardBalance.getCustomerId().toString().equals(id.toString())){
                return (GiftCardBalance) giftCardBalance;
            }
        }
        GiftCardBalance giftCardBalance = new GiftCardBalance(UUID.randomUUID(), 0d);
        StaticConstants.GIFT_CARD_BALANCE_LIST.add(giftCardBalance);
        return giftCardBalance;
    }
}
