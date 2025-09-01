package com.ecom.service;

import com.ecom.dto.Purchase;
import com.ecom.dto.PurchaseResponse;

public interface CheckoutService {

	PurchaseResponse placeOrder(Purchase purchase);

}
