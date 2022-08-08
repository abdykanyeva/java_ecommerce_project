package ecommerce.balance;

import java.util.UUID;

public class CustomerBalance extends Balance{


    public CustomerBalance(UUID customerId, Double balance) {
        super(customerId, balance);
    }

    @Override
    public Double additionalAmount(Double additionalAmount) {
        setBalance(getBalance() + additionalAmount);
        return getBalance();
    }
}
