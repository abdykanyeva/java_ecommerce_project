package ecommerce.balance;

import java.util.UUID;

public abstract class Balance {

    private UUID customerId;
    private Double balance;



    public Balance(UUID customerId, Double balance) {
        this.customerId = customerId;
        setBalance(balance);  // should we leave it with this.balance = balance ?? will it update??
    }



    public abstract Double additionalAmount(Double additionalAmount);

    public UUID getCustomerId() {
        return customerId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance){
        this.balance = balance;

    }
}
