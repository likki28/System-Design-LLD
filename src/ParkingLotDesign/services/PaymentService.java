package ParkingLotDesign.services;
import ParkingLotDesign.adapter.PaymentGatewayAdapter;
import ParkingLotDesign.adapter.RazorpayAdapter;
import ParkingLotDesign.adapter.StripeAdapter;
import ParkingLotDesign.domain.Payment;
import ParkingLotDesign.repository.PaymentRepository;

import java.util.*;

public class PaymentService {
    private PaymentRepository paymentRepository;
    private PaymentGatewayAdapter defaultGateway;

    public PaymentService(PaymentRepository paymentRepository){
        this.paymentRepository=paymentRepository;
        this.defaultGateway=new RazorpayAdapter();
        System.out.println("[SERVICE] PaymentService initialized with default gateway: Razorpay");
    }

    public boolean processPayment(UUID ticketId, double amount){

        Payment payment=new Payment(ticketId, amount, Payment.PaymentGateway.RAZORPAY);
        paymentRepository.save(payment);

        boolean success=defaultGateway.pay(ticketId,amount);

        if (success) {
            payment.markAsSuccess();
        }else{
            payment.markAsFailed();
        }
        paymentRepository.update(payment);
        System.out.println("[SERVICE] Payment processed with status: " + (success ? "SUCCESS" : "FAILED"));

        return success;
    }

    public boolean processPaymentWithRetry(UUID ticket, double amount, int maxRetries){
        System.out.println("[SERVICE] Processing payment with retry for ticket: " + ticket);
        for(int attempt=1;attempt<=maxRetries;attempt++){
            System.out.println("[SERVICE] Payment attempt " + attempt + " of " + maxRetries);

            boolean success=processPayment(ticket,amount);
            if (success) {
                System.out.println("[SERVICE] Payment successful on attempt " + attempt);
                return true;
            }

            if(attempt>1){
                defaultGateway=new StripeAdapter();
                System.out.println("[SERVICE] Switching to Stripe gateway for retry");

            }
        }
        System.out.println("[SERVICE] Payment failed after " + maxRetries + " attempts");
        return false;



    }
    public void setDefaultGateway(PaymentGatewayAdapter gateway) {
        this.defaultGateway = gateway;
    }
}
