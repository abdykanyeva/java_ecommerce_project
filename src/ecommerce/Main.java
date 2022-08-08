package ecommerce;

import ecommerce.balance.Balance;
import ecommerce.balance.CustomerBalance;
import ecommerce.balance.GiftCardBalance;
import ecommerce.category.Category;
import ecommerce.discount.Discount;
import ecommerce.order.Order;
import ecommerce.order.OrderService;
import ecommerce.order.OrderServiceImpl;



import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;


public class Main {

    public static void main(String[] args) {

        DataGenerator.createCustomer();
        DataGenerator.createCategory();
        DataGenerator.createProduct();
        DataGenerator.createBalance();
        DataGenerator.createDiscount();

        Scanner scan = new Scanner(System.in);
        System.out.println("Select customer: ");
        for (int i = 0; i < StaticConstants.CUSTOMER_LIST.size(); i++) {
            System.out.println("Type " + i + " for customer: " + StaticConstants.CUSTOMER_LIST.get(i).getUserName());
        }
        Customer customer = StaticConstants.CUSTOMER_LIST.get(scan.nextInt());
        Cart cart = new Cart(customer);

        while (true) {

            System.out.println("What would you like to do? Just type id for selection");

            for (int i = 0; i < menuItems().length; i++) {
                System.out.println(i + ": " + menuItems()[i]);
            }
            int menuSelection = scan.nextInt();
            switch (menuSelection) {
                case 0:
                    for (Category category : StaticConstants.CATEGORY_LIST) {
                        System.out.println("Category code: " + category.generateCategoryCode() + " Category name: " + category.getName());
                    }
                    break;
                case 1: // product name, product category


                    try {
                        for (Product product : StaticConstants.PRODUCT_LIST) {
                            System.out.println("Product name: " + product.getName() + "Product " + " Product category name: " + product.getProductCategoryName());
                        }
                    } catch (Exception e) {
                        System.out.println("Product could not be printed because category not found for product name: " + e.getMessage().split(",")[1]);
                    }

                    break;
                case 2:
                    for (Discount discount : StaticConstants.DISCOUNT_LIST) {
                        System.out.println("Discount name: " + discount.getName() + ": " + " threshold amount: " + discount.getThresholdAmount());
                    }

                    break;
                case 3:
                    // see balance

                    CustomerBalance cBalance = findCustomerBalance(customer.getId());
                    GiftCardBalance gBalance = findGiftCardBalance(customer.getId());

                    Double totalBalance = cBalance.getBalance() + gBalance.getBalance();
                    System.out.println("Total balance: " + totalBalance);
                    System.out.println("Gift Card Balance: " + gBalance.getBalance());
                    System.out.println("Customer balance: " + cBalance.getBalance());


                    break;
                case 4:
                    CustomerBalance customerBalance = findCustomerBalance(customer.getId());
                    GiftCardBalance giftBalance = findGiftCardBalance(customer.getId());

                    System.out.println("Which account would you like to add?");
                    System.out.println("Type 1 for Customer balance: "+customerBalance.getBalance());
                    System.out.println("Type 2 for Gift Card Balance: "+ giftBalance.getBalance());
                    int customerSelection = scan.nextInt();
                    System.out.println("How much you would like to add? ");
                    double additionalAmount = scan.nextInt();

                    switch(customerSelection){
                        case 1:
                            customerBalance.additionalAmount(additionalAmount);
                            System.out.println("New Customer Balance: "+ customerBalance.getBalance());

                            break;
                        case 2:
                            giftBalance.additionalAmount(additionalAmount);
                            System.out.println("New gift card balance: "+ giftBalance.getBalance());
                            break;
                    }

                    break;
                case 5:
                    Map<Product, Integer> map = new HashMap<>();
                    cart.setProductMap(map);

                    while(true){

                        System.out.println("Which product you want to add to your cart. For exit product selection Type: exit");
                        for(Product product: StaticConstants.PRODUCT_LIST){
                            try {
                                System.out.println("id: "+ product.getId() +" price: "+ product.getProductCategoryName() +
                                      "stock: "+  product.getRemainingStock() +"product deliveryDue date: "+product.getDeliveryDueDate() );
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        String productId = scan.next();

                        try {
                            Product product = findProductById(productId);
                            if(!putItemToCartIfStockAvailable(cart, product)){
                                System.out.println("Stock is insufficient. Please try again");
                                continue;

                            }
                        } catch (Exception e) {
                            System.out.println("Product does not exist.Please try again");
                            continue;
                        }
                        System.out.println("Do you want to add more product. Type Y for adding more, N for exit");
                        String decision = scan.next();
                        if(!decision.equals("Y")){
                            break;
                        }

                    }
                    System.out.println("Seems there are discounts options. " +
                            "Do  you want to apply to your cart if it is applicable. For no discount type no");
                    for(Discount discount: StaticConstants.DISCOUNT_LIST){
                        System.out.println("Discount id: "+discount.getDiscountId() + "discount name: "+ discount.getName());
                    }

                    String discountId = scan.next();

                    if(!discountId.equals("no")){
                        try {
                            Discount discount = findDiscountById(discountId);
                            if(discount.decideDiscountIsApplicableToCart(cart)){
                                cart.setId(discount.getDiscountId());

                            }
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }

                    OrderService orderService = new OrderServiceImpl();
                    String result = orderService.placeOrder(cart);
                    if(result.equals("Order has been placed successfully")){
                        System.out.println("Order is successful");
                        updateProductStock(cart.getProductMap());
                        cart.setProductMap(new HashMap<>());
                        cart.setId(null);
                    }else {
                        System.out.println(result);
                    }


                    break;
                case 6:
                    System.out.println("Your cart: ");

                    if(!cart.getProductMap().keySet().isEmpty()){
                        for(Product product: cart.getProductMap().keySet()){
                            System.out.println("Product name: "+ product.getName() + " count: "+ cart.getProductMap().get(product));
                        }
                    }else{
                        System.out.println("Your cart is empty");
                    }
                    break;
                case 7:
                    printOrderByCustomerId(customer.getId());
                    break;
                case 8:
                    printAddressByCustomerId(customer);
                    break;
                case 9:
                    System.exit(0);
            }

        }


    }

    private static void printAddressByCustomerId(Customer customer) {

        for(Address address : customer.getAddress()){
            System.out.println("Street name: "+ address.getStreetName() + "Street number: "+ address.getStreetNumber()+
                    " ZipCode: "+ address.getZipCode() + " State: "+ address.getState());
        }
    }


    private static void printOrderByCustomerId(UUID customerId) {

        for(Order order: StaticConstants.ORDER_LIST){
            if(order.getCustomerId().toString().equals(customerId.toString())){
                System.out.println("Order status: "+order.getOrderStatus() + "Order amount: "+ order.getPaidAmount()+
                        " order date: "+ order.getOrderDate());
            }
        }
    }

    private static void updateProductStock(Map<Product, Integer> productMap) {

        for(Product product: productMap.keySet()){
            product.setRemainingStock(product.getRemainingStock() - productMap.get(product));
        }

    }

    public static Discount findDiscountById(String discountId) throws Exception {

        for(Discount discount: StaticConstants.DISCOUNT_LIST){
            if(discount.getDiscountId().toString().equals(discountId)){
                return discount;
            }
        }
        throw new Exception("Discount not found");

    }

    private static boolean putItemToCartIfStockAvailable(Cart cart, Product product) {

        System.out.println("Please provide product count: ");
        Scanner scanner = new Scanner(System.in);
        int productCount = scanner.nextInt();

        Integer cartCount = cart.getProductMap().get(product);
        if(cartCount != null && product.getRemainingStock() > cartCount + productCount){
            cart.getProductMap().put(product, cartCount+productCount);
            return true;
        }else if(product.getRemainingStock() > productCount){
            cart.getProductMap().put(product, productCount);
            return true;
        }
        return false;

    }

    private static Product findProductById(String productId) throws Exception {

        for(Product product: StaticConstants.PRODUCT_LIST){
            if(product.getId().toString().equals(productId)){
                return product;
            }
        }
        throw new Exception("Product not found");
    }

    private static CustomerBalance findCustomerBalance(UUID customerId) {

        for (Balance eachCustomerBalance : StaticConstants.CUSTOMER_BALANCE_LIST) {
            if (eachCustomerBalance.getCustomerId().toString().equals(customerId.toString())) {
                return (CustomerBalance) eachCustomerBalance;

            }
        }
        CustomerBalance customerBalance = new CustomerBalance(customerId, 0d);
        StaticConstants.CUSTOMER_BALANCE_LIST.add(customerBalance);
        return customerBalance;
    }

    private static GiftCardBalance findGiftCardBalance(UUID customerId){

        for(Balance eachCustomerGiftBalance: StaticConstants.GIFT_CARD_BALANCE_LIST){
            if(eachCustomerGiftBalance.getCustomerId().toString().equals(customerId.toString())){
                return (GiftCardBalance) eachCustomerGiftBalance;
            }
        }

        GiftCardBalance giftCardBalance = new GiftCardBalance(customerId, 0d);
        StaticConstants.GIFT_CARD_BALANCE_LIST.add(giftCardBalance);
        return giftCardBalance;
    }

    private static String[] menuItems () {
            return new String[]{"List categories", "List Product", "List Discounts",
                    "See Balance", "Add Balance", "Place an order",
                    "See Cart", "See order details", "See your addresses", "Close App"};

        }



}
