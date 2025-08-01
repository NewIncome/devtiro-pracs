package com.jalfredev.springstore01;

public class OrderService {
  //c2.Contructor injection requires a -field- for the parameter to be injected
  private PaymentService paymentService;

  //c1.The recommended way to inject a dependency into a class
  //is via a -Constructor-
  public OrderService(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  public void placeOrder() {
    //var paymentService = new StripePaymentService(); //This is Tight coupling, which is fixed with Interfaces
    /*
     * The above line ins't used anymore because now we are using
     *  a private Field and a constructor for ConstructorInjection
     */
    //c3.And to use it we call the method from the object saved in the field
    paymentService.processPayment(10);
  }
}
