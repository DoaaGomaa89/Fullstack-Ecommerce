package com.group.springbootecommerce.dto;

import com.group.springbootecommerce.entity.Customer;
import com.group.springbootecommerce.entity.Address;
import com.group.springbootecommerce.entity.Order;
import com.group.springbootecommerce.entity.OrderItem;
import lombok.Data;

import java.util.Set;

@Data
public class Purchase {

    private Customer customer;

    private Address shippingAddress;

    private Address billingAddress;

    private Order order;

    private Set<OrderItem> orderItems;
}
