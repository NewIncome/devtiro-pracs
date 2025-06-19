package com.jalfredev.springstore01;

public class OrderService {
  private PaymentService paymentService;

  public OrderService(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  public void placeOrder() {
    //var paymentService = new StripePaymentService(); //This is Tight coupling, which is fixed with Interfaces
    /*
     * The above line ins't used anymore because now we are using
     *  a private Field and a constructor for ConstructorInjection
     */
    paymentService.processPayment(10);
  }
}
