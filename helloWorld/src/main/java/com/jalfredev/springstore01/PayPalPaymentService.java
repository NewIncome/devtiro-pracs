/**
 * Dependency for OrderService
 * or Implementation Class
 */
package com.jalfredev.springstore01;

import org.springframework.stereotype.Service;

/* This annotation is needed so OrderService now managed by Spring
 * can find this as a bean also, also to be managed by Spring
*/
@Service
public class PayPalPaymentService implements PaymentService {
  @Override
  public void processPayment(double amount) {
    System.out.println("PAYPAL");
    System.out.println("Amount: " + amount);
  }
}
