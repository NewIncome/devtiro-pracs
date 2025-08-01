/**
 * Class that habldes placing orders
 * • When an order is placed, the customer's payment needs to be processed
 *   SO, OrderService is dependentOn / CoupledTo: Stripe/PayPalPaymentService
 */
package com.jalfredev.springstore01;

public class OrderService {
  //c2.Contructor Injection requires a -field- for the parameter to be injected
  private PaymentService paymentService; //Object that implements the PaymentService Interface

  //c1.The recommended way to inject a dependency into a class
  //is via a -Constructor-
  public OrderService(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  public void placeOrder() {
    /* This is Tight coupling, meaning OrderS. is too DependentOn StripeP.S.
     * which is fixed with Interfaces
    */
    //var paymentService = new StripePaymentService();
    /*
     * The above line ins't used anymore because now we are using
     *  a private Field and a constructor for ConstructorInjection
     */
    //c3.And to use it we call the method from the object saved in the field
    paymentService.processPayment(10);
  }

  //Getters and Setters, by convention, go at the bottom of the class
  public void setPaymentService(PaymentService paymentService) {
    this.paymentService = paymentService;
  }
}
