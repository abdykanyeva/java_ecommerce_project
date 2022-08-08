package ecommerce.checkout;

import ecommerce.Customer;
import ecommerce.StaticConstants;
import ecommerce.balance.Balance;
import ecommerce.balance.CustomerBalance;

import java.util.UUID;


public class CustomerBalanceCheckoutServiceImpl implements CheckoutService{


    @Override
    public boolean checkout(Customer customer, Double totalAmount) {
        CustomerBalance customerBalance = findCustomerBalance(customer.getId());
        double finalBalance = customerBalance.getBalance() - totalAmount;
        if(finalBalance > 0){
            customerBalance.setBalance(finalBalance);
            return true;
        }
        return false;
    }



    private CustomerBalance findCustomerBalance(UUID id) {

        for(Balance customerBalance: StaticConstants.CUSTOMER_BALANCE_LIST){
            if(customerBalance.getCustomerId().toString().equals(id.toString())){
                return (CustomerBalance) customerBalance;
            }
        }
        CustomerBalance customerBalance = new CustomerBalance(UUID.randomUUID(), 0d);
        StaticConstants.CUSTOMER_BALANCE_LIST.add(customerBalance);

        return customerBalance;

    }
}
