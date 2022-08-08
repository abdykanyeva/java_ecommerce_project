package ecommerce.balance;

import java.util.UUID;

public class GiftCardBalance extends Balance{


    public GiftCardBalance(UUID customerId, Double balance) {
        super(customerId, balance);
    }

    @Override
    public Double additionalAmount(Double additionalAmount) {
        double promotionAmount = additionalAmount* 10/100;
        setBalance(getBalance() + additionalAmount + promotionAmount);
        return getBalance();
    }
}
