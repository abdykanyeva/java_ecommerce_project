package ecommerce;

import ecommerce.balance.Balance;
import ecommerce.balance.CustomerBalance;
import ecommerce.balance.GiftCardBalance;
import ecommerce.category.Category;
import ecommerce.category.Electronic;
import ecommerce.category.Furniture;
import ecommerce.category.SkinCare;
import ecommerce.discount.AmountBasedDiscount;
import ecommerce.discount.Discount;
import ecommerce.discount.RateBasedDiscount;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ecommerce.StaticConstants.*;

public class DataGenerator {

    public static void createCustomer() {


        Address address1Customer1 = new Address("1935", "Magnolia Crest Ln", "Fountain Lk", "13456", "VR");
        Address address2Customer1 = new Address("1789", "Ashford Crest Ln", "Fountain Lk", "13456", "TX");
        Address address1Customer2 = new Address("7869", "Hollow", "Fountain Lk", "RD345", "CA");


        List<Address> addressListCustomer1 = new ArrayList<>();
        addressListCustomer1.add(address1Customer1);
        addressListCustomer1.add(address2Customer1);

        Customer customer1 = new Customer(UUID.randomUUID(), "ozzy", "ozzy@gmail.com");
        Customer customer2 = new Customer(UUID.randomUUID(), "mike", "mike@gmail.com", addressListCustomer1);

        CUSTOMER_LIST.add(customer1);
        CUSTOMER_LIST.add(customer2);


    }

    public static void createCategory(){
        Category electronic = new Electronic(UUID.randomUUID(), "Electronic");
        Category furniture = new Furniture(UUID.randomUUID(), "Furniture");
        Category skinCare = new SkinCare(UUID.randomUUID(), "SkinCare");

        CATEGORY_LIST.add(electronic);
        CATEGORY_LIST.add(furniture);
        CATEGORY_LIST.add(skinCare);

    }

    public static void createProduct(){
        Product product1 = new Product(UUID.randomUUID(),"TV", 155.78, 10, 10, CATEGORY_LIST.get(0).getId());
        Product product2 = new Product(UUID.randomUUID(), "Sofa", 400.67, 7, 7, CATEGORY_LIST.get(1).getId());
        Product product3 = new Product(UUID.randomUUID(), "Face cream", 34.89, 17, 17, CATEGORY_LIST.get(2).getId());
        Product product4 = new Product(UUID.randomUUID(), "Milk", 2.0, 12, 12, UUID.randomUUID());

        PRODUCT_LIST.add(product1);
        PRODUCT_LIST.add(product2);
        PRODUCT_LIST.add(product3);
        PRODUCT_LIST.add(product4);
    }

    public static void createBalance(){

        Balance customerBalance = new CustomerBalance(CUSTOMER_LIST.get(0).getId(), 450.00);
        Balance giftCardBalance = new GiftCardBalance(CUSTOMER_LIST.get(1).getId(), 100.00);

        CUSTOMER_BALANCE_LIST.add(customerBalance);
        GIFT_CARD_BALANCE_LIST.add(giftCardBalance);


    }

    public static void createDiscount(){

        Discount amountBasedDiscount = new AmountBasedDiscount(UUID.randomUUID(), "Buy 250 free 50", 250.00, 50.0 );
        Discount rateBasedDiscount = new RateBasedDiscount(UUID.randomUUID(), "Buy 500 free 15 %", 500.0, 15.0);
        DISCOUNT_LIST.add(amountBasedDiscount);
        DISCOUNT_LIST.add(rateBasedDiscount);
    }





}
