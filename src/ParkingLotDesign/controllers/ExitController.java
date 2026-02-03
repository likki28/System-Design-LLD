package ParkingLotDesign.controllers;

import ParkingLotDesign.domain.Receipt;
import ParkingLotDesign.domain.Ticket;
import ParkingLotDesign.services.*;

import java.util.*;

import javax.print.PrintService;

public class ExitController {

    private TicketService ticketService;
    private SlotService slotService;
    private ReceiptService receiptService;
    private PaymentService paymentService;
    private PricingService pricingService;

    public ExitController(TicketService ticketService, PricingService pricingService,
                          PaymentService paymentService, ReceiptService receiptService,
                          SlotService slotService){

        this.ticketService = ticketService;
        this.pricingService = pricingService;
        this.paymentService = paymentService;
        this.receiptService = receiptService;
        this.slotService = slotService;
        System.out.println("[CONTROLLER] ExitController initialized");
    }

    public ExitResult exitVehicle(UUID ticketId){

        try {
            Optional<Ticket> ticketOpt = ticketService.getTicket(ticketId);
            if (ticketOpt.isEmpty()) {
                return new ExitResult(false, null, 0.0, "Ticket not found");
            }
            Ticket ticket = ticketOpt.get();
            if (!ticket.isActive()) {
                return new ExitResult(false, null, 0.0, "Ticket is not active");
            }

            double fee = pricingService.calculateFee(ticket);
            System.out.println("[CONTROLLER] Fee calculated: " + fee);

            boolean paymentSuccess=paymentService.processPaymentWithRetry(ticketId, fee, 3);
            if (!paymentSuccess) {
                return new ExitResult(false, null, 0.0, "Payment failed");
            }
            Receipt receipt = receiptService.generateReceipt(ticket, fee);
            receiptService.markReceiptAsPaid(receipt);

            slotService.releaseSlot(ticket.getSlotId());

            ticketService.deactivateTicket(ticketId);

        }catch (Exception e){


        }



    }

    public static class ExitResult {
        private final boolean success;
        private final UUID receiptId;
        private final double fee;
        private final String message;

        public ExitResult(boolean success, UUID receiptId, double fee, String message) {
            this.success = success;
            this.receiptId = receiptId;
            this.fee = fee;
            this.message = message;
        }

        public boolean isSuccess() { return success; }
        public UUID getReceiptId() { return receiptId; }
        public double getFee() { return fee; }
        public String getMessage() { return message; }
    }



}
