/**
 * Interface
 * • Used to de-couple OrderService from it's dependencies(Stripe & Paypal)
 * • Here we define the capabilities of the ImplementationClasses(the Dependencies)
 */
package com.jalfredev.springstore01;

public interface PaymentService {
  void processPayment(double amount);
}
