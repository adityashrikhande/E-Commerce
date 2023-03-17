package com.example.ecommerce;

public class Order {

    public static boolean placeOrder(Customer customer, Product product){
        try{
            //INSERT INTO orders(customer_id, product_id, status) VALUES (1, 1, 'ordered');
            String placeOrder = "INSERT INTO orders(customer_id, product_id, status) VALUES (" + customer.getId() + "," + product.getId() + ", 'ordered')";
            DatabaseConnection dbConn = new DatabaseConnection();
            return dbConn.insertUpdate(placeOrder);
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
