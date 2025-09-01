package com.ecom.service;

import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.dao.CustomerRepository;
import com.ecom.dto.Purchase;
import com.ecom.dto.PurchaseResponse;
import com.ecom.entity.Customer;
import com.ecom.entity.Order;
import com.ecom.entity.OrderItem;

import jakarta.transaction.Transactional;

@Service
public class CheckoutServiceImpl implements CheckoutService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	@Transactional
	public PurchaseResponse placeOrder(Purchase purchase) {
		// retrieve the order info from dto
		Order order = purchase.getOrder();

		// generate tracking number
		String orderTrackingNumber = generateOrderTrackingNumber();
		order.setOrderTrackingNumber(orderTrackingNumber);

		// populate order with orderItems

		Set<OrderItem> orderItems = purchase.getOrderItems();
		orderItems.forEach(item -> order.add(item));

		// populate order with billingAddress and shippingAddress

		order.setBillingAddress(purchase.getBillingAddress());
		order.setShippingAddress(purchase.getShippingAddress());

		// populate customer with order\

		Customer customer = purchase.getCustomer();
		customer.add(order);

		// save to the database
		customerRepository.save(customer);
		// return a response

		return new PurchaseResponse(orderTrackingNumber);

	}

	private String generateOrderTrackingNumber() {
		// generate a random UUID number (UUID Version-4)

		return UUID.randomUUID().toString();

	}

}
