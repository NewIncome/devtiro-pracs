/**
 * Class that habldes placing orders
 * • When an order is placed, the customer's payment needs to be processed
 *   SO, OrderService is dependentOn / CoupledTo: Stripe/PayPalPaymentService
 */
package com.jalfredev.springstore01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Annotation so Spring takes charge of managing implementations of this class
@Service
public class OrderService {
  //c2.Contructor Injection requires a -field- for the parameter to be injected
  private PaymentService paymentService; //Object that implements the PaymentService Interface

  public OrderService() {}

  //c1.The recommended way to inject a dependency into a class
  //is via a -Constructor-
  /*
   * The @Autowired annotation is needed only if there are more than 1 constructor
   * *This is to "autowire"/match the class with it's dependencies
   */
  //@Autowired
  public OrderService(PaymentService paymentService) {
    this.paymentService = paymentService;
  }
  /*
   * With the following extra constructor, if we don'e use the above @Autowired, we'll get an error
   public OrderService(PaymentService paymentService, int a) {}
   * 
   */

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
