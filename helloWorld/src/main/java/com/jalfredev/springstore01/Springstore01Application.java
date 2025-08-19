package com.jalfredev.springstore01;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Springstore01Application {

	public static void main(String[] args) {
    /*
     * • This .run method returns an object of type ApplicationContext
     * • ApplicationContext is our IOC container,
     *   so it can be stored as an Application Context object
     * • That object has the method getBean(), to get an object/bean that is managed by Spring
     */
		ConfigurableApplicationContext context = SpringApplication.run(Springstore01Application.class, args);
    var orderService = context.getBean(OrderService.class); //we want Spring to manage objects of type OrderService
    orderService.placeOrder();
    //This Spring takes care, instead of us manually creating objects and injecting dependencies
    //But our classes need to use an Annotation (@Component | @Service)

    /* comented out the above line (SpringApplication.run),
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
