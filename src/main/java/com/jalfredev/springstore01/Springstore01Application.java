package com.jalfredev.springstore01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Springstore01Application {

	public static void main(String[] args) {
		// SpringApplication.run(Springstore01Application.class, args);
    /* comented out the above line,
     *  to run a Console application instead of runnig a Spring application
     *   this to test out OrderService class, and
     *  to do DependencyInjection, and inject:
     *   Stripe and Paypal paymentServices to OrderService
     * Also to be run with command:
     *  mvn spring-boot:run
    */
    /*
     * Here we are "Injecting" the "Dependency" Stripe and
     *  Paypal payment services to the constructor of OrderService
     */
    var orderService1 = new OrderService(new StripePaymentService());
    orderService1.placeOrder();
    
    var orderService2 = new OrderService(new PayPalPaymentService());
    /* The next line is for SetterInjection 'instead-of' ConstructorInjection
    orderService2.setPaymentService(new PayPalPaymentService());
    */
    orderService2.placeOrder();
	}

}
