package ecommerce.discount;

import java.util.UUID;

public class RateBasedDiscount extends Discount{
    private Double rateBasedDiscount;

    public RateBasedDiscount(UUID discountId, String name, Double thresholdAmount, Double rateBasedDiscount) {
        super(discountId, name, thresholdAmount);
        this.rateBasedDiscount = rateBasedDiscount;
    }

    @Override
    public Double finalAmountAfterDiscount(Double amount) {
        return amount - (amount * rateBasedDiscount / 100);
    }


    public Double getRateBasedDiscount() {
        return rateBasedDiscount;
    }
}
