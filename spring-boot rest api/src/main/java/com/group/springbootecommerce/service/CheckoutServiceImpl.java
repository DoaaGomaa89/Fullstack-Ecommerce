package com.group.springbootecommerce.service;

import com.group.springbootecommerce.dao.CustomerRepository;
import com.group.springbootecommerce.dto.PurchaseResponse;
import com.group.springbootecommerce.entity.Customer;
import com.group.springbootecommerce.dto.Purchase;
import com.group.springbootecommerce.entity.Order;
import com.group.springbootecommerce.entity.OrderItem;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService{

    private CustomerRepository customerRepository;

    @Autowired
    public CheckoutServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        //retrieve the order info from dto
        Order order = purchase.getOrder();

        //generate tracking number
        String orderTrackingNumber = generateOrderTrackingNumber();
        order.setOrderTrackingNumber(orderTrackingNumber);

        //populate order with orderItems
        Set<OrderItem> orderItems = purchase.getOrderItems();
        for(OrderItem item: orderItems){
            order.add(item);
        }

        //populate order with billingAddress and shippingAddress
        order.setBillingAddress(purchase.getBillingAddress());
        order.setShippingAddress(purchase.getShippingAddress());

        //populate customer with order
        Customer customer = purchase.getCustomer();

        //check if this is an existing customer based on email add
        String theEmail = customer.getEmail();
        Customer customerFromDb = customerRepository.findByEmail(theEmail);

        if(customerFromDb != null){
            customer = customerFromDb;
        }

        customer.add(order);

        //save to the database
        customerRepository.save(customer);

        //return a response

        return new PurchaseResponse(orderTrackingNumber);
    }

    //we want a unique id that is hard to guess and random
    private String generateOrderTrackingNumber() {

        //generate a random UUID number (UUID version-4)
        return UUID.randomUUID().toString();
    }
}
