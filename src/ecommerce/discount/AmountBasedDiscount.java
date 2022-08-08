package ecommerce.discount;

import java.util.UUID;

public class AmountBasedDiscount extends Discount{

    private Double discountAmount;

    public AmountBasedDiscount(UUID discountId, String name, Double thresholdAmount, Double discountAmount) {
        super(discountId, name, thresholdAmount);
        this.discountAmount = discountAmount;
    }

    @Override
    public Double finalAmountAfterDiscount(Double amount) {
        return amount - discountAmount;
    }


    public Double getDiscountAmount() {
        return discountAmount;
    }
}
