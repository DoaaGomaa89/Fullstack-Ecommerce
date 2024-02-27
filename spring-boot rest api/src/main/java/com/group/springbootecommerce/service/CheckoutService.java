package com.group.springbootecommerce.service;

import com.group.springbootecommerce.dto.PurchaseResponse;
import com.group.springbootecommerce.dto.Purchase;

public interface CheckoutService {


    PurchaseResponse placeOrder(Purchase purchase);
}
