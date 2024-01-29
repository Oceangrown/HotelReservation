package service;

import model.Customer;
import model.Reservation;

import java.util.*;



public class CustomerService {
    private static CustomerService customerService_instance = null;

    private static final Map<String, Customer> customers = new HashMap<>();
    private CustomerService() {
    }
    //https://www.geeksforgeeks.org/singleton-class-java/
    public static CustomerService getInstance(){
        if (customerService_instance == null)
            customerService_instance = new CustomerService();

        return customerService_instance;
    }



    public void addCustomer(String email, String firstName, String lastName){
        Customer customer = new Customer(firstName, lastName, email);
        customers.put(email, customer);
    }

    public Customer getCustomer(String customerEmail){
        return customers.get(customerEmail);

    }

    public Collection<Customer> getAllCustomers(){
        return customers.values();
    }
}
