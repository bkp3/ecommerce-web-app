package com.ecom.service;

import java.util.*;

import com.ecom.dto.PaymentInfo;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    public CheckoutServiceImpl(@Value("${stripe.key.secret}") String secretKey){
        //initialize stripe API with secret key
        Stripe.apiKey=secretKey;

    }

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

    @Override
    public PaymentIntent createPaymentIntent(PaymentInfo paymentInfo) throws StripeException {
        List<String> paymentMethodTypes=new ArrayList<>();
        paymentMethodTypes.add("card");
        Map<String,Object>params=new HashMap<>();
        params.put("amount",paymentInfo.getAmount());
        params.put("currency",paymentInfo.getCurrency());
        params.put("payment_method_types",paymentMethodTypes);
        params.put("description","Ecommerce Purchase");
        params.put("receipt_email",paymentInfo.getReceiptEmail());

        return PaymentIntent.create(params);
    }

    private String generateOrderTrackingNumber() {
		// generate a random UUID number (UUID Version-4)

		return UUID.randomUUID().toString();

	}

}
